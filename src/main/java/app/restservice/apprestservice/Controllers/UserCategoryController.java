package app.restservice.apprestservice.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import app.restservice.apprestservice.Entities.UserCategory;
import app.restservice.apprestservice.Services.UserCategoryService;

import javax.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserCategoryController {
    @Autowired
    private UserCategoryService userCategoryService;

    @GetMapping("/usercategories/get/{id}")
    public UserCategory getUserCategory(@PathVariable Long id) {
        return userCategoryService.getUserCategory(id);
    }

    @GetMapping("/usercategories/getAll")
    public List<UserCategory> getAllUserCategories() {
        return userCategoryService.getAllUserCategories();
    }

    @GetMapping("/usercategories/getbyuserid/{id}")
    public List<UserCategory> getUserCategoriesByUserID(@PathVariable Long id) {
        return userCategoryService.getUserCategoriesByUserID(id);
    }

    @GetMapping("/usercategories/getfavourites/{id}")
    public List<UserCategory> getUserFavouriteCategoriesByUserID(@PathVariable Long id) {
        return userCategoryService.getUserFavouriteCategoriesByUserID(id);
    }

    @PostMapping("/usercategories/set")
    public ResponseEntity<Object> setUserCategory(@Valid @RequestBody UserCategory userCategory, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors().toString());
        } else {
            UserCategory resultObj = userCategoryService.setUserCategory(userCategory);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("message", "OK");
            map.put("status", HttpStatus.OK);
            map.put("data", resultObj);
            return new ResponseEntity<Object>(map, HttpStatus.OK);
        }
    }

    @PutMapping("/usercategories/update/{id}")
    public ResponseEntity<Object> updateUserCategory(@PathVariable Long id,
            @RequestBody UserCategory userCategoryRequest) {
        UserCategory resultObj = userCategoryService.updateUserCategory(userCategoryRequest, id);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", "OK");
        map.put("status", HttpStatus.OK);
        map.put("data", resultObj);
        return new ResponseEntity<Object>(map, HttpStatus.OK);
    }

    @DeleteMapping("/usercategories/delete/{id}")
    public ResponseEntity<?> deleteUserCategory(@PathVariable Long id) {
        return userCategoryService.deleteUserCategory(id);
    }
}
