package app.restservice.apprestservice.Services;

import org.springframework.stereotype.Service;

import app.restservice.apprestservice.CopyPropertiesOfEntity;
import app.restservice.apprestservice.Entities.Content;
import app.restservice.apprestservice.Entities.DashboardEntry;
import app.restservice.apprestservice.Entities.Topic;
import app.restservice.apprestservice.Entities.UserCategory;
import app.restservice.apprestservice.Entities.UserContent;
import app.restservice.apprestservice.Entities.UserTopic;
import app.restservice.apprestservice.Exceptions.ResourceNotFoundException;
import app.restservice.apprestservice.Repositories.CategoryRepository;
import app.restservice.apprestservice.Repositories.ContentRepository;
import app.restservice.apprestservice.Repositories.TopicRepository;
import app.restservice.apprestservice.Repositories.UserCategoryRepository;
import app.restservice.apprestservice.Repositories.UserTopicRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class ContentService {

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserTopicRepository userTopicRepository;

    @Autowired
    private UserContentService userContentService;

    @Autowired
    private UserCategoryRepository userCategoryRepository;

    private CopyPropertiesOfEntity copyPropertiesOfEntity;

    public Content getContent(Long id) {
        if (contentRepository.findById(id).isPresent()) {
            return contentRepository.findById(id).get();
        } else {
            throw new ResourceNotFoundException("no content found at id" + id);
        }
    }

    public List<Content> getAllContents() {
        return contentRepository.findAll();
    }

    public List<Content> getContentByTopicId(Long id) {
        if (topicRepository.findById(id).isPresent()) {
            return contentRepository.getContentByTopicId(id);
        } else {
            throw new ResourceNotFoundException("no topic found with id" + id);
        }
    }

    public List<DashboardEntry> getDashboardTopicContent(Long user_id) {
        List<UserTopic> favourites = userTopicRepository.getUserFavouriteTopicsByUserID(user_id);
        List<DashboardEntry> result = new ArrayList<>();
        for (int i = 0; i < favourites.size(); i++) {
            Long topic_id = favourites.get(i).getTopic_ID();
            String topic_name = topicRepository.getById(topic_id).getName();
            List<Content> randomContents = getRandomContentByTopicId(topic_id);
            DashboardEntry entry = new DashboardEntry(topic_name, randomContents);
            if (randomContents.size() > 0) {
                result.add(entry);
            }

        }
        return result;
    }

    public List<DashboardEntry> getDashboardCategoryContent(Long user_id) {
        List<UserCategory> favourites = userCategoryRepository.getUserFavouriteCategoriesByUserID(user_id);
        List<DashboardEntry> result = new ArrayList<>();
        for (int i = 0; i < favourites.size(); i++) {
            Long category_id = favourites.get(i).getCategory_ID();
            String category_name = categoryRepository.getById(category_id).getName();
            List<Topic> topics = topicRepository.getTopicsByCategoryId(category_id);
            int rdm1 = (int) (Math.random() * topics.size());
            int rdm2 = (int) (Math.random() * topics.size());
            if (topics.size() > 0) {
                List<Content> randomContents = getRandomContentByTopicId(topics.get(rdm1).getId());
                if (topics.size() >= 2) {
                    List<Content> randomContents2 = getRandomContentByTopicId(topics.get(rdm2).getId());
                    randomContents.addAll(randomContents2);
                }

                DashboardEntry entry = new DashboardEntry(category_name, new ArrayList<Content>(
                        new HashSet<Content>(randomContents)));
                if (randomContents.size() > 0) {
                    result.add(entry);
                }
            }

        }
        return result;
    }

    public DashboardEntry getDashboardFavouriteContent(Long user_id) {
        List<UserContent> favourites = userContentService.getUserContentFavouritesByUserId(user_id);
        List<Content> result = new ArrayList<>();
        for (int i = 0; i < favourites.size(); i++) {
            Content c = getContent(favourites.get(i).getContent_ID());
            result.add(c);
        }
        DashboardEntry entry = new DashboardEntry("Favoriten", result);

        return entry;
    }

    public List<Content> getRandomContentByTopicId(Long topic_id) {
        if (topicRepository.findById(topic_id).isPresent()) {
            return contentRepository.getRandomContentByTopicId(topic_id);
        } else {
            throw new ResourceNotFoundException("no topic found with id" + topic_id);
        }
    }

    public List<Content> getContentByCategoryId(Long id) {
        List<Topic> topics = topicRepository.getTopicsByCategoryId(id);
        List<Content> result = new ArrayList<Content>();
        for (int i = 0; i < topics.size(); i++) {
            List<Content> contents = contentRepository.getContentByTopicId(topics.get(i).getId());
            if (contents.size() > 0) {
                result.addAll(contents);
            }
        }
        return new ArrayList<Content>(
                new HashSet<Content>(result));

    }

    public Content setContent(Content content) {
        return contentRepository.save(content);
    }

    public Content updateContent(Content contentRequest, long id) {
        Content content = getContent(id);
        copyPropertiesOfEntity.copyNonNullProperties(contentRequest, content);
        return contentRepository.save(content);
    }

    public ResponseEntity<?> deleteContent(long id) {
        return contentRepository.findById(id)
                .map(content -> {
                    contentRepository.delete(content);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("content not found with id " + id));
    }

}
