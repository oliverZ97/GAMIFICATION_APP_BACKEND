package app.restservice.apprestservice.Services;

import org.springframework.stereotype.Service;

import app.restservice.apprestservice.CopyPropertiesOfEntity;
import app.restservice.apprestservice.Entities.Quest;
import app.restservice.apprestservice.Entities.UserQuest;
import app.restservice.apprestservice.Entities.UserQuestHelper;
import app.restservice.apprestservice.Exceptions.ResourceNotFoundException;
import app.restservice.apprestservice.Repositories.UserQuestRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserQuestService {

    @Autowired
    private UserQuestRepository userQuestRepository;

    @Autowired
    private QuestService questService;

    private CopyPropertiesOfEntity copyPropertiesOfEntity;

    public UserQuest getUserQuest(Long id) {
        if (userQuestRepository.findById(id).isPresent()) {
            return userQuestRepository.findById(id).get();
        } else {
            throw new ResourceNotFoundException("no user quest found at id" + id);
        }
    }

    public List<UserQuest> getAllUserQuests() {
        return userQuestRepository.findAll();
    }

    public List<UserQuestHelper> getActiveUserQuestsByUserId(Long user_id) {
        List<UserQuest> uqs = userQuestRepository.getActiveUserQuestsByUserId(user_id);
        List<UserQuestHelper> uqhs = new ArrayList<UserQuestHelper>();
        for (int i = 0; i < uqs.size(); i++) {
            Quest q = questService.getQuest(uqs.get(i).getQuest_ID());
            UserQuestHelper uqh = new UserQuestHelper();
            uqh.setEnd_date(uqs.get(i).getEnd_date());
            uqh.setGoal_value(uqs.get(i).getGoal_value());
            uqh.setProgress_value(uqs.get(i).getProgress_value());
            uqh.setQuest(q);
            uqh.setStart_date(uqs.get(i).getStart_date());
            uqh.setStatus(uqs.get(i).getStatus());
            uqh.setUser_ID(uqs.get(i).getUser_ID());
            uqhs.add(uqh);
        }
        return uqhs;
    }

    public UserQuest setUserQuest(UserQuest userQuest) {
        return userQuestRepository.save(userQuest);
    }

    public void addNewUserQuestSet(long user_id, int type) {
        List<Quest> quests = questService.getRandomQuestSet(type);
        LocalDateTime now = LocalDateTime.now();
        for (int i = 0; i < quests.size(); i++) {
            UserQuest uq = new UserQuest();
            uq.setQuest_ID(quests.get(i).getId());
            uq.setGoal_value(quests.get(i).getKey_count());
            uq.setStatus(1);
            uq.setProgress_value(0);
            uq.setUser_ID(user_id);
            uq.setStart_date(now.toString());
            switch (type) {
                case 1:
                    uq.setEnd_date(now.plusHours(24).toString());
                    break;
                case 2:
                    uq.setEnd_date(now.plusWeeks(1).toString());
                    break;
                case 3:
                    uq.setEnd_date(now.plusMonths(1).toString());
                    break;

            }
            setUserQuest(uq);
        }
    }

    public UserQuest updateUserQuest(UserQuest userQuestRequest, long id) {
        UserQuest userQuest = getUserQuest(id);
        copyPropertiesOfEntity.copyNonNullProperties(userQuestRequest, userQuest);
        return userQuestRepository.save(userQuest);
    }

    public ResponseEntity<?> deleteUserQuest(long id) {
        return userQuestRepository.findById(id)
                .map(userQuest -> {
                    userQuestRepository.delete(userQuest);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("userQuest not found with id " + id));
    }

}
