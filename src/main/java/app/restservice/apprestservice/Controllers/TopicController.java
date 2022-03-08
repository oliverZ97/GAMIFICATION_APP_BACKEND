package app.restservice.apprestservice.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import app.restservice.apprestservice.Entities.Topic;
import app.restservice.apprestservice.Services.TopicService;

import javax.validation.Valid;
import java.util.List;

@RestController
public class TopicController {

    @Autowired
    private TopicService topicService;

    @GetMapping("/topic/get/{id}")
    public Topic getTest(@PathVariable Long id) {
        return topicService.getTopic(id);
    }

    @GetMapping("/topic/getAll")
    public List<Topic> getAllTopics() {
        return topicService.getAllTopics();
    }

    @PostMapping("/topic/set")
    public ResponseEntity<String> setTest(@Valid @RequestBody Topic topic, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors().toString());
        } else {
            topicService.setTopic(topic);
            return ResponseEntity.status(HttpStatus.OK).body("Topic wurde erstellt");
        }
    }

    @PutMapping("/topic/update/{id}")
    public Topic updateTopic(@PathVariable Long id, @RequestBody Topic topicRequest) {
        return topicService.updateTopic(topicRequest, id);
    }

    @DeleteMapping("/topic/delete/{id}")
    public ResponseEntity<?> deleteTopic(@PathVariable Long id) {
        return topicService.deleteTopic(id);
    }
}

