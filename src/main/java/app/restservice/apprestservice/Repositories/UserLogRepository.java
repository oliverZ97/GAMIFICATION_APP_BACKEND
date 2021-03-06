package app.restservice.apprestservice.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import app.restservice.apprestservice.Entities.UserLog;

@Repository
public interface UserLogRepository extends JpaRepository<UserLog, Long> {
    @Query(value = "SELECT * FROM user_log u WHERE u.user_id = ?1", nativeQuery = true)
    List<UserLog> getUserLogsByUserId(long user_id);

    @Query(value = "SELECT * FROM user_log u WHERE u.user_id = ?1 AND u.type = ?2", nativeQuery = true)
    List<UserLog> getUserLogsByUserIdAndType(long user_id, int type);
}
