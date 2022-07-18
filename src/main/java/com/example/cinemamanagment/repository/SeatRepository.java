package com.example.cinemamanagment.repository;


import com.example.cinemamanagment.model.domain.Seat;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    boolean existsByNameAndRowId(String name, Long row_id);

    List<Seat> findAllByRowId(Long row_id, Pageable pageable);
}
