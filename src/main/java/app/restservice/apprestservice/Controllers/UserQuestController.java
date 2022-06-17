package app.restservice.apprestservice.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import app.restservice.apprestservice.Entities.Content;
import app.restservice.apprestservice.Entities.ContentQuestHelper;
import app.restservice.apprestservice.Entities.UserQuest;
import app.restservice.apprestservice.Entities.UserQuestHelper;
import app.restservice.apprestservice.Services.UserQuestService;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserQuestController {

    @Autowired
    private UserQuestService userQuestService;

    @GetMapping("/userquests/get/{id}")
    public UserQuest getUserQuest(@PathVariable Long id) {
        return userQuestService.getUserQuest(id);
    }

    @GetMapping("/userquests/getAll")
    public List<UserQuest> getAllUserQuests() {
        return userQuestService.getAllUserQuests();
    }

    @GetMapping("/userquests/getActiveUserQuests/{id}")
    public List<UserQuestHelper> getActiveUserQuests(@PathVariable Long id) {
        return userQuestService.getActiveUserQuestsByUserId(id);
    }

    @PostMapping("/userquests/checkContentForUserQuest")
    public List<UserQuestHelper> checkContentForUserQuest(@Valid @RequestBody ContentQuestHelper content,
            BindingResult result) {
        return userQuestService.checkContentForUserQuest(content);
    }

    @GetMapping("/userquests/setInitialUserQuests/{id}")
    public void setInitialUserQuests(@PathVariable Long id) {
        userQuestService.addNewUserQuestSet(id, 1);
        userQuestService.addNewUserQuestSet(id, 2);
        userQuestService.addNewUserQuestSet(id, 3);
    }

    @GetMapping("/userquests/setInitialUserQuestsByType/{id}/{type}")
    public void setInitialUserQuests(@PathVariable Long id, @PathVariable int type) {
        userQuestService.addNewUserQuestSet(id, type);

    }

    @PostMapping("/userquests/set")
    public ResponseEntity<String> setUserQuest(@Valid @RequestBody UserQuest userQuest, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors().toString());
        } else {
            userQuestService.setUserQuest(userQuest);
            return ResponseEntity.status(HttpStatus.OK).body("UserQuest wurde erstellt");
        }
    }

    @PutMapping("/userquests/update/{id}")
    public UserQuest updateUserQuest(@PathVariable Long id, @RequestBody UserQuest userQuestRequest) {
        System.out.println(userQuestRequest);
        return userQuestService.updateUserQuest(userQuestRequest, id);
    }

    @DeleteMapping("/userquests/delete/{id}")
    public ResponseEntity<?> deleteUserQuest(@PathVariable Long id) {
        return userQuestService.deleteUserQuest(id);
    }
}
