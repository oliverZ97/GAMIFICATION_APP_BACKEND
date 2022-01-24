package app.restservice.apprestservice.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.restservice.apprestservice.Entities.TestEntity;

@Repository
public interface TestRepository extends JpaRepository<TestEntity, Long> {

}
