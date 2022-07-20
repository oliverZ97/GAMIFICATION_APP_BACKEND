package app.restservice.apprestservice.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import app.restservice.apprestservice.Entities.TimeLog;
import app.restservice.apprestservice.Services.TimeLogService;

import javax.validation.Valid;
import java.util.List;

@RestController
public class TimeLogController {

    @Autowired
    private TimeLogService timeLogService;

    @GetMapping("/timelogs/get/{id}")
    public TimeLog getTimeLog(@PathVariable Long id) {
        return timeLogService.getTimeLog(id);
    }

    @GetMapping("/timelogs/getAll")
    public List<TimeLog> getAllTimeLogs() {
        return timeLogService.getAllTimeLogs();
    }

    @GetMapping("/timelogs/getTimeLogByType/{type}")
    public TimeLog getTimeLogByType(@PathVariable int type) {
        return timeLogService.getTimeLogByType(type);
    }

    @PostMapping("/timelogs/set")
    public ResponseEntity<String> setTimeLog(@Valid @RequestBody TimeLog timeLog, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors().toString());
        } else {
            timeLogService.setTimeLog(timeLog);
            return ResponseEntity.status(HttpStatus.OK).body("TimeLog wurde erstellt");
        }
    }

    @PutMapping("/timelogs/update/{id}")
    public TimeLog updateTimeLog(@PathVariable Long id, @RequestBody TimeLog timeLogRequest) {
        return timeLogService.updateTimeLog(timeLogRequest, id);
    }

    @DeleteMapping("/timelogs/delete/{id}")
    public ResponseEntity<?> deleteTimeLog(@PathVariable Long id) {
        return timeLogService.deleteTimeLog(id);
    }
}
