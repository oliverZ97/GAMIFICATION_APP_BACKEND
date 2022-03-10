package app.restservice.apprestservice.Services;

import org.springframework.stereotype.Service;

import app.restservice.apprestservice.CopyPropertiesOfEntity;
import app.restservice.apprestservice.Entities.UserContent;
import app.restservice.apprestservice.Exceptions.ResourceNotFoundException;
import app.restservice.apprestservice.Repositories.UserContentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Service
public class UserContentService {
   
    @Autowired
    private UserContentRepository userContentRepository;

    private CopyPropertiesOfEntity copyPropertiesOfEntity;

    public UserContent getUserContent(Long id) {
        if (userContentRepository.findById(id).isPresent()) {
            return userContentRepository.findById(id).get();
        } else {
            throw new ResourceNotFoundException("no user content found at id" + id);
        }
    }

    public List<UserContent> getAllUserContents() {
        return userContentRepository.findAll();
    }

    public UserContent setUserContent(UserContent userContent) {
        return userContentRepository.save(userContent);
    }

    public UserContent updateUserContent(UserContent userContentRequest, long id) {
        UserContent userContent = getUserContent(id);
        copyPropertiesOfEntity.copyNonNullProperties(userContentRequest, userContent);
        return userContentRepository.save(userContent);
    }


    public ResponseEntity<?> deleteUserContent(long id) {
        return userContentRepository.findById(id)
                .map(userContent -> {
                    userContentRepository.delete(userContent);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("userContent not found with id " + id));
    }

}
