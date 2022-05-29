package app.restservice.apprestservice.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import app.restservice.apprestservice.Entities.UserTopic;

@Repository
public interface UserTopicRepository extends JpaRepository<UserTopic, Long> {
    @Query(value = "SELECT * FROM user_topic u WHERE u.user_id = ?1", nativeQuery = true)
    List<UserTopic> getUserTopicsByUserID(long user_id);
}
