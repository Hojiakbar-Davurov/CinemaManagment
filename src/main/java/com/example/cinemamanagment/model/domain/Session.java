package com.example.cinemamanagment.model.domain;

import com.example.cinemamanagment.model.dto.SessionDTO;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;

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
    @Temporal(TemporalType.TIME)
    @Column(name = "session_time", nullable = false)
    private Time sessionTime;

    public SessionDTO map2DTO() {
        SessionDTO sessionDTO = new SessionDTO();
        sessionDTO.setId(this.getId());
        sessionDTO.setSessionTime(this.sessionTime);
        sessionDTO.setCreatedAt(this.getCreateAt());
        sessionDTO.setUpdatedAt(this.getUpdateAt());
        return sessionDTO;
    }
}
