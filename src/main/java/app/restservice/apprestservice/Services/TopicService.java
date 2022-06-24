package app.restservice.apprestservice.Services;

import org.springframework.stereotype.Service;

import app.restservice.apprestservice.CopyPropertiesOfEntity;
import app.restservice.apprestservice.Entities.Topic;
import app.restservice.apprestservice.Exceptions.ResourceNotFoundException;
import app.restservice.apprestservice.Repositories.CategoryRepository;
import app.restservice.apprestservice.Repositories.ContentRepository;
import app.restservice.apprestservice.Repositories.TopicRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private CopyPropertiesOfEntity copyPropertiesOfEntity;

    public Topic getTopic(Long id) {
        if (topicRepository.findById(id).isPresent()) {
            return topicRepository.findById(id).get();
        } else {
            throw new ResourceNotFoundException("no topic found at id" + id);
        }
    }

    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    public List<Topic> getTopicsByCategoryID(Long id) {
        if (categoryRepository.findById(id).isPresent()) {
            return topicRepository.getTopicsByCategoryId(id);
        } else {
            throw new ResourceNotFoundException("no category found at id" + id);
        }
    }

    public List<Topic> getTopicsByIdString(String topic_ids) {
        List<Topic> result = new ArrayList<Topic>();
        String tmp = topic_ids.replace("[", "").replace("]", "");
        String[] ids = tmp.split(",");
        for (int i = 0; i < ids.length; i++) {
            Topic t = getTopic(Long.parseLong(ids[i]));
            result.add(t);
        }
        return result;
    }

    public Topic setTopic(Topic topic) {
        return topicRepository.save(topic);
    }

    public Topic updateTopic(Topic topicRequest, long id) {
        Topic topic = getTopic(id);
        copyPropertiesOfEntity.copyNonNullProperties(topicRequest, topic);
        return topicRepository.save(topic);
    }

    public ResponseEntity<?> deleteTopic(long id) {
        return topicRepository.findById(id)
                .map(topic -> {
                    topicRepository.delete(topic);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("topic not found with id " + id));
    }

}
