package app.restservice.apprestservice.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import app.restservice.apprestservice.Entities.Topic;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    @Query(value = "SELECT * FROM topic e WHERE e.category_id = ?1", nativeQuery = true)
    List<Topic> getTopicsByCategoryId(long category_id);
}
