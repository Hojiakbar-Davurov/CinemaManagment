package com.example.cinemamanagment.model.dto;


import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FreeSeatInExecutionFilmDTO {
    private String film;
    private String row;
    private String seat;
    private String seatStatus;

}
