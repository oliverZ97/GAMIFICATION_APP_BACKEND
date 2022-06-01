package app.restservice.apprestservice.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import app.restservice.apprestservice.Entities.UserTopic;
import app.restservice.apprestservice.Services.UserTopicService;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserTopicController {

    @Autowired
    private UserTopicService userTopicService;

    @GetMapping("/usertopics/get/{id}")
    public UserTopic getUserTopics(@PathVariable Long id) {
        return userTopicService.getUserTopic(id);
    }

    @GetMapping("/usertopics/getbyuserid/{id}")
    public List<UserTopic> getUserTopicsByUserID(@PathVariable Long id) {
        return userTopicService.getUserTopicsByUserID(id);
    }

    @GetMapping("/usertopics/getfavourites/{id}")
    public List<UserTopic> getUserFavouriteTopicsByUserID(@PathVariable Long id) {
        return userTopicService.getUserFavouriteTopicsByUserID(id);
    }

    @GetMapping("/usertopics/getAll")
    public List<UserTopic> getAllUserTopics() {
        return userTopicService.getAllUserTopics();
    }

    @PostMapping("/usertopics/set")
    public ResponseEntity<String> setUserTopic(@Valid @RequestBody UserTopic userTopic, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors().toString());
        } else {
            userTopicService.setUserTopic(userTopic);
            return ResponseEntity.status(HttpStatus.OK).body("UserTopic wurde erstellt");
        }
    }

    @PutMapping("/usertopics/update/{id}")
    public UserTopic updateUserTopic(@PathVariable Long id, @RequestBody UserTopic userTopicRequest) {
        return userTopicService.updateUserTopic(userTopicRequest, id);
    }

    @DeleteMapping("/usertopics/delete/{id}")
    public ResponseEntity<?> deleteUserTopic(@PathVariable Long id) {
        return userTopicService.deleteUserTopic(id);
    }
}
