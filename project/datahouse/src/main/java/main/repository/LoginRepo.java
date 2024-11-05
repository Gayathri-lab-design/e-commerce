package main.repository;

import io.swagger.models.auth.In;
import main.model.LoginDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoginRepo extends JpaRepository<LoginDetails , Integer> {

    @Query(value = "SELECT * FROM login_details WHERE name =?1", nativeQuery = true)
    List<LoginDetails> findByName(String name);
}
