package app.restservice.apprestservice.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.restservice.apprestservice.Entities.UserContent;

@Repository
public interface UserContentRepository extends JpaRepository<UserContent, Long> {

}
