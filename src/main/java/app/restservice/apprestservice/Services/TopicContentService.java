package app.restservice.apprestservice.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import app.restservice.apprestservice.CopyPropertiesOfEntity;
import app.restservice.apprestservice.Entities.TopicContent;
import app.restservice.apprestservice.Exceptions.ResourceNotFoundException;
import app.restservice.apprestservice.Repositories.TopicContentRepository;

@Service
public class TopicContentService {

    @Autowired
    private TopicContentRepository topicContentRepository;

    private CopyPropertiesOfEntity copyPropertiesOfEntity;

    public TopicContent getTopicContent(Long id) {
        if (topicContentRepository.findById(id).isPresent()) {
            return topicContentRepository.findById(id).get();
        } else {
            throw new ResourceNotFoundException("no topic content found at id" + id);
        }
    }

    public List<TopicContent> getAllTopicContents() {
        return topicContentRepository.findAll();
    }

    public List<TopicContent> getAllByTopicId(long id) {
        return topicContentRepository.getAllByTopicId(id);
    }

    public List<TopicContent> getAllByContentId(long id) {
        return topicContentRepository.getAllByContentId(id);
    }

    public int getTopicCountForContentId(long id) {
        return topicContentRepository.getTopicCountForContentId(id);
    }

    public TopicContent setTopicContent(TopicContent topicContent) {
        return topicContentRepository.save(topicContent);
    }

    public TopicContent updateTopicContent(TopicContent topicContentRequest, long id) {
        TopicContent topicContent = getTopicContent(id);
        copyPropertiesOfEntity.copyNonNullProperties(topicContentRequest, topicContent);
        return topicContentRepository.save(topicContent);
    }

    public ResponseEntity<?> deleteTopicContent(long id) {
        return topicContentRepository.findById(id)
                .map(topicContent -> {
                    topicContentRepository.delete(topicContent);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("topicContent not found with id " + id));
    }

}
