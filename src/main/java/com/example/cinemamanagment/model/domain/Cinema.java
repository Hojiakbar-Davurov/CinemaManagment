package com.example.cinemamanagment.model.domain;

import com.example.cinemamanagment.model.dto.CinemaDTO;
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
@Table(name = "cinema")
public class Cinema extends AbstractEntity {

    /**
     * cinema name
     */
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "cinema", cascade = CascadeType.REMOVE)
    Set<Hall> halls=new HashSet<>();

    public Cinema(String name) {
        this.name = name;
    }

    public CinemaDTO map2DTO() {
        CinemaDTO cinemaDTO = new CinemaDTO();
        cinemaDTO.setId(this.getId());
        cinemaDTO.setName(this.name);
        cinemaDTO.setCreatedAt(this.getCreateAt());
        cinemaDTO.setUpdatedAt(this.getUpdateAt());
        return cinemaDTO;
    }
}
