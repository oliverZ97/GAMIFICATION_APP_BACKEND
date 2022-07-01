package app.restservice.apprestservice.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import app.restservice.apprestservice.Entities.Content;
import app.restservice.apprestservice.Entities.ContentAggregationHelper;
import app.restservice.apprestservice.Entities.DashboardEntry;
import app.restservice.apprestservice.Entities.Topic;
import app.restservice.apprestservice.Services.ContentService;
import app.restservice.apprestservice.Services.TopicService;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ContentController {

    @Autowired
    private ContentService contentService;

    @Autowired
    private TopicService topicService;

    @GetMapping("/contents/get/{id}")
    public Content getContent(@PathVariable Long id) {
        return contentService.getContent(id);
    }

    @GetMapping("/contents/getAll")
    public List<Content> getAllContents() {
        return contentService.getAllContents();
    }

    @GetMapping("/contents/getbytopicid/{id}")
    public List<Content> getContentByTopicId(@PathVariable Long id) {
        return contentService.getContentByTopicId(id);
    }

    @GetMapping("/contents/getrandombytopicid/{id}")
    public List<Content> getRandomContentByTopicId(@PathVariable Long id) {
        return contentService.getRandomContentByTopicId(id);
    }

    @GetMapping("/contents/getdashboardtopiccontent/{id}")
    public List<DashboardEntry> getDashboardTopicContent(@PathVariable Long id) {
        return contentService.getDashboardTopicContent(id);
    }

    @GetMapping("/contents/getdashboardcategorycontent/{id}")
    public List<DashboardEntry> getDashboardCategoryContent(@PathVariable Long id) {
        return contentService.getDashboardCategoryContent(id);
    }

    @GetMapping("/contents/getdashboardfavouritecontent/{id}")
    public DashboardEntry getDashboardFavouriteContent(@PathVariable Long id) {
        return contentService.getDashboardFavouriteContent(id);
    }

    @GetMapping("/contents/getbycategoryid/{id}")
    public List<Content> getContentByCategoryId(@PathVariable Long id) {
        return contentService.getContentByCategoryId(id);
    }

    @PostMapping("/contents/set")
    public ResponseEntity<String> setContent(@Valid @RequestBody ContentAggregationHelper contentRequest,
            BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors().toString());
        } else {
            String topicIdsString = contentRequest.getTopic_IDs();
            String[] substrings = topicIdsString.replace("[", "").replace("]", "").split(",");
            List<Topic> topics = new ArrayList<>();
            for (int i = 0; i < substrings.length; i++) {
                Topic topic = topicService.getTopic(Long.parseLong(substrings[i]));
                topics.add(topic);
            }
            Content content = new Content();
            content.setAuthor(contentRequest.getAuthor());
            content.setContent(contentRequest.getContent());
            content.setExperience(contentRequest.getExperience());
            content.setNumber_of_words(contentRequest.getNumber_of_words());
            content.setSource(contentRequest.getSource());
            content.setTitle(contentRequest.getTitle());
            content.setTopics(topics);
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
