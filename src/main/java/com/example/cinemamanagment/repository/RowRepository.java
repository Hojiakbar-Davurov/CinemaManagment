package com.example.cinemamanagment.repository;


import com.example.cinemamanagment.model.domain.Row;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RowRepository extends JpaRepository<Row, Long> {
    boolean existsByNameAndHallId(String name, Long hall_id);
}
