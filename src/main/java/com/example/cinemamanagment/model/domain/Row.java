package com.example.cinemamanagment.model.domain;

import com.example.cinemamanagment.model.dto.HallDTO;
import com.example.cinemamanagment.model.dto.RowDTO;
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
@Table(name = "row")
public class Row extends AbstractEntity {

    /**
     * row name
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * number of how many seats are this row
     */
    @Column(name = "number_of_seats", nullable = false)
    private Integer numberOfSeats;

    /**
     * related hall id
     */
    @JoinColumn(name = "hall_id", nullable = false)
    @ManyToOne
    private Hall hall;

    @OneToMany(mappedBy = "row", cascade = CascadeType.REMOVE)
    Set<Seat> seats=new HashSet<>();

    public RowDTO map2DTO() {
        RowDTO rowDTO = new RowDTO();
        rowDTO.setId(this.getId());
        rowDTO.setName(this.name);
        rowDTO.setNumberOfSeats(this.numberOfSeats);
        rowDTO.setHallId(this.hall.getId());
        rowDTO.setHall(this.hall.getName());
        rowDTO.setCreatedAt(this.getCreateAt());
        rowDTO.setUpdatedAt(this.getUpdateAt());
        return rowDTO;
    }

    public Row(String name, Integer numberOfSeats, Hall hall) {
        this.name = name;
        this.numberOfSeats = numberOfSeats;
        this.hall = hall;
    }
}
