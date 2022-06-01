package app.restservice.apprestservice.Services;

import org.springframework.stereotype.Service;

import app.restservice.apprestservice.CopyPropertiesOfEntity;
import app.restservice.apprestservice.Entities.Content;
import app.restservice.apprestservice.Entities.Topic;
import app.restservice.apprestservice.Exceptions.ResourceNotFoundException;
import app.restservice.apprestservice.Repositories.ContentRepository;
import app.restservice.apprestservice.Repositories.TopicRepository;

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

    public List<Content> getRandomContentByTopicId(Long id) {
        if (topicRepository.findById(id).isPresent()) {
            return contentRepository.getRandomContentByTopicId(id);
        } else {
            throw new ResourceNotFoundException("no topic found with id" + id);
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
