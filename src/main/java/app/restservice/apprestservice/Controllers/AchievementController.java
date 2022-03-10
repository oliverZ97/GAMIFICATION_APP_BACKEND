package app.restservice.apprestservice.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import app.restservice.apprestservice.Entities.Achievement;
import app.restservice.apprestservice.Services.AchievementService;

import javax.validation.Valid;
import java.util.List;

@RestController
public class AchievementController {

    @Autowired
    private AchievementService achievementService;

    @GetMapping("/achievements/get/{id}")
    public Achievement getAchievement(@PathVariable Long id) {
        return achievementService.getAchievement(id);
    }

    @GetMapping("/achievements/getAll")
    public List<Achievement> getAllAchievements() {
        return achievementService.getAllAchievements();
    }

    @PostMapping("/achievements/set")
    public ResponseEntity<String> setAchievement(@Valid @RequestBody Achievement achievement, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors().toString());
        } else {
            achievementService.setAchievement(achievement);
            return ResponseEntity.status(HttpStatus.OK).body("Achievement wurde erstellt");
        }
    }

    @PutMapping("/achievements/update/{id}")
    public Achievement updateTest(@PathVariable Long id, @RequestBody Achievement achievementRequest) {
        return achievementService.updateAchievement(achievementRequest, id);
    }

    @DeleteMapping("/achievements/delete/{id}")
    public ResponseEntity<?> deleteAchievement(@PathVariable Long id) {
        return achievementService.deleteAchievement(id);
    }
}

