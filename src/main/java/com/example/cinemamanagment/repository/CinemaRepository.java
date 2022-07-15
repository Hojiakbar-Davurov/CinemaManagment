package com.example.cinemamanagment.repository;


import com.example.cinemamanagment.model.domain.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Long> {
    boolean existsByName(String name);
}
