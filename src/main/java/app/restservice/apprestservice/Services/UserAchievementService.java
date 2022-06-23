package app.restservice.apprestservice.Services;

import org.springframework.stereotype.Service;

import app.restservice.apprestservice.CopyPropertiesOfEntity;
import app.restservice.apprestservice.Entities.Achievement;
import app.restservice.apprestservice.Entities.UserAchievement;
import app.restservice.apprestservice.Entities.UserAchievementHelper;
import app.restservice.apprestservice.Exceptions.ResourceNotFoundException;
import app.restservice.apprestservice.Repositories.UserAchievementRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserAchievementService {

    @Autowired
    private UserAchievementRepository userAchievementRepository;

    @Autowired
    private AchievementService achievementService;

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

    public List<UserAchievementHelper> getByUserId(Long id) {
        List<UserAchievement> userAchievements = userAchievementRepository.getUserAchievementsByUserId(id);
        List<UserAchievementHelper> uahs = new ArrayList<UserAchievementHelper>();
        for (int i = 0; i < userAchievements.size(); i++) {
            Achievement a = achievementService.getAchievement(userAchievements.get(i).getAchievement_ID());
            UserAchievementHelper uah = new UserAchievementHelper();
            uah.setAchievement(a);
            uah.setCount_achieved_user(userAchievements.get(i).getCount_achieved_user());
            uah.setEnd_date(userAchievements.get(i).getEnd_date());
            uah.setGoal_value(userAchievements.get(i).getGoal_value());
            uah.setProgress_value(userAchievements.get(i).getProgress_value());
            uah.setId(userAchievements.get(i).getId());
            uah.setStart_date(userAchievements.get(i).getStart_date());
            uah.setStatus(userAchievements.get(i).getStatus());
            uah.setUser_ID(userAchievements.get(i).getUser_ID());
            uahs.add(uah);
        }
        return uahs;
    }

    public void checkUserAchievements(Long id) {
        List<UserAchievement> achievements = userAchievementRepository.getUserAchievementsByUserId(id);
        if (achievements.size() == 0) {
            initializeUserAchievements(id);
        }
    }

    public void initializeUserAchievements(Long user_id) {
        List<Achievement> achievements = achievementService.getAllAchievements();
        for (int i = 0; i < achievements.size(); i++) {
            Achievement a = achievements.get(i);
            UserAchievement u = new UserAchievement();
            u.setAchievement_ID(a.getId());
            u.setCount_achieved_user(0);
            u.setGoal_value(a.getKey_count());
            u.setProgress_value(0);
            u.setStatus(1);
            u.setUser_ID(user_id);
            u.setStart_date(LocalDateTime.now().toString());
            setUserAchievement(u);
        }
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
