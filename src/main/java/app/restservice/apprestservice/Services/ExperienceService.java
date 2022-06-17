package app.restservice.apprestservice.Services;

import org.springframework.stereotype.Service;

import app.restservice.apprestservice.CopyPropertiesOfEntity;
import app.restservice.apprestservice.Entities.Experience;
import app.restservice.apprestservice.Entities.UserLog;
import app.restservice.apprestservice.Exceptions.ResourceNotFoundException;
import app.restservice.apprestservice.Repositories.ExperienceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExperienceService {

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private LevelService levelService;

    @Autowired
    private UserLogService userLogService;

    private CopyPropertiesOfEntity copyPropertiesOfEntity;

    public Experience getExperience(Long id) {
        if (experienceRepository.findById(id).isPresent()) {
            return experienceRepository.findById(id).get();
        } else {
            throw new ResourceNotFoundException("no experience found at id" + id);
        }
    }

    public Experience getExperienceByUserID(Long id) {
        if (experienceRepository.findById(id).isPresent()) {
            return experienceRepository.getExperienceByUserID(id).get();
        } else {
            throw new ResourceNotFoundException("no experience found at id" + id);
        }
    }

    public List<Experience> getAllExperiences() {
        return experienceRepository.findAll();
    }

    public Experience setExperience(Experience experience) {
        return experienceRepository.save(experience);
    }

    public Experience updateExperience(Experience experienceRequest, long id) {
        int level = levelService.checkLevelStatus(experienceRequest.getExperience_value());
        Experience experience = getExperience(id);
        copyPropertiesOfEntity.copyNonNullProperties(experienceRequest, experience);
        if (level != experienceRequest.getLevel()) {
            UserLog entry = new UserLog();
            entry.setDate_created(LocalDateTime.now().toString());
            entry.setInfo("level changed");
            entry.setType(1);
            entry.setStatus(1);
            entry.setUser_ID(experience.getUser_ID());
            userLogService.setUserLog(entry);
        }
        experience.setLevel(level);

        return experienceRepository.save(experience);
    }

    public ResponseEntity<?> deleteExperience(long id) {
        return experienceRepository.findById(id)
                .map(experience -> {
                    experienceRepository.delete(experience);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("experience not found with id " + id));
    }

}
