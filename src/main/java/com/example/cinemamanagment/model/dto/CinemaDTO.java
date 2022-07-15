package com.example.cinemamanagment.model.dto;


import com.example.cinemamanagment.model.domain.Cinema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Timestamp;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CinemaDTO {

    private Long id;
    @NotNull(message = "Cinema name not be empty")
    private String name;

    private Timestamp createdAt;

    private Timestamp updatedAt;
    public Cinema map2Entity() {
        return new Cinema(this.name);
    }

}
