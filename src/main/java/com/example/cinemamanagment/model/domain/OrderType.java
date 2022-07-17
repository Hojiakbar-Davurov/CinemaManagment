package com.example.cinemamanagment.model.domain;

import com.example.cinemamanagment.model.dto.OrderTypeDTO;
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
@Table(name = "order_type")
public class OrderType extends AbstractEntity {

    /**
     * order type name
     */
    @Column(name = "name", nullable = false)
    private String name;

    public OrderTypeDTO map2DTO() {
        OrderTypeDTO dto = new OrderTypeDTO();
        dto.setId(this.getId());
        dto.setName(this.name);
        dto.setCreatedAt(this.getCreateAt());
        dto.setUpdatedAt(this.getUpdateAt());
        return dto;
    }
}
