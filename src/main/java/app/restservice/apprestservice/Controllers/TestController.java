package app.restservice.apprestservice.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import app.restservice.apprestservice.Entities.Test;
import app.restservice.apprestservice.Services.TestService;

import javax.validation.Valid;
import java.util.List;

@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/test/get/{id}")
    public Test getTest(@PathVariable Long id) {
        return testService.getTest(id);
    }

    @GetMapping("/test/getAll")
    public List<Test> getAllTests() {
        return testService.getAllTests();
    }

    @PostMapping("/test/set")
    public ResponseEntity<String> setTest(@Valid @RequestBody Test test, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors().toString());
        } else {
            testService.setTest(test);
            return ResponseEntity.status(HttpStatus.OK).body("Test wurde erstellt");
        }
    }

    @PutMapping("/test/update/{id}")
    public Test updateTest(@PathVariable Long id, @RequestBody Test TestRequest) {
        return testService.updateTest(TestRequest, id);
    }

    @DeleteMapping("/test/delete/{id}")
    public ResponseEntity<?> deleteTest(@PathVariable Long id) {
        return testService.deleteTest(id);
    }
}

