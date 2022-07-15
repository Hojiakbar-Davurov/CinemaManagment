package com.example.cinemamanagment.model.domain;

import com.example.cinemamanagment.model.dto.FilmDTO;
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
@Table(name = "film")
public class Film extends AbstractEntity {

    /**
     * film name
     */
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    public FilmDTO map2DTO() {
        FilmDTO filmDTO = new FilmDTO();
        filmDTO.setId(this.getId());
        filmDTO.setName(this.name);
        filmDTO.setCreatedAt(this.getCreateAt());
        filmDTO.setUpdatedAt(this.getUpdateAt());
        return filmDTO;
    }
}
