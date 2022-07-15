package com.example.cinemamanagment.model.domain;

import com.example.cinemamanagment.model.dto.HallDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "hall")
public class Hall extends AbstractEntity {

    /**
     * hall name
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * number of how many rows are this hall
     */
    @Column(name = "number_of_rows", nullable = false)
    private Integer numberOfRows;

    /**
     * related cinema id
     */
    @Column(name = "cinema_id", nullable = false)
    @ManyToOne
    private Cinema cinema;

    public HallDTO map2DTO() {
        HallDTO hallDTO = new HallDTO();
        hallDTO.setId(this.getId());
        hallDTO.setName(this.name);
        hallDTO.setNumberOfRows(this.numberOfRows);
        hallDTO.setCinemaId(this.cinema.getId());
        hallDTO.setCreatedAt(this.getCreateAt());
        hallDTO.setUpdatedAt(this.getUpdateAt());
        return hallDTO;
    }
}
