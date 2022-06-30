package app.restservice.apprestservice.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import app.restservice.apprestservice.CopyPropertiesOfEntity;
import app.restservice.apprestservice.Entities.Content;
import app.restservice.apprestservice.Entities.UserContent;
import app.restservice.apprestservice.Exceptions.ResourceNotFoundException;
import app.restservice.apprestservice.Repositories.UserContentRepository;

@Service
public class UserContentService {

    @Autowired
    private UserContentRepository userContentRepository;

    @Autowired
    private UserAchievementService userAchievementService;

    @Autowired
    private ContentService contentService;

    private CopyPropertiesOfEntity copyPropertiesOfEntity;

    public UserContent getUserContent(Long id) {
        if (userContentRepository.findById(id).isPresent()) {
            return userContentRepository.findById(id).get();
        } else {
            throw new ResourceNotFoundException("no user content found at id" + id);
        }
    }

    public int getUserContentWordCount(Long id, int number_of_words) {
        List<UserContent> userContents = getUserContentsByUserId(id);
        int counter = 0;
        for (int i = 0; i < userContents.size(); i++) {
            Content c = contentService.getContent(userContents.get(i).getContent_ID());
            if (c.getNumber_of_words() >= number_of_words) {
                counter++;
            }
        }
        return counter;
    }

    public Boolean checkRewardStatus(Long content_id, Long user_id) {
        UserContent check = userContentRepository.checkRewardStatus(content_id, user_id);
        if (check == null || !check.isGetReward()) {
            return false;
        } else {
            return true;
        }
    }

    public int getUserContentCount(Long user_id) {
        return userContentRepository.getUserContentCount(user_id);
    }

    public List<UserContent> getAllUserContents() {
        return userContentRepository.findAll();
    }

    public List<UserContent> getUserContentsByUserId(Long user_id) {
        return userContentRepository.getUserContentsByUserId(user_id);
    }

    public UserContent setUserContent(UserContent userContent) {
        UserContent obj = userContentRepository.save(userContent);
        userAchievementService.handleUserAchievementByKey(userContent.getUser_ID(), "content_count");
        userAchievementService.handleUserAchievementByKey(userContent.getUser_ID(), "wordcount");
        userAchievementService.handleTopicCountKey(userContent.getUser_ID(), "topic_count", obj.getContent_ID());
        return obj;
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
