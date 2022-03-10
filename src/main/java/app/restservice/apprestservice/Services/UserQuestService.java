package app.restservice.apprestservice.Services;

import org.springframework.stereotype.Service;

import app.restservice.apprestservice.CopyPropertiesOfEntity;
import app.restservice.apprestservice.Entities.UserQuest;
import app.restservice.apprestservice.Exceptions.ResourceNotFoundException;
import app.restservice.apprestservice.Repositories.UserQuestRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Service
public class UserQuestService {
   
    @Autowired
    private UserQuestRepository userQuestRepository;

    private CopyPropertiesOfEntity copyPropertiesOfEntity;

    public UserQuest getUserQuest(Long id) {
        if (userQuestRepository.findById(id).isPresent()) {
            return userQuestRepository.findById(id).get();
        } else {
            throw new ResourceNotFoundException("no user quest found at id" + id);
        }
    }

    public List<UserQuest> getAllUserQuests() {
        return userQuestRepository.findAll();
    }

    public UserQuest setUserQuest(UserQuest userQuest) {
        return userQuestRepository.save(userQuest);
    }

    public UserQuest updateUserQuest(UserQuest userQuestRequest, long id) {
        UserQuest userQuest = getUserQuest(id);
        copyPropertiesOfEntity.copyNonNullProperties(userQuestRequest, userQuest);
        return userQuestRepository.save(userQuest);
    }


    public ResponseEntity<?> deleteUserQuest(long id) {
        return userQuestRepository.findById(id)
                .map(userQuest -> {
                    userQuestRepository.delete(userQuest);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("userQuest not found with id " + id));
    }

}
