package main.repository;

import main.model.DatahouseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.lang.annotation.Native;
import java.util.List;

@Repository
public interface DatahouseRepository extends JpaRepository<DatahouseModel, Integer> {

    @Query(value = "SELECT * FROM data_house WHERE section =?1", nativeQuery = true)
    List<DatahouseModel> findBySection(String sec);
}
