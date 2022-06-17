package app.restservice.apprestservice.Services;

import org.springframework.stereotype.Service;

import app.restservice.apprestservice.CopyPropertiesOfEntity;
import app.restservice.apprestservice.Entities.UserLog;
import app.restservice.apprestservice.Entities.UserQuest;
import app.restservice.apprestservice.Exceptions.ResourceNotFoundException;
import app.restservice.apprestservice.Repositories.UserLogRepository;
import app.restservice.apprestservice.Repositories.UserQuestRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Service
public class UserLogService {

    @Autowired
    private UserLogRepository userLogRepository;

    private CopyPropertiesOfEntity copyPropertiesOfEntity;

    public UserLog getUserLog(Long id) {
        if (userLogRepository.findById(id).isPresent()) {
            return userLogRepository.findById(id).get();
        } else {
            throw new ResourceNotFoundException("no userLog found at id" + id);
        }
    }

    public List<UserLog> getAllUserLogs() {
        return userLogRepository.findAll();
    }

    public List<UserLog> getByUserId(Long id) {
        return userLogRepository.getUserLogsByUserId(id);
    }

    public List<UserLog> getByUserIdAndType(Long id, int type) {
        return userLogRepository.getUserLogsByUserIdAndType(id, type);
    }

    public UserLog setUserLog(UserLog userLog) {
        return userLogRepository.save(userLog);
    }

    public UserLog updateUserLog(UserLog userLogRequest, long id) {
        UserLog userLog = getUserLog(id);
        copyPropertiesOfEntity.copyNonNullProperties(userLogRequest, userLog);
        return userLogRepository.save(userLog);
    }

    public ResponseEntity<?> deleteUserLog(long id) {
        return userLogRepository.findById(id)
                .map(userLog -> {
                    userLogRepository.delete(userLog);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("userLog not found with id " + id));
    }

}
