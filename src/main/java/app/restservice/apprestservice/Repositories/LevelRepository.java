package app.restservice.apprestservice.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.restservice.apprestservice.Entities.Level;

@Repository
public interface LevelRepository extends JpaRepository<Level, Long> {

}
