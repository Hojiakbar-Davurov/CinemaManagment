package com.example.cinemamanagment.repository;


import com.example.cinemamanagment.model.domain.ExecutionFilm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExecutionFilmRepository extends JpaRepository<ExecutionFilm, Long> {
    boolean existsByHallIdAndFilmIdAndSessionId(Long hall_id, Long film_id, Long session_id);
}
