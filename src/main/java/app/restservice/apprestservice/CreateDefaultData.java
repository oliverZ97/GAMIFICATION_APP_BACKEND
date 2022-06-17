package app.restservice.apprestservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import app.restservice.apprestservice.Entities.User;
import app.restservice.apprestservice.Services.QuestService;
import app.restservice.apprestservice.Services.UserService;

@Component
public class CreateDefaultData implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(CreateDefaultData.class);

    @Autowired
    UserService userService;

    @Autowired
    QuestService questService;

    @Override
    public void run(String... args) throws Exception {
        questService.createRandomTopicQuest(1);
        questService.createRandomTopicQuest(1);
        questService.createRandomCategoryQuest(1);
        questService.createRandomCategoryQuest(1);
        questService.createRandomWordCountQuest(1);
        questService.createRandomWordCountQuest(1);
        questService.createRandomTopicQuest(2);
        questService.createRandomTopicQuest(2);
        questService.createRandomCategoryQuest(2);
        questService.createRandomCategoryQuest(2);
        questService.createRandomWordCountQuest(2);
        questService.createRandomWordCountQuest(2);
        questService.createRandomCategoryQuest(3);
        questService.createRandomCategoryQuest(3);
        questService.createRandomTopicQuest(3);
        questService.createRandomTopicQuest(3);
        questService.createRandomWordCountQuest(3);
        questService.createRandomWordCountQuest(3);
        System.out.println("Quests erzeugt");

        // if (userService != null) {
        // logger.info("saving students");
        // userService.setUser(
        // new User(1L, "mySuperemail@gmail.com", "Oliver", "Ziemann", "1526000001",
        // (long) 1, "1111"));
        // } else {
        // logger.info("studentservice is null");
        // }
    }
}
