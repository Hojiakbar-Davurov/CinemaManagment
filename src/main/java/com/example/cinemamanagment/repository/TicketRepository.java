package com.example.cinemamanagment.repository;


import com.example.cinemamanagment.model.domain.Ticket;
import com.example.cinemamanagment.model.dto.FreeSeatInExecutionFilmDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    boolean existsByExecutionFilmIdAndSeatId(Long executionFilm_id, Long seat_id);

    Optional<Ticket> findByExecutionFilmIdAndSeatId(Long executionFilm_id, Long seat_id);

    @Query(value = "select new  com.example.cinemamanagment.model.dto.FreeSeatInExecutionFilmDTO(" +
            "f.name , r.name ,s.name ,ss.name )" +
            "from Ticket t\n" +
            "join Film f on f.id = t.filmId\n" +
            "join Row r on r.id = t.rowId\n" +
            "join Seat s on s.id = t.seat.id\n" +
            "join SeatStatus ss on ss.id = t.seatStatus.id\n" +
            "where t.executionFilm.id = :executionFilmId and t.isCancelled = false and ss.name  <> com.example.cinemamanagment.model.enums.SeatStatusEnum.OZOD",
            countQuery = "select \n" +
                    "count(t) \n" +
                    "from Ticket t \n" +
                    "join SeatStatus ss on ss.id = t.seatStatus.id \n" +
                    "where t.executionFilm.id = :executionFilmId and t.isCancelled= false and ss.name  <> com.example.cinemamanagment.model.enums.SeatStatusEnum.OZOD ")
    Page<FreeSeatInExecutionFilmDTO> findFreeSeatByExecutionFilm(@Param("executionFilmId") Long executionFilmId, Pageable pageable);

    @Query(value = "update Ticket " +
            "set isCancelled = true " +
            "where id = :id ")
    void cancelNotPayment(@Param("id") Long id);

    @Query(value = "select t.id \n" +
            "from Ticket t\n" +
            "where t.isPay = false and t.isCancelled = false and t.executionFilm.session.time <= :time")
    List<Long> findNotPaymentByTime(LocalTime time);
}
