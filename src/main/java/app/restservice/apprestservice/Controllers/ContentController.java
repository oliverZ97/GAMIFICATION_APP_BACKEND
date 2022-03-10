package app.restservice.apprestservice.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import app.restservice.apprestservice.Entities.Content;
import app.restservice.apprestservice.Services.ContentService;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ContentController {

    @Autowired
    private ContentService contentService;

    @GetMapping("/contents/get/{id}")
    public Content getContent(@PathVariable Long id) {
        return contentService.getContent(id);
    }

    @GetMapping("/contents/getAll")
    public List<Content> getAllContents() {
        return contentService.getAllContents();
    }

    @PostMapping("/contents/set")
    public ResponseEntity<String> setContent(@Valid @RequestBody Content content, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors().toString());
        } else {
            contentService.setContent(content);
            return ResponseEntity.status(HttpStatus.OK).body("Content wurde erstellt");
        }
    }

    @PutMapping("/contents/update/{id}")
    public Content updateContent(@PathVariable Long id, @RequestBody Content contentRequest) {
        return contentService.updateContent(contentRequest, id);
    }

    @DeleteMapping("/contents/delete/{id}")
    public ResponseEntity<?> deleteContent(@PathVariable Long id) {
        return contentService.deleteContent(id);
    }
}

