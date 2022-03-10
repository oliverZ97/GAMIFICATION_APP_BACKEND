package app.restservice.apprestservice.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import app.restservice.apprestservice.Entities.UserAchievement;
import app.restservice.apprestservice.Services.UserAchievementService;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserAchievementController {

    @Autowired
    private UserAchievementService userAchievementService;

    @GetMapping("/userachievements/get/{id}")
    public UserAchievement getUserAchievement(@PathVariable Long id) {
        return userAchievementService.getUserAchievement(id);
    }

    @GetMapping("/userachievements/getAll")
    public List<UserAchievement> getAllUserAchievements() {
        return userAchievementService.getAllUserAchievements();
    }

    @PostMapping("/userachievements/set")
    public ResponseEntity<String> setUserAchievement(@Valid @RequestBody UserAchievement userAchievement, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors().toString());
        } else {
            userAchievementService.setUserAchievement(userAchievement);
            return ResponseEntity.status(HttpStatus.OK).body("UserAchievement wurde erstellt");
        }
    }

    @PutMapping("/userachievements/update/{id}")
    public UserAchievement updateUserAchievement(@PathVariable Long id, @RequestBody UserAchievement userAchievementRequest) {
        return userAchievementService.updateUserAchievement(userAchievementRequest, id);
    }

    @DeleteMapping("/userachievements/delete/{id}")
    public ResponseEntity<?> deleteUserAchievement(@PathVariable Long id) {
        return userAchievementService.deleteUserAchievement(id);
    }
}

