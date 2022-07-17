package com.example.cinemamanagment.model.dto;


import com.example.cinemamanagment.model.domain.Session;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SessionDTO {

    private Long id;
    @NotNull(message = "Session time not be empty")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime time;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    public Session map2Entity() {
        return new Session(this.time);
    }
}
