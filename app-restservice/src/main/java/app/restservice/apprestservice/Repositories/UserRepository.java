package app.restservice.apprestservice.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import app.restservice.apprestservice.Entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    @Query(value = "SELECT * FROM user u WHERE u.email = ?1", nativeQuery = true)
    Optional<User> getUserByEmail(String email);
}
