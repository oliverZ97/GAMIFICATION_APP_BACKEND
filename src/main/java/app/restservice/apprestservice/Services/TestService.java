package app.restservice.apprestservice.Services;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import app.restservice.apprestservice.CopyPropertiesofEntity;
import app.restservice.apprestservice.Entities.TestEntity;
import app.restservice.apprestservice.Exceptions.ResourceNotFoundException;
import app.restservice.apprestservice.Repositories.TestRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class TestService {

    private CopyPropertiesofEntity copyPropertiesofEntity;

    @Autowired
    private TestRepository testRepository;

    public TestEntity getTest(Long id) {
        Optional<TestEntity> testOptional = testRepository.findById(id);
        if (testOptional.isPresent()) {
            return testOptional.get();
        } else {
            throw new app.restservice.apprestservice.Exceptions.ResourceNotFoundException(
                    "Unit not found at id " + id.toString());
        }
    }

    public TestEntity setTest(TestEntity test) {
        return testRepository.save(test);
    }

    public Page<TestEntity> getAllTests(Pageable pageable) {

        pageable = PageRequest.of(0, Integer.MAX_VALUE);
        return testRepository.findAll(pageable);
    }

    public TestEntity updateTest(Long id, TestEntity testRequest) {
    ;
    TestEntity existingTest = getTest(id);
    copyPropertiesofEntity.copyNonNullProperties(testRequest, existingTest);
    return testRepository.save(existingTest);
    }

    public ResponseEntity<?> deleteTest(Long id) {
    return testRepository.findById(id).map(test -> {
    testRepository.delete(test);
    return ResponseEntity.ok().build();
    }).orElseThrow(() -> new ResourceNotFoundException("Unit not found with id "
    + id));
    }
}
