package com.example.cinemamanagment.model.dto;


import com.example.cinemamanagment.model.domain.Hall;
import com.example.cinemamanagment.model.domain.Row;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Timestamp;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RowDTO {

    private Long id;
    @NotNull(message = "Row name not be empty")
    private String name;
    @NotNull(message = "Number of seats in row not be empty")
    private Integer numberOfSeats;

    @NotNull(message = "Hall id not be empty")
    private Long hallId;

    private String hall;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    public Row map2Entity(Hall hall) {
        return new Row(
                this.name,
                this.numberOfSeats,
                hall
        );
    }



}
