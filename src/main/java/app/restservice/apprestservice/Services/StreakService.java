package app.restservice.apprestservice.Services;

import org.springframework.stereotype.Service;

import app.restservice.apprestservice.CopyPropertiesOfEntity;
import app.restservice.apprestservice.Entities.Streak;
import app.restservice.apprestservice.Entities.UserLog;
import app.restservice.apprestservice.Exceptions.ResourceNotFoundException;
import app.restservice.apprestservice.Repositories.StreakRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class StreakService {

    @Autowired
    private StreakRepository streakRepository;

    @Autowired
    private UserAchievementService userAchievementService;

    @Autowired
    private UserLogService userLogService;

    private CopyPropertiesOfEntity copyPropertiesOfEntity;

    public Streak getStreak(Long id) {
        if (streakRepository.findById(id).isPresent()) {
            return streakRepository.findById(id).get();
        } else {
            throw new ResourceNotFoundException("no streak found at id" + id);
        }
    }

    public List<Streak> getAllStreaks() {
        return streakRepository.findAll();
    }

    public Streak setStreak(Streak streak) {
        return streakRepository.save(streak);
    }

    public Streak getActiveStreakByUserId(Long user_id) {
        Streak s = streakRepository.getActiveStreakByUserId(user_id);
        if (s != null) {
            return s;
        } else {
            LocalDateTime now = LocalDateTime.now().withNano(0);
            Streak streak = new Streak();
            streak.setDay_count(0);
            streak.setLast_updated(now.toString());
            streak.setStatus(1);
            streak.setUser_id(user_id);
            streak.setChanged_today(false);
            setStreak(streak);
            return streak;
        }

    }

    public List<Streak> getStreaksByUserId(Long user_id) {
        return streakRepository.getStreakByUserId(user_id);
    }

    public void checkStreakToday(Long id) {
        Streak s = getActiveStreakByUserId(id);
        if (s != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            LocalDateTime last_updated = LocalDateTime.parse(s.getLast_updated(), formatter);
            if (last_updated.getDayOfYear() < LocalDateTime.now().getDayOfYear()) {
                s.setChanged_today(false);
                updateStreak(s, s.getId());
            }
        }
    }

    public Streak updateStreak(Streak streakRequest, long id) {
        Streak streak = getStreak(id);
        copyPropertiesOfEntity.copyNonNullProperties(streakRequest, streak);

        userAchievementService.handleUserAchievementByKey(streakRequest.getUser_id(), "streak");
        return streakRepository.save(streak);
    }

    public void handleStreakStatus(Long user_id) {
        Streak userStreak = getActiveStreakByUserId(user_id);
        LocalDateTime now = LocalDateTime.now().withNano(0);
        if (userStreak != null) {
            if (!userStreak.getChanged_today()) {
                LocalDateTime last_updated = LocalDateTime.parse(userStreak.getLast_updated());
                if (last_updated.getDayOfYear() + 1 == now.getDayOfYear()
                        || (last_updated.getDayOfYear() == now.getDayOfYear() && userStreak.getDay_count() == 0)) {
                    userStreak.setDay_count(userStreak.getDay_count() + 1);
                    userStreak.setChanged_today(true);
                    userStreak.setLast_updated(now.toString());
                } else {
                    userStreak.setStatus(2);
                }
                updateStreak(userStreak, userStreak.getId());
            }
        } else {
            Streak streak = new Streak();
            streak.setDay_count(1);
            streak.setLast_updated(now.toString());
            streak.setStatus(1);
            streak.setUser_id(user_id);
            streak.setChanged_today(true);
            setStreak(streak);
        }
        UserLog entry = new UserLog();
        entry.setDate_created(LocalDateTime.now().toString());
        entry.setInfo("streak changed");
        entry.setType(4);
        entry.setStatus(1);
        entry.setUser_ID(user_id);
        userLogService.setUserLog(entry);
    }

    public ResponseEntity<?> deleteStreak(long id) {
        return streakRepository.findById(id)
                .map(streak -> {
                    streakRepository.delete(streak);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("streak not found with id " + id));
    }

}
