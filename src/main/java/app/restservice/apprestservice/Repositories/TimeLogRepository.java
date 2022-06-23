package app.restservice.apprestservice.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import app.restservice.apprestservice.Entities.TimeLog;

@Repository
public interface TimeLogRepository extends JpaRepository<TimeLog, Long> {

    @Query(value = "SELECT * FROM time_log u WHERE u.type = ?1 AND u.status < 3 LIMIT 1", nativeQuery = true)
    TimeLog getTimeLogByType(int type);
}
