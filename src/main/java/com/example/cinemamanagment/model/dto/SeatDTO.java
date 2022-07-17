package com.example.cinemamanagment.model.dto;


import com.example.cinemamanagment.model.domain.Row;
import com.example.cinemamanagment.model.domain.Seat;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Timestamp;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SeatDTO {

    private Long id;
    @NotNull(message = "Seat name not be empty")
    private String name;

    @NotNull(message = "Row id not be empty")
    private Long rowId;

    private String row;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    public Seat map2Entity(Row row) {
        return new Seat(
                this.name,
                row
        );
    }

}
