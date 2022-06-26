package app.restservice.apprestservice.Services;

import org.springframework.stereotype.Service;

import app.restservice.apprestservice.CopyPropertiesOfEntity;
import app.restservice.apprestservice.Entities.Achievement;
import app.restservice.apprestservice.Entities.Experience;
import app.restservice.apprestservice.Entities.Streak;
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

    @Autowired
    private ExperienceService experienceService;

    @Autowired
    private UserQuestService userQuestService;

    @Autowired
    private StreakService streakService;

    @Autowired
    private UserContentService userContentService;

    @Autowired
    private UserLogService userLogService;

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

    public UserAchievement getUserAchievementsByUserIdAndAchievementId(Long user_id, Long achievement_id) {
        return userAchievementRepository.getUserAchievementsByUserIdAndAchievementId(user_id, achievement_id);
    }

    public int getUserAchievementCountByAchievementId(long achievement_id) {
        return userAchievementRepository.getUserAchievementCountByAchievementId(achievement_id);
    }

    public int getFinishedUserAchievementsCountByUserId(Long user_id) {
        return userAchievementRepository.getFinishedUserAchievementsCountByUserId(user_id);
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

    public List<UserAchievement> getUserAchievementsByUserIdAndKey(Long user_id, String key) {
        List<Achievement> keyAchievements = achievementService.getAchievementsByKey(key);
        List<UserAchievement> result = new ArrayList<UserAchievement>();
        for (int i = 0; i < keyAchievements.size(); i++) {
            UserAchievement ua = getUserAchievementsByUserIdAndAchievementId(user_id, keyAchievements.get(i).getId());
            if (ua != null) {
                result.add(ua);
            }

        }

        return result;

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

    public void handleUserAchievementByKey(Long user_id, String key) {
        List<UserAchievement> list = getUserAchievementsByUserIdAndKey(user_id, key);
        Experience userExp = experienceService.getExperienceByUserID(user_id);
        switch (key) {
            case "level":
                handleLevelKey(user_id, key, list, userExp);
                break;
            case "achievement":
                handleAchievementKey(user_id, key, list, userExp);
                break;
            case "daily":
                handleDailyKey(user_id, key, list, userExp);
                break;
            case "weekly":
                handleWeeklyKey(user_id, key, list, userExp);
                break;
            case "monthly":
                handleMonthlyKey(user_id, key, list, userExp);
                break;
            case "streak":
                handleStreakKey(user_id, key, list, userExp);
                break;
        }
    }

    public void handleStreakKey(Long user_id, String key, List<UserAchievement> list, Experience userExp) {
        Streak streak = streakService.getActiveStreakByUserId(user_id);
        int day_count = 0;
        if (streak != null) {
            day_count = streak.getDay_count();
        }

        for (int i = 0; i < list.size(); i++) {
            UserAchievement ua = list.get(i);
            if (day_count >= ua.getGoal_value()) {
                ua = successConditionHandler(user_id, key, userExp, ua);
            }
            updateUserAchievement(ua, ua.getId(), false);
        }
    }

    public void handleLevelKey(Long user_id, String key, List<UserAchievement> list, Experience userExp) {
        for (int i = 0; i < list.size(); i++) {
            UserAchievement ua = list.get(i);
            if (userExp.getLevel() >= ua.getGoal_value() && ua.getStatus() == 1) {
                ua = successConditionHandler(user_id, key, userExp, ua);
            } else {
                ua.setProgress_value(userExp.getLevel());
            }
            updateUserAchievement(ua, ua.getId(), true);
        }
    }

    public void handleAchievementKey(Long user_id, String key, List<UserAchievement> list, Experience userExp) {
        // get number of finished achievements to check if goal value is reached
        int finishedAchievementCount = getFinishedUserAchievementsCountByUserId(user_id);

        for (int i = 0; i < list.size(); i++) {
            UserAchievement ua = list.get(i);
            if (finishedAchievementCount >= ua.getGoal_value()) {
                ua = successConditionHandler(user_id, key, userExp, ua);
            } else {
                ua.setProgress_value(finishedAchievementCount);
            }
            updateUserAchievement(ua, ua.getId(), false);
        }
    }

    public void handleDailyKey(Long user_id, String key, List<UserAchievement> list, Experience userExp) {
        int finishedDailyCount = userQuestService.getFinishedUserQuestCountByType(user_id, 1);
        for (int i = 0; i < list.size(); i++) {
            UserAchievement ua = list.get(i);
            if (finishedDailyCount >= ua.getGoal_value() && ua.getStatus() == 1) {
                ua = successConditionHandler(user_id, key, userExp, ua);
            } else {
                ua.setProgress_value(finishedDailyCount);
            }
            updateUserAchievement(ua, ua.getId(), true);
        }
    }

    public void handleWeeklyKey(Long user_id, String key, List<UserAchievement> list, Experience userExp) {
        int finishedDailyCount = userQuestService.getFinishedUserQuestCountByType(user_id, 2);
        for (int i = 0; i < list.size(); i++) {
            UserAchievement ua = list.get(i);
            if (finishedDailyCount >= ua.getGoal_value() && ua.getStatus() == 1) {
                ua = successConditionHandler(user_id, key, userExp, ua);
            } else {
                ua.setProgress_value(finishedDailyCount);
            }
            updateUserAchievement(ua, ua.getId(), true);
        }
    }

    public void handleMonthlyKey(Long user_id, String key, List<UserAchievement> list, Experience userExp) {
        int finishedDailyCount = userQuestService.getFinishedUserQuestCountByType(user_id, 3);
        for (int i = 0; i < list.size(); i++) {
            UserAchievement ua = list.get(i);
            if (finishedDailyCount >= ua.getGoal_value() && ua.getStatus() == 1) {
                ua = successConditionHandler(user_id, key, userExp, ua);
            } else {
                ua.setProgress_value(finishedDailyCount);
            }
            updateUserAchievement(ua, ua.getId(), true);
        }
    }

    // public void handleTopicKey(Long user_id, String key, List<UserAchievement>
    // list, Experience userExp) {
    // int readTopicContentCount =
    // userContentService.getReadContentsByTopicAndUser(user_id);
    // for (int i = 0; i < list.size(); i++) {
    // UserAchievement ua = list.get(i);
    // if (readTopicContentCount >= ua.getGoal_value() && ua.getStatus() == 1) {
    // ua = successConditionHandler(user_id, key, userExp, ua);
    // } else {
    // ua.setProgress_value(readTopicContentCount);
    // }
    // updateUserAchievement(ua, ua.getId(), true);
    // }
    // }

    public UserAchievement successConditionHandler(Long user_id, String key, Experience userExp, UserAchievement ua) {
        Achievement a = achievementService.getAchievement(ua.getAchievement_ID());
        ua.setProgress_value(ua.getGoal_value());
        ua.setStatus(2);
        ua.setEnd_date(LocalDateTime.now().toString());
        ua.setCount_achieved_user(getUserAchievementCountByAchievementId(ua.getAchievement_ID()) + 1);
        userLogService.setPartialUserLog("Errungenschaft: " + a.getTitle() + " freigeschaltet!", 3, user_id);
        userExp.setExperience_value(userExp.getExperience_value() + a.getExperience());
        experienceService.updateExperience(userExp, userExp.getId());
        return ua;
    }

    public UserAchievement setUserAchievement(UserAchievement userAchievement) {
        return userAchievementRepository.save(userAchievement);
    }

    public UserAchievement updateUserAchievement(UserAchievement userAchievementRequest, long id,
            Boolean doAchievementCheck) {
        UserAchievement userAchievement = getUserAchievement(id);
        copyPropertiesOfEntity.copyNonNullProperties(userAchievementRequest, userAchievement);
        if (doAchievementCheck) {
            handleUserAchievementByKey(userAchievement.getUser_ID(), "achievement");
        }
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
