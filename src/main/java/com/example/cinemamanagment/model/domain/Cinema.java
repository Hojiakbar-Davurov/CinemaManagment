package com.example.cinemamanagment.model.domain;

import com.example.cinemamanagment.model.dto.CinemaDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cinema")
public class Cinema extends AbstractEntity {

    /**
     * cinema name
     */
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    public CinemaDTO map2DTO() {
        CinemaDTO cinemaDTO = new CinemaDTO();
        cinemaDTO.setId(this.getId());
        cinemaDTO.setName(this.name);
        cinemaDTO.setCreatedAt(this.getCreateAt());
        cinemaDTO.setUpdatedAt(this.getUpdateAt());
        return cinemaDTO;
    }
}
