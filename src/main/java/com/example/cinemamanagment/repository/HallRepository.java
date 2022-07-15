package com.example.cinemamanagment.repository;


import com.example.cinemamanagment.model.domain.Hall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HallRepository extends JpaRepository<Hall, Long> {
    boolean existsByNameAndCinemaId(String name, Long cinema_id);
}
