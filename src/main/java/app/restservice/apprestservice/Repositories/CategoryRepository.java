package app.restservice.apprestservice.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.restservice.apprestservice.Entities.Category;



@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}