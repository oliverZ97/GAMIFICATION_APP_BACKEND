package app.restservice.apprestservice.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.restservice.apprestservice.Entities.Quest;



@Repository
public interface QuestRepository extends JpaRepository<Quest, Long> {

}
