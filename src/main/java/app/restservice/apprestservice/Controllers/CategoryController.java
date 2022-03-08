package app.restservice.apprestservice.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import app.restservice.apprestservice.Entities.Category;
import app.restservice.apprestservice.Services.CategoryService;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/category/get/{id}")
    public Category getCategory(@PathVariable Long id) {
        return categoryService.getCategory(id);
    }

    @GetMapping("/category/getAll")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @PostMapping("/category/set")
    public ResponseEntity<String> setCategory(@Valid @RequestBody Category category, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors().toString());
        } else {
            categoryService.setCategory(category);
            return ResponseEntity.status(HttpStatus.OK).body("Category wurde erstellt");
        }
    }

    @PutMapping("/category/update/{id}")
    public Category updateCategory(@PathVariable Long id, @RequestBody Category categoryRequest) {
        return categoryService.updateCategory(categoryRequest, id);
    }

    @DeleteMapping("/category/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        return categoryService.deleteCategory(id);
    }
}

