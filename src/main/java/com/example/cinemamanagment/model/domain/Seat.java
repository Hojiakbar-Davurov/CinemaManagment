package com.example.cinemamanagment.model.domain;

import com.example.cinemamanagment.model.dto.SeatDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "seat")
public class Seat extends AbstractEntity {

    /**
     * seat name
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * related row id
     */
    @JoinColumn(name = "row_id", nullable = false)
    @ManyToOne
    private Row row;

    public SeatDTO map2DTO() {
        SeatDTO seatDTO = new SeatDTO();
        seatDTO.setId(this.getId());
        seatDTO.setName(this.name);
        seatDTO.setRowId(this.row.getId());
        seatDTO.setRow(this.row.getName());
        seatDTO.setCreatedAt(this.getCreateAt());
        seatDTO.setUpdatedAt(this.getUpdateAt());
        return seatDTO;
    }
}
