package app.restservice.apprestservice.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import app.restservice.apprestservice.Entities.UserContent;

@Repository
public interface UserContentRepository extends JpaRepository<UserContent, Long> {
    @Query(value = "SELECT * FROM user_content u WHERE u.content_id = ?1 AND u.user_id = ?2", nativeQuery = true)
    UserContent checkRewardStatus(long content_id, long user_id);

    @Query(value = "SELECT * FROM user_content u WHERE u.user_id = ?1 AND u.get_reward = true", nativeQuery = true)
    List<UserContent> getUserReadContents(long user_id);
}
