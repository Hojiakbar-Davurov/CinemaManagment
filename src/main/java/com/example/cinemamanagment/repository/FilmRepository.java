package com.example.cinemamanagment.repository;


import com.example.cinemamanagment.model.domain.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {
    boolean existsByName(String name);
}
