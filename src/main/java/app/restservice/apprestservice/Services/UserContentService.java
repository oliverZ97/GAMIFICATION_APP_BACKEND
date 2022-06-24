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
    private ContentService contentService;

    private CopyPropertiesOfEntity copyPropertiesOfEntity;

    public UserContent getUserContent(Long id) {
        if (userContentRepository.findById(id).isPresent()) {
            return userContentRepository.findById(id).get();
        } else {
            throw new ResourceNotFoundException("no user content found at id" + id);
        }
    }

    public Boolean checkRewardStatus(Long content_id, Long user_id) {
        UserContent check = userContentRepository.checkRewardStatus(content_id, user_id);
        if (check == null || !check.isGetReward()) {
            return false;
        } else {
            return true;
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

    // public int getReadContentsByTopicAndUser(Long user_id) {
    // List<UserContent> rewardedContents =
    // userContentRepository.getUserReadContents(user_id);
    // int counter = 0;
    // String ids = "";
    // for (int i = 0; i < rewardedContents.size(); i++) {
    // Content c =
    // contentService.getContent(rewardedContents.get(i).getContent_ID());

    // if (c.getTopic_IDs().contains(topic_id.toString())) {
    // counter++;
    // }
    // }
    // return counter;
    // }

}
