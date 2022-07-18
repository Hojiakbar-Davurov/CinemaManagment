package com.example.cinemamanagment.repository;


import com.example.cinemamanagment.model.domain.Row;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RowRepository extends JpaRepository<Row, Long> {
    boolean existsByNameAndHallId(String name, Long hall_id);

    List<Row> findAllByHallId(Long hall_id, Pageable pageable);

}
