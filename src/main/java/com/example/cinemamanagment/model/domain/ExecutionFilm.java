package com.example.cinemamanagment.model.domain;

import com.example.cinemamanagment.model.dto.ExecutionFilmDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "execution_film")
public class ExecutionFilm extends AbstractEntity {

    /**
     * hall id
     */
    @JoinColumn(name = "hall_id", nullable = false)
    @OneToOne
    private Hall hall;

    /**
     * film id
     */
    @JoinColumn(name = "fill_id", nullable = false)
    @OneToOne
    private Film film;

    /**
     * session id
     */
    @JoinColumn(name = "session_id", nullable = false)
    @OneToOne
    private Session session;

    @OneToMany(mappedBy = "executionFilm", cascade = CascadeType.REMOVE)
    Set<Ticket> tickets=new HashSet<>();

    public ExecutionFilmDTO map2DTO() {
        ExecutionFilmDTO executionFilmDTO = new ExecutionFilmDTO();
        executionFilmDTO.setId(this.getId());
        executionFilmDTO.setHallId(this.hall.getId());
        executionFilmDTO.setHall(this.hall.getName());
        executionFilmDTO.setFilmId(this.film.getId());
        executionFilmDTO.setFilm(this.film.getName());
        executionFilmDTO.setSessionId(this.session.getId());
        executionFilmDTO.setSession(this.session.getTime().toString());

        return executionFilmDTO;
    }

    public ExecutionFilm(Hall hall, Film film, Session session) {
        this.hall = hall;
        this.film = film;
        this.session = session;
    }
}
