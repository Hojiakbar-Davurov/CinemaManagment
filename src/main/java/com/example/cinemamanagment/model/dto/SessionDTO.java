package com.example.cinemamanagment.model.dto;


import com.example.cinemamanagment.model.domain.Session;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Time;
import java.sql.Timestamp;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SessionDTO {

    private Long id;
    @NotNull(message = "Session name not be empty")
    private Time sessionTime;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    public Session map2Entity() {
        return new Session(this.sessionTime);
    }
}
