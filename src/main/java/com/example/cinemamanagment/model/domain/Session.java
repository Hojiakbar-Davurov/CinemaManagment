package com.example.cinemamanagment.model.domain;

import com.example.cinemamanagment.model.dto.SessionDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "session")
public class Session extends AbstractEntity {

    /**
     * session time
     */
    @Column(name = "time", unique = true, nullable = false)
    private LocalTime time;

    @OneToOne(mappedBy = "session", cascade = CascadeType.REMOVE)
    ExecutionFilm executionFilm;
    public SessionDTO map2DTO() {
        SessionDTO sessionDTO = new SessionDTO();
        sessionDTO.setId(this.getId());
        sessionDTO.setTime(this.time);
        sessionDTO.setCreatedAt(this.getCreateAt());
        sessionDTO.setUpdatedAt(this.getUpdateAt());
        return sessionDTO;
    }

    public Session(LocalTime time) {
        this.time = time;
    }
}
