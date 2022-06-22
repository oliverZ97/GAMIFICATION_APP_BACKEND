package app.restservice.apprestservice;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import app.restservice.apprestservice.Entities.TimeLog;
import app.restservice.apprestservice.Entities.User;
import app.restservice.apprestservice.Services.QuestService;
import app.restservice.apprestservice.Services.TimeLogService;
import app.restservice.apprestservice.Services.UserService;

@Component
public class CreateDefaultData implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(CreateDefaultData.class);

    @Autowired
    UserService userService;

    @Autowired
    QuestService questService;

    @Autowired
    TimeLogService timeLogService;

    @Override
    public void run(String... args) throws Exception {
        // questService.createRandomTopicQuest(1);
        // questService.createRandomTopicQuest(1);
        // questService.createRandomCategoryQuest(1);
        // questService.createRandomCategoryQuest(1);
        // questService.createRandomWordCountQuest(1);
        // questService.createRandomWordCountQuest(1);
        // questService.createRandomTopicQuest(2);
        // questService.createRandomTopicQuest(2);
        // questService.createRandomCategoryQuest(2);
        // questService.createRandomCategoryQuest(2);
        // questService.createRandomWordCountQuest(2);
        // questService.createRandomWordCountQuest(2);
        // questService.createRandomCategoryQuest(3);
        // questService.createRandomCategoryQuest(3);
        // questService.createRandomTopicQuest(3);
        // questService.createRandomTopicQuest(3);
        // questService.createRandomWordCountQuest(3);
        // questService.createRandomWordCountQuest(3);
        // System.out.println("Quests erzeugt");

        List<TimeLog> dailyLogs = timeLogService.getTimeLogsByType(1);
        List<TimeLog> weeklyLogs = timeLogService.getTimeLogsByType(2);
        List<TimeLog> monthlyLogs = timeLogService.getTimeLogsByType(3);
        if (dailyLogs.size() > 0) {

        } else {
            LocalDateTime now = LocalDateTime.now();

            TimeLog daily = new TimeLog();
            daily.setType(1);
            daily.setStatus(1);
        }
    }
}
