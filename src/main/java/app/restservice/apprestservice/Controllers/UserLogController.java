package app.restservice.apprestservice.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import app.restservice.apprestservice.Entities.UserLog;
import app.restservice.apprestservice.Entities.UserLogSorted;
import app.restservice.apprestservice.Services.UserLogService;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserLogController {

    @Autowired
    private UserLogService userLogService;

    @GetMapping("/userlogs/get/{id}")
    public UserLog getUserLog(@PathVariable Long id) {
        return userLogService.getUserLog(id);
    }

    @GetMapping("/userlogs/getAll")
    public List<UserLog> getAllUserLogs() {
        return userLogService.getAllUserLogs();
    }

    @GetMapping("/userlogs/getByUserId/{id}")
    public List<UserLog> getUserLogsByUserId(@PathVariable Long id) {
        return userLogService.getByUserId(id);
    }

    @GetMapping("/userlogs/getSortedLogsByUserId/{id}")
    public UserLogSorted getSortedLogsByUserId(@PathVariable Long id) {
        return userLogService.getSortedUserLogs(id);
    }

    @GetMapping("/userlogs/getByUserIdAndType/{id}/{type}")
    public List<UserLog> getUserLogsByUserIdAndType(@PathVariable Long id, @PathVariable int type) {
        return userLogService.getByUserIdAndType(id, type);
    }

    @PostMapping("/userlogs/set")
    public ResponseEntity<String> setUserLog(@Valid @RequestBody UserLog userLog, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors().toString());
        } else {
            userLogService.setUserLog(userLog);
            return ResponseEntity.status(HttpStatus.OK).body("UserLog wurde erstellt");
        }
    }

    @PutMapping("/userlogs/update/{id}")
    public UserLog updateUserLog(@PathVariable Long id, @RequestBody UserLog userLogRequest) {
        return userLogService.updateUserLog(userLogRequest, id);
    }

    @DeleteMapping("/userlogs/delete/{id}")
    public ResponseEntity<?> deleteUserLog(@PathVariable Long id) {
        return userLogService.deleteUserLog(id);
    }
}
