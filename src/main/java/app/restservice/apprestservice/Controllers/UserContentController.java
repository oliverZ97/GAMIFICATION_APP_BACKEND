package app.restservice.apprestservice.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import app.restservice.apprestservice.Entities.UserContent;
import app.restservice.apprestservice.Services.UserContentService;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserContentController {

    @Autowired
    private UserContentService userContentService;

    @GetMapping("/usercontents/get/{id}")
    public UserContent getUserContent(@PathVariable Long id) {
        return userContentService.getUserContent(id);
    }

    @GetMapping("/usercontents/getAll")
    public List<UserContent> getAllUserContents() {
        return userContentService.getAllUserContents();
    }

    @PostMapping("/usercontents/set")
    public ResponseEntity<String> setUserContent(@Valid @RequestBody UserContent userContent, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors().toString());
        } else {
            userContentService.setUserContent(userContent);
            return ResponseEntity.status(HttpStatus.OK).body("UserContent wurde erstellt");
        }
    }

    @PutMapping("/usercontents/update/{id}")
    public UserContent updateUserContent(@PathVariable Long id, @RequestBody UserContent userContentRequest) {
        return userContentService.updateUserContent(userContentRequest, id);
    }

    @DeleteMapping("/usercontents/delete/{id}")
    public ResponseEntity<?> deleteUserContent(@PathVariable Long id) {
        return userContentService.deleteUserContent(id);
    }
}

