package com.example.cinemamanagment.model.domain;

import com.example.cinemamanagment.model.dto.OrderTypeDTO;
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
@Table(name = "order_type")
public class OrderType extends AbstractEntity {

    /**
     * order type name
     */
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "orderType", cascade = CascadeType.REMOVE)
    Set<Ticket> tickets=new HashSet<>();
    public OrderTypeDTO map2DTO() {
        OrderTypeDTO dto = new OrderTypeDTO();
        dto.setId(this.getId());
        dto.setName(this.name);
        dto.setCreatedAt(this.getCreateAt());
        dto.setUpdatedAt(this.getUpdateAt());
        return dto;
    }

    public OrderType(String name) {
        this.name = name;
    }
}
