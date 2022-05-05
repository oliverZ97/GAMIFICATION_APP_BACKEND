package app.restservice.apprestservice.Repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import app.restservice.apprestservice.Entities.Experience;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long> {
    @Query(value = "SELECT * FROM experience e WHERE e.user_id = ?1", nativeQuery = true)
    Optional<Experience> getExperienceByUserID(long user_id);

}
