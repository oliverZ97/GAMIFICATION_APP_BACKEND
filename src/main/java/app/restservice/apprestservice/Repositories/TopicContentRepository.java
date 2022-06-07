package app.restservice.apprestservice.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import app.restservice.apprestservice.Entities.TopicContent;

@Repository
public interface TopicContentRepository extends JpaRepository<TopicContent, Long> {
    @Query(value = "SELECT * FROM topic_content e WHERE e.topic_ID = ?1", nativeQuery = true)
    List<TopicContent> getAllByTopicId(long topic_Id);

    @Query(value = "SELECT * FROM topic_content e WHERE e.content_ID = ?1", nativeQuery = true)
    List<TopicContent> getAllByContentId(long content_Id);
}
