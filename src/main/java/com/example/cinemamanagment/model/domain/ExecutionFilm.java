package com.example.cinemamanagment.model.domain;

import com.example.cinemamanagment.model.dto.ExecutionFilmDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;

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
}
