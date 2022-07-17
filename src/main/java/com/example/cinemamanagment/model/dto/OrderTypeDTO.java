package com.example.cinemamanagment.model.dto;


import com.example.cinemamanagment.model.domain.OrderType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Timestamp;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderTypeDTO {

    private Long id;
    @NotNull(message = "Order type name not be empty")
    private String name;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    public OrderType map2Entity() {
        return new OrderType(this.name);
    }

}
