package app.restservice.apprestservice.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import app.restservice.apprestservice.Entities.TopicContent;
import app.restservice.apprestservice.Services.TopicContentService;

import javax.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TopicContentController {

    @Autowired
    private TopicContentService topicContentService;

    @GetMapping("/topiccontents/get/{id}")
    public TopicContent getTopicContent(@PathVariable Long id) {
        return topicContentService.getTopicContent(id);
    }

    @GetMapping("/topiccontents/getAll")
    public List<TopicContent> getAllTopicContents() {
        return topicContentService.getAllTopicContents();
    }

    @GetMapping("/topiccontents/getallbytopicid/{id}")
    public List<TopicContent> getAllByTopicId(@PathVariable long id) {
        return topicContentService.getAllByTopicId(id);
    }

    @GetMapping("/topiccontents/getallbycontentid/{id}")
    public List<TopicContent> getAllByContentId(@PathVariable long id) {
        return topicContentService.getAllByContentId(id);
    }

    @PostMapping("/topiccontents/set")
    public ResponseEntity<Object> setTopicContent(@Valid @RequestBody TopicContent userTopic, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors().toString());
        } else {
            TopicContent resultObj = topicContentService.setTopicContent(userTopic);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("message", "OK");
            map.put("status", HttpStatus.OK);
            map.put("data", resultObj);
            return new ResponseEntity<Object>(map, HttpStatus.OK);
        }
    }

    @PutMapping("/topiccontents/update/{id}")
    public ResponseEntity<Object> updateTopicContent(@PathVariable Long id,
            @RequestBody TopicContent userTopicRequest) {
        TopicContent resultObj = topicContentService.updateTopicContent(userTopicRequest, id);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", "OK");
        map.put("status", HttpStatus.OK);
        map.put("data", resultObj);
        return new ResponseEntity<Object>(map, HttpStatus.OK);
    }

    @DeleteMapping("/topiccontents/delete/{id}")
    public ResponseEntity<?> deleteTopicContent(@PathVariable Long id) {
        return topicContentService.deleteTopicContent(id);
    }
}
