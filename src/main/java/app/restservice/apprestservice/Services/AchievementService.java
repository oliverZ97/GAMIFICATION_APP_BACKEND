package app.restservice.apprestservice.Services;

import org.springframework.stereotype.Service;

import app.restservice.apprestservice.CopyPropertiesOfEntity;
import app.restservice.apprestservice.Entities.Achievement;
import app.restservice.apprestservice.Exceptions.ResourceNotFoundException;
import app.restservice.apprestservice.Repositories.AchievementRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Service
public class AchievementService {

    @Autowired
    private AchievementRepository achievementRepository;

    private CopyPropertiesOfEntity copyPropertiesOfEntity;

    public Achievement getAchievement(Long id) {
        if (achievementRepository.findById(id).isPresent()) {
            return achievementRepository.findById(id).get();
        } else {
            throw new ResourceNotFoundException("no achievement found at id" + id);
        }
    }

    public List<Achievement> getAllAchievements() {
        return achievementRepository.findAll();
    }

    public List<Achievement> getAchievementsByKey(String key) {
        return achievementRepository.getAchievementsByKey(key);
    }

    public Achievement setAchievement(Achievement achievement) {
        return achievementRepository.save(achievement);
    }

    public Achievement updateAchievement(Achievement achievementRequest, long id) {
        Achievement achievement = getAchievement(id);
        copyPropertiesOfEntity.copyNonNullProperties(achievementRequest, achievement);
        return achievementRepository.save(achievement);
    }

    public ResponseEntity<?> deleteAchievement(long id) {
        return achievementRepository.findById(id)
                .map(achievement -> {
                    achievementRepository.delete(achievement);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("achievement not found with id " + id));
    }

}
