package app.restservice.apprestservice.Services;

import org.springframework.stereotype.Service;

import app.restservice.apprestservice.CopyPropertiesOfEntity;
import app.restservice.apprestservice.Entities.UserLog;
import app.restservice.apprestservice.Entities.UserLogSorted;
import app.restservice.apprestservice.Exceptions.ResourceNotFoundException;
import app.restservice.apprestservice.Repositories.UserLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
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

    public void setPartialUserLog(String info, int type, Long user_id) {
        // User Log Entry for Frontend Toast
        UserLog log = new UserLog();
        log.setDate_created(LocalDateTime.now().toString());
        log.setInfo(info);
        log.setStatus(1);
        log.setUser_ID(user_id);
        log.setType(type);
        setUserLog(log);
    }

    public UserLogSorted getSortedUserLogs(Long user_id) {
        UserLogSorted logs = new UserLogSorted();
        logs.setLevelLogs(userLogRepository.getUserLogsByUserIdAndType(user_id, 1));
        logs.setQuestLogs(userLogRepository.getUserLogsByUserIdAndType(user_id, 2));
        logs.setAchievementLogs(userLogRepository.getUserLogsByUserIdAndType(user_id, 3));
        logs.setStreakLogs(userLogRepository.getUserLogsByUserIdAndType(user_id, 4));
        return logs;
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
