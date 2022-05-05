package app.restservice.apprestservice.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import app.restservice.apprestservice.CopyPropertiesOfEntity;
import app.restservice.apprestservice.Entities.UserCategory;
import app.restservice.apprestservice.Exceptions.ResourceNotFoundException;
import app.restservice.apprestservice.Repositories.UserCategoryRepository;

@Service
public class UserCategoryService {
    @Autowired
    private UserCategoryRepository userCategoryRepository;

    private CopyPropertiesOfEntity copyPropertiesOfEntity;

    public UserCategory getUserCategory(Long id) {
        if (userCategoryRepository.findById(id).isPresent()) {
            return userCategoryRepository.findById(id).get();
        } else {
            throw new ResourceNotFoundException("no user category found at id" + id);
        }
    }

    public List<UserCategory> getUserCategoriesByUserID(Long id) {
        return userCategoryRepository.getUserCategoriesByUserID(id);
    }

    public List<UserCategory> getAllUserCategories() {
        return userCategoryRepository.findAll();
    }

    public UserCategory setUserCategory(UserCategory userCategory) {
        return userCategoryRepository.save(userCategory);
    }

    public UserCategory updateUserCategory(UserCategory userCategoryRequest, long id) {
        UserCategory UserCategory = getUserCategory(id);
        copyPropertiesOfEntity.copyNonNullProperties(userCategoryRequest, UserCategory);
        return userCategoryRepository.save(UserCategory);
    }

    public ResponseEntity<?> deleteUserCategory(long id) {
        return userCategoryRepository.findById(id)
                .map(UserCategory -> {
                    userCategoryRepository.delete(UserCategory);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("UserCategory not found with id " + id));
    }
}
