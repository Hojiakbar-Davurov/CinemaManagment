package com.example.cinemamanagment.model.dto;


import com.example.cinemamanagment.model.domain.Film;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Timestamp;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FilmDTO {

    private Long id;
    @NotNull(message = "Film name not be empty")
    private String name;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    public Film map2Entity() {
        return new Film(this.name);
    }

}
