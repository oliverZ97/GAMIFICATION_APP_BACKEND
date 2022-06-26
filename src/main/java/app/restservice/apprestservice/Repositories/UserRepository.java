package app.restservice.apprestservice.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import app.restservice.apprestservice.Entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String username);

    @Query(value = "SELECT * FROM users m WHERE m.email = ?1", nativeQuery = true)
    User getUserByUsername(String username);

}
