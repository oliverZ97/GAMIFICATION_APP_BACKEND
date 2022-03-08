package app.restservice.apprestservice.Services;

import org.springframework.stereotype.Service;

import app.restservice.apprestservice.CopyPropertiesOfEntity;
import app.restservice.apprestservice.Entities.Content;
import app.restservice.apprestservice.Exceptions.ResourceNotFoundException;
import app.restservice.apprestservice.Repositories.ContentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Service
public class ContentService {
   
    @Autowired
    private ContentRepository contentRepository;

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
