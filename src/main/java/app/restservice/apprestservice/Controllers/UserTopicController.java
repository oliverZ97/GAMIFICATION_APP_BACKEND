package app.restservice.apprestservice.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import app.restservice.apprestservice.Entities.UserTopic;
import app.restservice.apprestservice.Services.UserTopicService;

import javax.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<Object> setUserTopic(@Valid @RequestBody UserTopic userTopic, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors().toString());
        } else {
            UserTopic resultObj = userTopicService.setUserTopic(userTopic);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("message", "OK");
            map.put("status", HttpStatus.OK);
            map.put("data", resultObj);
            return new ResponseEntity<Object>(map, HttpStatus.OK);
        }
    }

    @PutMapping("/usertopics/update/{id}")
    public ResponseEntity<Object> updateUserTopic(@PathVariable Long id, @RequestBody UserTopic userTopicRequest) {
        UserTopic resultObj = userTopicService.updateUserTopic(userTopicRequest, id);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", "OK");
        map.put("status", HttpStatus.OK);
        map.put("data", resultObj);
        return new ResponseEntity<Object>(map, HttpStatus.OK);
    }

    @DeleteMapping("/usertopics/delete/{id}")
    public ResponseEntity<?> deleteUserTopic(@PathVariable Long id) {
        return userTopicService.deleteUserTopic(id);
    }
}
