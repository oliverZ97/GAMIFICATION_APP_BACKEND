package app.restservice.apprestservice.Services;

import org.springframework.stereotype.Service;

import app.restservice.apprestservice.CopyPropertiesOfEntity;
import app.restservice.apprestservice.Entities.Level;
import app.restservice.apprestservice.Exceptions.ResourceNotFoundException;
import app.restservice.apprestservice.Repositories.LevelRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Service
public class LevelService {

    @Autowired
    private LevelRepository levelRepository;

    private CopyPropertiesOfEntity copyPropertiesOfEntity;

    public Level getLevel(Long id) {
        if (levelRepository.findById(id).isPresent()) {
            return levelRepository.findById(id).get();
        } else {
            throw new ResourceNotFoundException("no level found at id" + id);
        }
    }

    public List<Level> getAllLevels() {
        return levelRepository.findAll();
    }

    public int checkLevelStatus(int experience) {
        List<Level> levels = getAllLevels();
        Level result = levels.get(0);
        for (int i = 0; i < levels.size(); i++) {
            if (levels.get(i).getExperience() < experience) {
                result = levels.get(i);
            }
        }
        return result.getLevel();
    }

    public Level setLevel(Level level) {
        return levelRepository.save(level);
    }

    public Level updateLevel(Level levelRequest, long id) {
        Level level = getLevel(id);
        copyPropertiesOfEntity.copyNonNullProperties(levelRequest, level);
        return levelRepository.save(level);
    }

    public ResponseEntity<?> deleteLevel(long id) {
        return levelRepository.findById(id)
                .map(level -> {
                    levelRepository.delete(level);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("level not found with id " + id));
    }

}
