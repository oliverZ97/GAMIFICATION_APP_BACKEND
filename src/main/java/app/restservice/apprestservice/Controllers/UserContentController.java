package app.restservice.apprestservice.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import app.restservice.apprestservice.Entities.UserContent;
import app.restservice.apprestservice.Services.UserContentService;

import javax.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<Object> setUserContent(@Valid @RequestBody UserContent userContent, BindingResult result) {
        System.out.println(userContent);
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors().toString());
        } else {
            UserContent resultObj = userContentService.setUserContent(userContent);
            System.out.println(resultObj);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("message", "OK");
            map.put("status", HttpStatus.OK);
            map.put("data", resultObj);
            return new ResponseEntity<Object>(map, HttpStatus.OK);
        }
    }

    @PutMapping("/usercontents/update/{id}")
    public ResponseEntity<Object> updateUserContent(@PathVariable Long id,
            @RequestBody UserContent userContentRequest) {
        UserContent resultObj = userContentService.updateUserContent(userContentRequest, id);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", "OK");
        map.put("status", HttpStatus.OK);
        map.put("data", resultObj);
        return new ResponseEntity<Object>(map, HttpStatus.OK);
    }

    @DeleteMapping("/usercontents/delete/{id}")
    public ResponseEntity<?> deleteUserContent(@PathVariable Long id) {
        return userContentService.deleteUserContent(id);
    }
}
