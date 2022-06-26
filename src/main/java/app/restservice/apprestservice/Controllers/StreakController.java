package app.restservice.apprestservice.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import app.restservice.apprestservice.Entities.Streak;
import app.restservice.apprestservice.Services.StreakService;

import javax.validation.Valid;
import java.util.List;

@RestController
public class StreakController {

    @Autowired
    private StreakService streakService;

    @GetMapping("/streaks/get/{id}")
    public Streak getStreak(@PathVariable Long id) {
        return streakService.getStreak(id);
    }

    @GetMapping("/streaks/getAll")
    public List<Streak> getAllStreaks() {
        return streakService.getAllStreaks();
    }

    @GetMapping("/streaks/getByUserId/{id}")
    public List<Streak> getStreaksByUserId(@PathVariable Long id) {
        return streakService.getStreaksByUserId(id);
    }

    @PostMapping("/streaks/set")
    public ResponseEntity<String> setStreak(@Valid @RequestBody Streak achievement, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors().toString());
        } else {
            streakService.setStreak(achievement);
            return ResponseEntity.status(HttpStatus.OK).body("Streak wurde erstellt");
        }
    }

    @PutMapping("/streaks/update/{id}")
    public Streak updateStreak(@PathVariable Long id, @RequestBody Streak achievementRequest) {
        return streakService.updateStreak(achievementRequest, id);
    }

    @DeleteMapping("/streaks/delete/{id}")
    public ResponseEntity<?> deleteStreak(@PathVariable Long id) {
        return streakService.deleteStreak(id);
    }
}
