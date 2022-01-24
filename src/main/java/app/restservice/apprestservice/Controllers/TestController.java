package app.restservice.apprestservice.Controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.*;

import app.restservice.apprestservice.Entities.TestEntity;
import app.restservice.apprestservice.Services.TestService;

@RestController
public class TestController {

    @Autowired
    TestService testService = new TestService();

    @GetMapping("/")
    public String index() {
		return "Greetings from Spring Boot!";
	}

    @GetMapping("/test/get/{id}")
    public TestEntity getTestEntity(@PathVariable Long id) {
        return testService.getTest(id);
    }

      @GetMapping("/test/getAll")
    public Page<TestEntity> getAllTests(Pageable pageable) {
        return testService.getAllTests(pageable);
    }

    @PostMapping("/test/set")
    public ResponseEntity<String> setTest(@Valid @RequestBody TestEntity test, BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors().toString());
        } else {
            testService.setTest(test);
            return ResponseEntity.status(HttpStatus.OK).body("Unit wurde erfolgreich gespeichert");
        }
    }

    @PutMapping("/test/update/{id}")
    public TestEntity updateTest(@PathVariable Long id, @RequestBody TestEntity testRequest) {
        return testService.updateTest(id, testRequest);
    }

    @DeleteMapping("/test/delete/{id}")
    public ResponseEntity<?> deleteTest(@PathVariable Long id) {
        return testService.deleteTest(id);
    }

}

