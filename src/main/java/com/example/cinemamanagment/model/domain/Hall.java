package com.example.cinemamanagment.model.domain;

import com.example.cinemamanagment.model.dto.HallDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

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
    @JoinColumn(name = "cinema_id", nullable = false)
    @ManyToOne
    private Cinema cinema;

    @OneToMany(mappedBy = "hall", cascade = CascadeType.REMOVE)
    Set<Row> rows=new HashSet<>();

    @OneToOne(mappedBy = "hall", cascade = CascadeType.REMOVE)
    ExecutionFilm executionFilm;

    public HallDTO map2DTO() {
        HallDTO hallDTO = new HallDTO();
        hallDTO.setId(this.getId());
        hallDTO.setName(this.name);
        hallDTO.setNumberOfRows(this.numberOfRows);
        hallDTO.setCinemaId(this.cinema.getId());
        hallDTO.setCinema(this.cinema.getName());
        hallDTO.setCreatedAt(this.getCreateAt());
        hallDTO.setUpdatedAt(this.getUpdateAt());
        return hallDTO;
    }

    public Hall(String name, Integer numberOfRows, Cinema cinema) {
        this.name = name;
        this.numberOfRows = numberOfRows;
        this.cinema = cinema;
    }
}
