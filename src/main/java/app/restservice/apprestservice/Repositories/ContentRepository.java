package app.restservice.apprestservice.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import app.restservice.apprestservice.Entities.Content;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {
    @Query(value = "SELECT * FROM content e WHERE e.topic_ids LIKE %?1%", nativeQuery = true)
    List<Content> getContentByTopicId(Long topic_ID);

    @Query(value = "SELECT * FROM content c WHERE POSITION(?1 in c.topic_ids) > 0 AND NOT EXISTS (Select content_id FROM user_content u WHERE c.id = u.content_id) ORDER BY RANDOM() LIMIT 2 ;", nativeQuery = true)
    List<Content> getRandomContentByTopicId(String topic_id);
}
