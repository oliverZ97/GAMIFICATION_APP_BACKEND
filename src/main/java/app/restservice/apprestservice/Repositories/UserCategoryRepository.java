package app.restservice.apprestservice.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import app.restservice.apprestservice.Entities.UserCategory;

@Repository
public interface UserCategoryRepository extends JpaRepository<UserCategory, Long> {
    @Query(value = "SELECT * FROM user_category u WHERE u.user_id = ?1", nativeQuery = true)
    List<UserCategory> getUserCategoriesByUserID(long user_id);

    @Query(value = "SELECT * FROM user_category u WHERE u.user_id = ?1 AND u.favourite = 1", nativeQuery = true)
    List<UserCategory> getUserFavouriteCategoriesByUserID(long user_id);
}