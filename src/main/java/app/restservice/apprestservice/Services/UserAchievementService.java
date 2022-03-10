package app.restservice.apprestservice.Services;

import org.springframework.stereotype.Service;

import app.restservice.apprestservice.CopyPropertiesOfEntity;
import app.restservice.apprestservice.Entities.UserAchievement;
import app.restservice.apprestservice.Exceptions.ResourceNotFoundException;
import app.restservice.apprestservice.Repositories.UserAchievementRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Service
public class UserAchievementService {
   
    @Autowired
    private UserAchievementRepository userAchievementRepository;

    private CopyPropertiesOfEntity copyPropertiesOfEntity;

    public UserAchievement getUserAchievement(Long id) {
        if (userAchievementRepository.findById(id).isPresent()) {
            return userAchievementRepository.findById(id).get();
        } else {
            throw new ResourceNotFoundException("no user achievement found at id" + id);
        }
    }

    public List<UserAchievement> getAllUserAchievements() {
        return userAchievementRepository.findAll();
    }

    public UserAchievement setUserAchievement(UserAchievement userAchievement) {
        return userAchievementRepository.save(userAchievement);
    }

    public UserAchievement updateUserAchievement(UserAchievement userAchievementRequest, long id) {
        UserAchievement userAchievement = getUserAchievement(id);
        copyPropertiesOfEntity.copyNonNullProperties(userAchievementRequest, userAchievement);
        return userAchievementRepository.save(userAchievement);
    }


    public ResponseEntity<?> deleteUserAchievement(long id) {
        return userAchievementRepository.findById(id)
                .map(userAchievement -> {
                    userAchievementRepository.delete(userAchievement);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("userAchievement not found with id " + id));
    }

}
