package app.restservice.apprestservice.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.restservice.apprestservice.Entities.Achievement;



@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Long> {

}
