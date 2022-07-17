package com.example.cinemamanagment.model.dto;


import com.example.cinemamanagment.model.domain.SeatStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Timestamp;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SeatStatusDTO {

    private Long id;
    @NotNull(message = "Seat status name not be empty")
    private String name;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    public SeatStatus map2Entity() {
        return new SeatStatus(this.name);
    }

}
