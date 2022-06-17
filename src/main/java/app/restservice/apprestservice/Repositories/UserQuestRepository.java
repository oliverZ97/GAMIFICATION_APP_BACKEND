package app.restservice.apprestservice.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import app.restservice.apprestservice.Entities.UserQuest;

@Repository
public interface UserQuestRepository extends JpaRepository<UserQuest, Long> {
    @Query(value = "SELECT * FROM user_quest u WHERE u.user_id = ?1 AND u.status = 1", nativeQuery = true)
    List<UserQuest> getActiveUserQuestsByUserId(long user_id);
}
