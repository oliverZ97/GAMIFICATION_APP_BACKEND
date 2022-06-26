package app.restservice.apprestservice.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import app.restservice.apprestservice.Entities.Streak;

@Repository
public interface StreakRepository extends JpaRepository<Streak, Long> {

    @Query(value = "SELECT * FROM streak u WHERE u.user_id = ?1", nativeQuery = true)
    List<Streak> getStreakByUserId(Long id);

    @Query(value = "SELECT * FROM streak u WHERE u.user_id = ?1 AND u.status = 1", nativeQuery = true)
    Streak getActiveStreakByUserId(Long id);

}