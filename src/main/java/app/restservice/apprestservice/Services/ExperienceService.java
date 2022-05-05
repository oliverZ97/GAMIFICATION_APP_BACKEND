package app.restservice.apprestservice.Services;

import org.springframework.stereotype.Service;

import app.restservice.apprestservice.CopyPropertiesOfEntity;
import app.restservice.apprestservice.Entities.Experience;
import app.restservice.apprestservice.Exceptions.ResourceNotFoundException;
import app.restservice.apprestservice.Repositories.ExperienceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Service
public class ExperienceService {

    @Autowired
    private ExperienceRepository experienceRepository;

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
        Experience experience = getExperience(id);
        copyPropertiesOfEntity.copyNonNullProperties(experienceRequest, experience);
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
