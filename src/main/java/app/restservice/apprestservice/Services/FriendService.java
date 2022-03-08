package app.restservice.apprestservice.Services;

import org.springframework.stereotype.Service;

import app.restservice.apprestservice.CopyPropertiesOfEntity;
import app.restservice.apprestservice.Entities.Friend;
import app.restservice.apprestservice.Exceptions.ResourceNotFoundException;
import app.restservice.apprestservice.Repositories.FriendRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Service
public class FriendService {
   
    @Autowired
    private FriendRepository friendRepository;

    private CopyPropertiesOfEntity copyPropertiesOfEntity;

    public Friend getFriend(Long id) {
        if (friendRepository.findById(id).isPresent()) {
            return friendRepository.findById(id).get();
        } else {
            throw new ResourceNotFoundException("no friend found at id" + id);
        }
    }

    public List<Friend> getAllFriends() {
        return friendRepository.findAll();
    }

    public Friend setFriend(Friend friend) {
        return friendRepository.save(friend);
    }

    public Friend updateFriend(Friend friendRequest, long id) {
        Friend friend = getFriend(id);
        copyPropertiesOfEntity.copyNonNullProperties(friendRequest, friend);
        return friendRepository.save(friend);
    }


    public ResponseEntity<?> deleteFriend(long id) {
        return friendRepository.findById(id)
                .map(friend -> {
                    friendRepository.delete(friend);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("friend not found with id " + id));
    }

}
