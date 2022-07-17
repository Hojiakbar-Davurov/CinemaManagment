package com.example.cinemamanagment.repository;


import com.example.cinemamanagment.model.domain.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    boolean existsByTime(LocalTime time);
}
