package app.restservice.apprestservice.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.restservice.apprestservice.Entities.UserAchievement;



@Repository
public interface UserAchievementRepository extends JpaRepository<UserAchievement, Long> {

}
