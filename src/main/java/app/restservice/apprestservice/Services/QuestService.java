package app.restservice.apprestservice.Services;

import org.springframework.stereotype.Service;

import app.restservice.apprestservice.CopyPropertiesOfEntity;
import app.restservice.apprestservice.Entities.Category;
import app.restservice.apprestservice.Entities.Quest;
import app.restservice.apprestservice.Entities.Topic;
import app.restservice.apprestservice.Exceptions.ResourceNotFoundException;
import app.restservice.apprestservice.Repositories.QuestRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class QuestService {

    @Autowired
    private QuestRepository questRepository;

    @Autowired
    private TopicService topicService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ContentService contentService;

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

    public Quest createRandomTopicQuest(int type) {
        List<Topic> topics = topicService.getAllTopics();
        int rdm = (int) Math.round(Math.random() * topics.size());
        Topic topic = topics.get(rdm);
        int keyCount = 0;
        int experience = 0;
        switch (type) {
            case 1:
                keyCount = (int) Math.round(Math.random() * 2 + 1);
                break;
            case 2:
                keyCount = (int) Math.round(Math.random() * 5 + 5);
                break;
            case 3:
                keyCount = (int) Math.round(Math.random() * 15 + 15);
                break;
            default:
                keyCount = (int) Math.round(Math.random() * 2 + 1);
                break;
        }
        experience = keyCount * 10;
        String description = "Lese " + keyCount + " Inhalte des Themas " + topic.getName();
        String title = "Fokustraining";
        Quest quest = new Quest();
        quest.setDescription(description);
        quest.setExperience(experience);
        quest.setKey(topic.getName());
        quest.setKey_count(keyCount);
        quest.setTitle(title);
        quest.setType(type);
        quest.setValue(0);
        return questRepository.save(quest);
    }

    public Quest createRandomCategoryQuest(int type) {
        List<Category> categories = categoryService.getAllCategories();
        int rdm = (int) Math.round(Math.random() * categories.size());
        Category category = categories.get(rdm);
        int keyCount = 0;
        int experience = 0;
        switch (type) {
            case 1:
                keyCount = (int) Math.round(Math.random() * 2 + 1);
                break;
            case 2:
                keyCount = (int) Math.round(Math.random() * 5 + 5);
                break;
            case 3:
                keyCount = (int) Math.round(Math.random() * 15 + 15);
                break;
            default:
                keyCount = (int) Math.round(Math.random() * 2 + 1);
                break;
        }
        experience = keyCount * 10;
        String description = "Lese " + keyCount + " Inhalte der Katgorie " + category.getName();
        String title = "Fachexpertise";
        Quest quest = new Quest();
        quest.setDescription(description);
        quest.setExperience(experience);
        quest.setKey(category.getName());
        quest.setKey_count(keyCount);
        quest.setTitle(title);
        quest.setType(type);
        quest.setValue(0);
        return questRepository.save(quest);
    }

    public Quest createRandomWordCountQuest(int type) {
        int keyCount = 0;
        int key = 0;
        int experience = 0;
        switch (type) {
            case 1:
                keyCount = 1;
                key = 250;
                break;
            case 2:
                keyCount = (int) Math.round(Math.random() * 3 + 2);
                key = 300;
                break;
            case 3:
                keyCount = (int) Math.round(Math.random() * 10 + 5);
                key = 400;
                break;
            default:
                keyCount = 1;
                key = 250;
                break;
        }
        experience = keyCount * 10;
        String description = "Lese " + keyCount + " Inhalte die mindestens " + key + " Wörter enthalten";
        String title = "Marathontraining";
        Quest quest = new Quest();
        quest.setDescription(description);
        quest.setExperience(experience);
        quest.setKey(String.valueOf(key));
        quest.setKey_count(keyCount);
        quest.setTitle(title);
        quest.setType(type);
        quest.setValue(0);
        return questRepository.save(quest);
    }

    public List<Quest> getRandomQuestSet(int type) {
        List<Quest> quests = questRepository.getAllByType(type);
        return pickNRandom(quests, 4);
    }

    // https://stackoverflow.com/questions/8378752/pick-multiple-random-elements-from-a-list-in-java
    public static List<Quest> pickNRandom(List<Quest> lst, int n) {
        List<Quest> copy = new ArrayList<Quest>(lst);
        Collections.shuffle(copy);
        return n > copy.size() ? copy.subList(0, copy.size()) : copy.subList(0, n);
    }

}
