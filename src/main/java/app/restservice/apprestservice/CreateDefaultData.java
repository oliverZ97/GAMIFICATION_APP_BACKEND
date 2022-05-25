package app.restservice.apprestservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import app.restservice.apprestservice.Entities.User;
import app.restservice.apprestservice.Services.UserService;

public class CreateDefaultData implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(CreateDefaultData.class);

    @Autowired
    UserService userService;

    @Override
    public void run(String... args) throws Exception {

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
