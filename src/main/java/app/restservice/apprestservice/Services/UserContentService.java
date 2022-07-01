package app.restservice.apprestservice.Services;

import java.util.List;

import org.hibernate.loader.plan.spi.Return;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import app.restservice.apprestservice.CopyPropertiesOfEntity;
import app.restservice.apprestservice.Entities.Content;
import app.restservice.apprestservice.Entities.UserContent;
import app.restservice.apprestservice.Entities.UserContentFavourite;
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

    public List<UserContent> getUserContentFavouritesByUserId(Long user_id) {
        return userContentRepository.getUserContentFavouritesByUserId(user_id);
    }

    public UserContent setUserContent(UserContent userContent) {
        UserContent uc = getUserContentByContentId(userContent.getUser_ID(),
                userContent.getContent_ID());
        UserContent obj = null;
        if (uc == null) {
            obj = userContentRepository.save(userContent);
            userAchievementService.handleUserAchievementByKey(userContent.getUser_ID(), "content_count");
            userAchievementService.handleUserAchievementByKey(userContent.getUser_ID(), "wordcount");
            userAchievementService.handleTopicCountKey(userContent.getUser_ID(), "topic_count", obj.getContent_ID());
        } else {
            obj = uc;
        }

        return obj;
    }

    public UserContentFavourite getUserContentFavouriteByContentId(Long user_id, Long content_id) {
        UserContent u = userContentRepository.getUserContentByContentId(user_id, content_id);
        UserContentFavourite f = new UserContentFavourite();

        if (u != null) {
            f.setContent_ID(u.getContent_ID());
            f.setFavourite(u.getFavourite());
            f.setUser_ID(u.getUser_ID());
            f.setUser_content_ID(u.getId());

        } else {
            f.setContent_ID(content_id);
            f.setFavourite(0);
            f.setUser_ID(user_id);
        }

        return f;
    }

    public UserContent getUserContentByContentId(Long user_id, Long content_id) {
        return userContentRepository.getUserContentByContentId(user_id, content_id);
    }

    public UserContent updateUserContent(UserContent userContentRequest, long id) {
        UserContent userContent = getUserContent(id);
        copyPropertiesOfEntity.copyNonNullProperties(userContentRequest,
                userContent);
        return userContentRepository.save(userContent);
    }

    public UserContent updateUserContentFavourite(long id, int favourite) {
        UserContent userContent = getUserContent(id);
        userContent.setFavourite(favourite);
        UserContent result = userContentRepository.save(userContent);
        return result;
    }

    public ResponseEntity<?> deleteUserContent(long id) {
        return userContentRepository.findById(id)
                .map(userContent -> {
                    userContentRepository.delete(userContent);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("userContent not found with id " + id));
    }

}
