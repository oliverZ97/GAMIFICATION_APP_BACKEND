package app.restservice.apprestservice.Services;

import org.springframework.stereotype.Service;

import app.restservice.apprestservice.CopyPropertiesOfEntity;
import app.restservice.apprestservice.Entities.UserTopic;
import app.restservice.apprestservice.Exceptions.ResourceNotFoundException;
import app.restservice.apprestservice.Repositories.UserTopicRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Service
public class UserTopicService {

    @Autowired
    private UserTopicRepository userTopicRepository;

    private CopyPropertiesOfEntity copyPropertiesOfEntity;

    public UserTopic getUserTopic(Long id) {
        if (userTopicRepository.findById(id).isPresent()) {
            return userTopicRepository.findById(id).get();
        } else {
            throw new ResourceNotFoundException("no user topic found at id" + id);
        }
    }

    public List<UserTopic> getUserTopicsByUserID(Long id) {
        return userTopicRepository.getUserTopicsByUserID(id);
    }

    public List<UserTopic> getUserFavouriteTopicsByUserID(Long id) {
        return userTopicRepository.getUserFavouriteTopicsByUserID(id);
    }

    public List<UserTopic> getAllUserTopics() {
        return userTopicRepository.findAll();
    }

    public UserTopic setUserTopic(UserTopic userTopic) {
        return userTopicRepository.save(userTopic);
    }

    public UserTopic updateUserTopic(UserTopic userTopicRequest, long id) {
        UserTopic userTopic = getUserTopic(id);
        copyPropertiesOfEntity.copyNonNullProperties(userTopicRequest, userTopic);
        return userTopicRepository.save(userTopic);
    }

    public ResponseEntity<?> deleteUserTopic(long id) {
        return userTopicRepository.findById(id)
                .map(userTopic -> {
                    userTopicRepository.delete(userTopic);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("userTopic not found with id " + id));
    }

}
