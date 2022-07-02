package app.restservice.apprestservice.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import app.restservice.apprestservice.Entities.Quest;

@Repository
public interface QuestRepository extends JpaRepository<Quest, Long> {
    @Query(value = "SELECT * FROM quest e WHERE e.type = ?1", nativeQuery = true)
    List<Quest> getAllByType(int type);

    @Query(value = "SELECT * FROM quest e WHERE e.type = ?1 AND e.min_level <= ?2", nativeQuery = true)
    List<Quest> getAllByTypeAndMaxLevel(int type, int level);
}
