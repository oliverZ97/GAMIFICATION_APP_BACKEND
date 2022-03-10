package app.restservice.apprestservice.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.restservice.apprestservice.Entities.UserTopic;



@Repository
public interface UserTopicRepository extends JpaRepository<UserTopic, Long> {

}
