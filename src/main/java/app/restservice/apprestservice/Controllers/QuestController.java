package app.restservice.apprestservice.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import app.restservice.apprestservice.Entities.Quest;
import app.restservice.apprestservice.Services.QuestService;

import javax.validation.Valid;
import java.util.List;

@RestController
public class QuestController {

    @Autowired
    private QuestService questService;

    @GetMapping("/quests/get/{id}")
    public Quest getQuest(@PathVariable Long id) {
        return questService.getQuest(id);
    }

    @GetMapping("/quests/getAll")
    public List<Quest> getAllQuests() {
        return questService.getAllQuests();
    }

    @PostMapping("/quests/set")
    public ResponseEntity<String> setTest(@Valid @RequestBody Quest quest, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors().toString());
        } else {
            questService.setQuest(quest);
            return ResponseEntity.status(HttpStatus.OK).body("Quest wurde erstellt");
        }
    }

    @PutMapping("/quests/update/{id}")
    public Quest updateQuest(@PathVariable Long id, @RequestBody Quest questRequest) {
        return questService.updateQuest(questRequest, id);
    }

    @DeleteMapping("/quests/delete/{id}")
    public ResponseEntity<?> deleteQuest(@PathVariable Long id) {
        return questService.deleteQuest(id);
    }
}

