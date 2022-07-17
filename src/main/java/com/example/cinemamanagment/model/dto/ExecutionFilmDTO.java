package com.example.cinemamanagment.model.dto;


import com.example.cinemamanagment.model.domain.ExecutionFilm;
import com.example.cinemamanagment.model.domain.Film;
import com.example.cinemamanagment.model.domain.Hall;
import com.example.cinemamanagment.model.domain.Session;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Timestamp;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ExecutionFilmDTO {

    private Long id;

    @NotNull(message = "Hall id not be empty")
    private Long hallId;

    private String hall;

    @NotNull(message = "Film id not be empty")
    private Long filmId;

    private String film;

    @NotNull(message = "Session id not be empty")
    private Long sessionId;

    private String session;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    public ExecutionFilm map2Entity(Hall hall, Film film, Session session) {
        return new ExecutionFilm(
                hall,
                film,
                session
        );
    }

}
