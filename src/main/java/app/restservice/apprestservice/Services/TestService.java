package app.restservice.apprestservice.Services;

import org.springframework.stereotype.Service;

import app.restservice.apprestservice.CopyPropertiesOfEntity;
import app.restservice.apprestservice.Entities.Test;
import app.restservice.apprestservice.Exceptions.ResourceNotFoundException;
import app.restservice.apprestservice.Repositories.TestRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Service
public class TestService {
   
    @Autowired
    private TestRepository testRepository;

    private CopyPropertiesOfEntity copyPropertiesOfEntity;

    public Test getTest(Long id) {
        if (testRepository.findById(id).isPresent()) {
            return testRepository.findById(id).get();
        } else {
            throw new ResourceNotFoundException("no test found at id" + id);
        }
    }

    public List<Test> getAllTests() {
        return testRepository.findAll();
    }

    public Test setTest(Test test) {
        return testRepository.save(test);
    }

    public Test updateTest(Test testRequest, long id) {
        Test test = getTest(id);
        copyPropertiesOfEntity.copyNonNullProperties(testRequest, test);
        return testRepository.save(test);
    }


    public ResponseEntity<?> deleteTest(long id) {
        return testRepository.findById(id)
                .map(taste -> {
                    testRepository.delete(taste);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("test not found with id " + id));
    }

}
