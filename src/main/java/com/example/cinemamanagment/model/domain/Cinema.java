package com.example.cinemamanagment.model.domain;

import com.example.cinemamanagment.model.dto.CinemaDTO;
import jakarta.persistence.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "cinema")
public class Cinema extends AbstractEntity {

    /**
     * cinema name
     */
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "cinema", cascade = CascadeType.REMOVE)
    Set<Hall> halls=new HashSet<>();
}
