package app.restservice.apprestservice.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.restservice.apprestservice.Entities.Friend;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {

}
