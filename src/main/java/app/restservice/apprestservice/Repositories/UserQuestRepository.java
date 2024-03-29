package app.restservice.apprestservice.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import app.restservice.apprestservice.Entities.UserQuest;

@Repository
public interface UserQuestRepository extends JpaRepository<UserQuest, Long> {
    @Query(value = "SELECT * FROM user_quest u WHERE u.user_id = ?1 AND u.status < 3", nativeQuery = true)
    List<UserQuest> getActiveUserQuestsByUserId(long user_id);

    @Query(value = "SELECT * FROM user_quest u WHERE u.user_id = ?1 AND u.type = ?2 AND u.status < 3", nativeQuery = true)
    List<UserQuest> getActiveUserQuestsByUserIdAndType(long user_id, int type);

    @Query(value = "SELECT * FROM user_quest u WHERE u.user_id = ?1 AND u.status = 5", nativeQuery = true)
    List<UserQuest> getFinishedUserQuestsByUserId(long user_id);
}
