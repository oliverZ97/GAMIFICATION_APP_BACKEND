package app.restservice.apprestservice.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import app.restservice.apprestservice.Entities.Friend;
import app.restservice.apprestservice.Services.FriendService;

import javax.validation.Valid;
import java.util.List;

@RestController
public class FriendController {

    @Autowired
    private FriendService friendService;

    @GetMapping("/friends/get/{id}")
    public Friend getFriend(@PathVariable Long id) {
        return friendService.getFriend(id);
    }

    @GetMapping("/friends/getAll")
    public List<Friend> getAllFriends() {
        return friendService.getAllFriends();
    }

    @PostMapping("/friends/set")
    public ResponseEntity<String> setTest(@Valid @RequestBody Friend friend, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors().toString());
        } else {
            friendService.setFriend(friend);
            return ResponseEntity.status(HttpStatus.OK).body("Friend wurde erstellt");
        }
    }

    @PutMapping("/friends/update/{id}")
    public Friend updateFriend(@PathVariable Long id, @RequestBody Friend friendRequest) {
        return friendService.updateFriend(friendRequest, id);
    }

    @DeleteMapping("/friends/delete/{id}")
    public ResponseEntity<?> deleteFriend(@PathVariable Long id) {
        return friendService.deleteFriend(id);
    }
}

