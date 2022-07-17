package com.example.cinemamanagment.repository;


import com.example.cinemamanagment.model.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    boolean existsByExecutionFilmIdAndSeatId(Long executionFilm_id, Long seat_id);

    Optional<Ticket> findByExecutionFilmIdAndSeatId(Long executionFilm_id, Long seat_id);
}
