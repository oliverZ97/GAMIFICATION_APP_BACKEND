package app.restservice.apprestservice.Controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import app.restservice.apprestservice.Entities.Level;
import app.restservice.apprestservice.Services.LevelService;

@RestController
public class LevelController {
    @Autowired
    private LevelService friendService;

    @GetMapping("/levels/get/{id}")
    public Level getFriend(@PathVariable Long id) {
        return friendService.getLevel(id);
    }

    @GetMapping("/levels/getAll")
    public List<Level> getAllFriends() {
        return friendService.getAllLevels();
    }

    @PostMapping("/levels/set")
    public ResponseEntity<String> setTest(@Valid @RequestBody Level friend, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors().toString());
        } else {
            friendService.setLevel(friend);
            return ResponseEntity.status(HttpStatus.OK).body("Level wurde erstellt");
        }
    }

    @PutMapping("/levels/update/{id}")
    public Level updateFriend(@PathVariable Long id, @RequestBody Level friendRequest) {
        return friendService.updateLevel(friendRequest, id);
    }

    @DeleteMapping("/levels/delete/{id}")
    public ResponseEntity<?> deleteFriend(@PathVariable Long id) {
        return friendService.deleteLevel(id);
    }
}
