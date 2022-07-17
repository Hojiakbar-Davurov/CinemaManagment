package com.example.cinemamanagment.repository;


import com.example.cinemamanagment.model.domain.SeatStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatStatusRepository extends JpaRepository<SeatStatus, Long> {
    boolean existsByName(String name);
}
