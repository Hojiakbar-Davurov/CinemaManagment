package com.example.cinemamanagment.model.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

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
}
