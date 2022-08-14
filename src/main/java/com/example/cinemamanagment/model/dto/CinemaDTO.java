package com.example.cinemamanagment.model.dto;


import com.example.cinemamanagment.model.domain.Cinema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CinemaDTO {

    private Long id;
    @NotNull(message = "Cinema name not be empty")
    private String name;
}
