package com.example.cinemamanagment.model.domain;

import com.example.cinemamanagment.model.dto.SessionDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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

    public SessionDTO map2DTO() {
        SessionDTO sessionDTO = new SessionDTO();
        sessionDTO.setId(this.getId());
        sessionDTO.setTime(this.time);
        sessionDTO.setCreatedAt(this.getCreateAt());
        sessionDTO.setUpdatedAt(this.getUpdateAt());
        return sessionDTO;
    }
}
