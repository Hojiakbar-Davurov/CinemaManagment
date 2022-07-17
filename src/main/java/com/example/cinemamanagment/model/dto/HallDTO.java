package com.example.cinemamanagment.model.dto;


import com.example.cinemamanagment.model.domain.Cinema;
import com.example.cinemamanagment.model.domain.Hall;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Timestamp;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HallDTO {

    private Long id;
    @NotNull(message = "Hall name not be empty")
    private String name;
    @NotNull(message = "Number of rows in hall not be empty")
    private Integer numberOfRows;

    @NotNull(message = "Cinema id not be empty")
    private Long cinemaId;

    private String cinema;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    public Hall map2Entity(Cinema cinema) {
        return new Hall(
                this.name,
                this.numberOfRows,
                cinema
        );
    }

}
