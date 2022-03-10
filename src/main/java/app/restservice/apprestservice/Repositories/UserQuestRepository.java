package app.restservice.apprestservice.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.restservice.apprestservice.Entities.UserQuest;



@Repository
public interface UserQuestRepository extends JpaRepository<UserQuest, Long> {

}
