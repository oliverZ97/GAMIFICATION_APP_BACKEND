package app.restservice.apprestservice.Services;

import org.springframework.stereotype.Service;

import app.restservice.apprestservice.CopyPropertiesOfEntity;
import app.restservice.apprestservice.Entities.Experience;
import app.restservice.apprestservice.Entities.Streak;
import app.restservice.apprestservice.Entities.UserLog;
import app.restservice.apprestservice.Exceptions.ResourceNotFoundException;
import app.restservice.apprestservice.Repositories.ExperienceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExperienceService {

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private LevelService levelService;

    @Autowired
    private UserLogService userLogService;

    @Autowired
    private StreakService streakService;

    @Autowired
    private UserAchievementService userAchievementService;

    private CopyPropertiesOfEntity copyPropertiesOfEntity;

    public Experience getExperience(Long id) {
        if (experienceRepository.findById(id).isPresent()) {
            return experienceRepository.findById(id).get();
        } else {
            throw new ResourceNotFoundException("no experience found at id" + id);
        }
    }

    public Experience getExperienceByUserID(Long id) {
        if (experienceRepository.findById(id).isPresent()) {
            return experienceRepository.getExperienceByUserID(id).get();
        } else {
            throw new ResourceNotFoundException("no experience found at id" + id);
        }
    }

    public List<Experience> getAllExperiences() {
        return experienceRepository.findAll();
    }

    public Experience setExperience(Experience experience) {
        return experienceRepository.save(experience);
    }

    public Experience updateExperience(Experience experienceRequest, long id) {
        Experience experience = getExperience(id);
        copyPropertiesOfEntity.copyNonNullProperties(experienceRequest, experience);
        // Check if level changed
        int level = levelService.checkLevelStatus(experienceRequest.getExperience_value());
        if (level != experienceRequest.getLevel()) {
            UserLog entry = new UserLog();
            entry.setDate_created(LocalDateTime.now().toString());
            entry.setInfo("level changed");
            entry.setType(1);
            entry.setStatus(1);
            entry.setUser_ID(experience.getUser_ID());
            userLogService.setUserLog(entry);
        }
        experience.setLevel(level);

        // check if new achievement is reached
        userAchievementService.handleUserAchievementByKey(experience.getUser_ID(), "level");

        // check if streak is ongoing
        Streak userStreak = streakService.getActiveStreakByUserId(experience.getUser_ID());
        LocalDateTime now = LocalDateTime.now();
        if (userStreak != null) {
            LocalDateTime last_updated = LocalDateTime.parse(userStreak.getLast_updated());
            if (last_updated.plusHours(24).isBefore(now)) {
                userStreak.setDay_count(userStreak.getDay_count() + 1);
            } else {
                userStreak.setStatus(2);
            }
            streakService.updateStreak(userStreak, userStreak.getId());
        } else {
            Streak streak = new Streak();
            streak.setDay_count(1);
            streak.setLast_updated(now.toString());
            streak.setStatus(1);
            streakService.setStreak(streak);
        }

        return experienceRepository.save(experience);
    }

    public ResponseEntity<?> deleteExperience(long id) {
        return experienceRepository.findById(id)
                .map(experience -> {
                    experienceRepository.delete(experience);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("experience not found with id " + id));
    }

}
