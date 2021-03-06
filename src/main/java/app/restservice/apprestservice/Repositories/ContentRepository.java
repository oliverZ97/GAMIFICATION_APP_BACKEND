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

    @Query(value = "SELECT * FROM content c WHERE c.id IN (SELECT content_id FROM topic_content t WHERE ?1 = t.topic_id) AND NOT EXISTS (Select content_id FROM user_content u WHERE c.id = u.content_id AND u.get_reward = true) ORDER BY RANDOM() LIMIT 2;", nativeQuery = true)
    List<Content> getRandomContentByTopicId(Long topic_id);
}
