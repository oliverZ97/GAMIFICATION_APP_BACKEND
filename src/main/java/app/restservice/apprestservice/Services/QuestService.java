package app.restservice.apprestservice.Services;

import org.springframework.stereotype.Service;

import app.restservice.apprestservice.CopyPropertiesOfEntity;
import app.restservice.apprestservice.Entities.Quest;
import app.restservice.apprestservice.Exceptions.ResourceNotFoundException;
import app.restservice.apprestservice.Repositories.QuestRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Service
public class QuestService {
   
    @Autowired
    private QuestRepository questRepository;

    private CopyPropertiesOfEntity copyPropertiesOfEntity;

    public Quest getQuest(Long id) {
        if (questRepository.findById(id).isPresent()) {
            return questRepository.findById(id).get();
        } else {
            throw new ResourceNotFoundException("no quest found at id" + id);
        }
    }

    public List<Quest> getAllQuests() {
        return questRepository.findAll();
    }

    public Quest setQuest(Quest quest) {
        return questRepository.save(quest);
    }

    public Quest updateQuest(Quest questRequest, long id) {
        Quest quest = getQuest(id);
        copyPropertiesOfEntity.copyNonNullProperties(questRequest, quest);
        return questRepository.save(quest);
    }


    public ResponseEntity<?> deleteQuest(long id) {
        return questRepository.findById(id)
                .map(quest -> {
                    questRepository.delete(quest);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("quest not found with id " + id));
    }

}
