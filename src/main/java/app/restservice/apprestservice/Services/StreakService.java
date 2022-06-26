package app.restservice.apprestservice.Services;

import org.springframework.stereotype.Service;

import app.restservice.apprestservice.CopyPropertiesOfEntity;
import app.restservice.apprestservice.Entities.Streak;
import app.restservice.apprestservice.Exceptions.ResourceNotFoundException;
import app.restservice.apprestservice.Repositories.StreakRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Service
public class StreakService {

    @Autowired
    private StreakRepository streakRepository;

    @Autowired
    private UserAchievementService userAchievementService;

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
        return streakRepository.getActiveStreakByUserId(user_id);
    }

    public List<Streak> getStreaksByUserId(Long user_id) {
        return streakRepository.getStreakByUserId(user_id);
    }

    public Streak updateStreak(Streak streakRequest, long id) {
        Streak streak = getStreak(id);
        copyPropertiesOfEntity.copyNonNullProperties(streakRequest, streak);

        userAchievementService.handleUserAchievementByKey(streakRequest.getUser_id(), "streak");
        return streakRepository.save(streak);
    }

    public ResponseEntity<?> deleteStreak(long id) {
        return streakRepository.findById(id)
                .map(streak -> {
                    streakRepository.delete(streak);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("streak not found with id " + id));
    }

}
