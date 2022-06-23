package app.restservice.apprestservice.Services;

import org.springframework.stereotype.Service;

import app.restservice.apprestservice.CopyPropertiesOfEntity;
import app.restservice.apprestservice.Entities.ContentQuestHelper;
import app.restservice.apprestservice.Entities.Quest;
import app.restservice.apprestservice.Entities.TimeLog;
import app.restservice.apprestservice.Entities.Topic;
import app.restservice.apprestservice.Entities.UserLog;
import app.restservice.apprestservice.Entities.UserQuest;
import app.restservice.apprestservice.Entities.UserQuestHelper;
import app.restservice.apprestservice.Exceptions.ResourceNotFoundException;
import app.restservice.apprestservice.Repositories.UserQuestRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;

import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserQuestService {

    @Autowired
    private UserQuestRepository userQuestRepository;

    @Autowired
    private QuestService questService;

    @Autowired
    private UserLogService userLogService;

    @Autowired
    private TimeLogService timeLogService;

    @Autowired
    private TopicService topicService;

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
            uqh.setId(uqs.get(i).getId());
            uqhs.add(uqh);
        }
        return uqhs;
    }

    public void checkIfQuestsAreExpired(Long user_id) {
        LocalDateTime now = LocalDateTime.now();
        TimeLog dailyLog = timeLogService.getTimeLogByType(1);
        TimeLog weeklyLog = timeLogService.getTimeLogByType(2);
        TimeLog monthlyLog = timeLogService.getTimeLogByType(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        if (dailyLog == null) {
            LocalDateTime start = timeLogService.todayAt(4, 0);
            LocalDateTime end = start.plusHours(24);
            timeLogService.setTimeLog(start.toString(), end.toString(), "", 1, 1);
            addNewUserQuestSet(user_id, 1);
        } else {
            LocalDateTime daily = LocalDateTime.parse(dailyLog.getDate_end(), formatter);
            if (now.isAfter(daily)) {
                LocalDateTime end = daily.plusHours(24);
                dailyLog.setStatus(3);
                timeLogService.updateTimeLog(dailyLog, dailyLog.getId());
                timeLogService.setTimeLog(daily.toString(), end.toString(), "", 1, 1);
                addNewUserQuestSet(user_id, 1);
            }
        }
        if (weeklyLog == null) {
            LocalDateTime start = timeLogService.todayAt(4, 0).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            LocalDateTime end = start.plusDays(7);
            timeLogService.setTimeLog(start.toString(), end.toString(), "", 1, 2);
            addNewUserQuestSet(user_id, 2);
        } else {
            LocalDateTime weekly = LocalDateTime.parse(weeklyLog.getDate_end(), formatter);
            if (now.isAfter(weekly)) {
                LocalDateTime end = weekly.plusDays(7);
                weeklyLog.setStatus(3);
                timeLogService.updateTimeLog(weeklyLog, weeklyLog.getId());
                timeLogService.setTimeLog(weekly.toString(), end.toString(), "", 1, 2);
                addNewUserQuestSet(user_id, 2);
            }
        }
        if (monthlyLog == null) {
            LocalDateTime start = timeLogService.todayAt(4, 0).withDayOfMonth(1);
            LocalDateTime end = start.plusMonths(1);
            timeLogService.setTimeLog(start.toString(), end.toString(), "", 1, 3);
            addNewUserQuestSet(user_id, 3);
        } else {
            LocalDateTime monthly = LocalDateTime.parse(monthlyLog.getDate_end(), formatter);
            if (now.isAfter(monthly)) {
                LocalDateTime end = monthly.plusMonths(1);
                monthlyLog.setStatus(3);
                timeLogService.updateTimeLog(monthlyLog, monthlyLog.getId());
                timeLogService.setTimeLog(monthly.toString(), end.toString(), "", 1, 3);
                addNewUserQuestSet(user_id, 3);
            }
        }

    }

    public List<UserQuestHelper> checkContentForUserQuest(ContentQuestHelper helper) {
        List<UserQuestHelper> uqs = getActiveUserQuestsByUserId(helper.getUser_ID());
        List<Topic> topics = topicService.getTopicsByIdString(helper.getTopic_IDs());
        List<UserQuestHelper> result = new ArrayList<UserQuestHelper>();
        for (int i = 0; i < uqs.size(); i++) {
            String key = uqs.get(i).getQuest().getKey();
            try {
                if (Integer.parseInt(key) <= helper.getNumber_of_words()) {
                    result.add(uqs.get(i));
                }
            } catch (NumberFormatException e) {
                for (int j = 0; j < topics.size(); j++) {
                    if (key.equals(topics.get(j).getName())) {
                        result.add(uqs.get(i));
                    }
                }
            }

        }
        return result;
    }

    public UserQuest setUserQuest(UserQuest userQuest) {
        return userQuestRepository.save(userQuest);
    }

    public void addNewUserQuestSet(long user_id, int type) {
        List<Quest> quests = questService.getRandomQuestSet(type);
        TimeLog log = timeLogService.getTimeLogByType(type);
        for (int i = 0; i < quests.size(); i++) {
            UserQuest uq = new UserQuest();
            uq.setQuest_ID(quests.get(i).getId());
            uq.setGoal_value(quests.get(i).getKey_count());
            uq.setStatus(1);
            uq.setProgress_value(0);
            uq.setUser_ID(user_id);
            uq.setStart_date(log.getDate_start());
            uq.setEnd_date(log.getDate_end());
            setUserQuest(uq);
        }
    }

    public UserQuest updateUserQuest(UserQuest userQuestRequest, long id) {
        UserQuest userQuest = getUserQuest(id);
        copyPropertiesOfEntity.copyNonNullProperties(userQuestRequest, userQuest);
        if (userQuestRequest.getProgress_value() >= userQuestRequest.getGoal_value()
                && userQuestRequest.getStatus() == 1) {
            UserLog entry = new UserLog();
            entry.setDate_created(LocalDateTime.now().toString());
            entry.setInfo("quest passed");
            entry.setType(2);
            entry.setStatus(1);
            entry.setUser_ID(userQuestRequest.getUser_ID());
            userLogService.setUserLog(entry);
        }
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
