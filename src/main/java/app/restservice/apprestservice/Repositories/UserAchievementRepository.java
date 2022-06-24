package app.restservice.apprestservice.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import app.restservice.apprestservice.Entities.UserAchievement;

@Repository
public interface UserAchievementRepository extends JpaRepository<UserAchievement, Long> {
    @Query(value = "SELECT * FROM user_achievement u WHERE u.user_id = ?1", nativeQuery = true)
    List<UserAchievement> getUserAchievementsByUserId(long user_id);

    @Query(value = "SELECT * FROM user_achievement u WHERE u.user_id = ?1 AND u.achievement_id = ?2 AND u.status = 1", nativeQuery = true)
    UserAchievement getUserAchievementsByUserIdAndAchievementId(long user_id, long achievement_id);

    @Query(value = "SELECT COUNT(id) FROM user_achievement u WHERE u.achievement_id = ?1 AND u.status > 1", nativeQuery = true)
    int getUserAchievementCountByAchievementId(long achievement_id);
}
