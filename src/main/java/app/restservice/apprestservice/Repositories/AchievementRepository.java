package app.restservice.apprestservice.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import app.restservice.apprestservice.Entities.Achievement;

@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Long> {

    @Query(value = "SELECT * FROM achievement u WHERE u.key = ?1", nativeQuery = true)
    List<Achievement> getAchievementsByKey(String key);
}
