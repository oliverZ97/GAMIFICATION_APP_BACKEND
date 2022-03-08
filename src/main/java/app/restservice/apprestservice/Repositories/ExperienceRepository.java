package app.restservice.apprestservice.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.restservice.apprestservice.Entities.Experience;



@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long> {

}
