package app.restservice.apprestservice.Services;

import org.springframework.stereotype.Service;

import app.restservice.apprestservice.CopyPropertiesOfEntity;
import app.restservice.apprestservice.Entities.Category;
import app.restservice.apprestservice.Exceptions.ResourceNotFoundException;
import app.restservice.apprestservice.Repositories.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    private CopyPropertiesOfEntity copyPropertiesOfEntity;

    public Category getCategory(Long id) {
        if (categoryRepository.findById(id).isPresent()) {
            return categoryRepository.findById(id).get();
        } else {
            throw new ResourceNotFoundException("no category found at id" + id);
        }
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category setCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category updateCategory(Category categoryRequest, long id) {
        Category category = getCategory(id);
        copyPropertiesOfEntity.copyNonNullProperties(categoryRequest, category);
        return categoryRepository.save(category);
    }

    public ResponseEntity<?> deleteCategory(long id) {
        return categoryRepository.findById(id)
                .map(category -> {
                    categoryRepository.delete(category);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("category not found with id " + id));
    }

}
