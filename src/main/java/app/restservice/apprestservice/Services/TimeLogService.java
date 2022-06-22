package app.restservice.apprestservice.Services;

import org.springframework.stereotype.Service;

import app.restservice.apprestservice.CopyPropertiesOfEntity;
import app.restservice.apprestservice.Entities.TimeLog;
import app.restservice.apprestservice.Entities.UserQuest;
import app.restservice.apprestservice.Exceptions.ResourceNotFoundException;
import app.restservice.apprestservice.Repositories.TimeLogRepository;
import app.restservice.apprestservice.Repositories.UserLogRepository;
import app.restservice.apprestservice.Repositories.UserQuestRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Service
public class TimeLogService {

    @Autowired
    private TimeLogRepository timeLogRepository;

    private CopyPropertiesOfEntity copyPropertiesOfEntity;

    public TimeLog getTimeLog(Long id) {
        if (timeLogRepository.findById(id).isPresent()) {
            return timeLogRepository.findById(id).get();
        } else {
            throw new ResourceNotFoundException("no timeLog found at id" + id);
        }
    }

    public List<TimeLog> getAllTimeLogs() {
        return timeLogRepository.findAll();
    }

    public List<TimeLog> getTimeLogsByType(int type) {
        return timeLogRepository.getTimeLogsByType(type);
    }

    public TimeLog setTimeLog(TimeLog timeLog) {
        return timeLogRepository.save(timeLog);
    }

    public TimeLog updateTimeLog(TimeLog timeLogRequest, long id) {
        TimeLog timeLog = getTimeLog(id);
        copyPropertiesOfEntity.copyNonNullProperties(timeLogRequest, timeLog);
        return timeLogRepository.save(timeLog);
    }

    public ResponseEntity<?> deleteTimeLog(long id) {
        return timeLogRepository.findById(id)
                .map(timeLog -> {
                    timeLogRepository.delete(timeLog);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("timeLog not found with id " + id));
    }

}
