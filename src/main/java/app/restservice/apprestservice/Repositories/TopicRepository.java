package app.restservice.apprestservice.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.restservice.apprestservice.Entities.Topic;



@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

}
