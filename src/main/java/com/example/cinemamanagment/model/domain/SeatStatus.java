package com.example.cinemamanagment.model.domain;

import com.example.cinemamanagment.model.dto.SeatStatusDTO;
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
@Table(name = "seat_status")
public class SeatStatus extends AbstractEntity {

    /**
     * seat status name
     */
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "seatStatus", cascade = CascadeType.REMOVE)
    Set<Ticket> tickets=new HashSet<>();
    public SeatStatusDTO map2DTO() {
        SeatStatusDTO seatStatusDTO = new SeatStatusDTO();
        seatStatusDTO.setId(this.getId());
        seatStatusDTO.setName(this.name);
        seatStatusDTO.setCreatedAt(this.getCreateAt());
        seatStatusDTO.setUpdatedAt(this.getUpdateAt());
        return seatStatusDTO;
    }

    public SeatStatus(String name) {
        this.name = name;
    }
}
