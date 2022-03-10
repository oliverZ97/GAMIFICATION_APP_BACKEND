package app.restservice.apprestservice.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import app.restservice.apprestservice.Entities.Experience;
import app.restservice.apprestservice.Services.ExperienceService;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ExperienceController {

    @Autowired
    private ExperienceService experienceService;

    @GetMapping("/experiences/get/{id}")
    public Experience getTest(@PathVariable Long id) {
        return experienceService.getExperience(id);
    }

    @GetMapping("/experiences/getAll")
    public List<Experience> getAllExperiences() {
        return experienceService.getAllExperiences();
    }

    @PostMapping("/experiences/set")
    public ResponseEntity<String> setExperience(@Valid @RequestBody Experience experience, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors().toString());
        } else {
            experienceService.setExperience(experience);
            return ResponseEntity.status(HttpStatus.OK).body("Experience wurde erstellt");
        }
    }

    @PutMapping("/experiences/update/{id}")
    public Experience updateExperience(@PathVariable Long id, @RequestBody Experience experienceRequest) {
        return experienceService.updateExperience(experienceRequest, id);
    }

    @DeleteMapping("/experiences/delete/{id}")
    public ResponseEntity<?> deleteExperience(@PathVariable Long id) {
        return experienceService.deleteExperience(id);
    }
}

