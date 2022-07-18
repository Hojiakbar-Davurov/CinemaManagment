package com.example.cinemamanagment.model.dto;


import com.example.cinemamanagment.model.domain.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Timestamp;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TicketDTO {

    private Long id;
    @NotNull(message = "client name not be empty")
    private String clientName;
    @NotNull(message = "payment status not be empty")
    private Boolean isPay;

    @NotNull(message = "execution film id not be empty")
    private Long executionFilmId;

    @NotNull(message = "seat id not be empty")
    private Long seatId;

    private String seatName;

    @NotNull(message = "seat status id not be empty")
    private Long seatStatusId;

    private String seatStatusName;

    @NotNull(message = "order type id not be empty")
    private Long orderTypeId;

    private String orderTypeName;

    private String film;

    private String session;

    private String row;
    private Boolean isCancelled=Boolean.FALSE;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    public Ticket map2Entity(ExecutionFilm executionFilm, Seat seat, SeatStatus seatStatus, OrderType orderType) {
        return new Ticket(
                this.clientName,
                this.isPay,
                this.isCancelled,
                seat.getRow().getId(),
                executionFilm.getFilm().getId(),
                executionFilm.getSession().getId(),
                executionFilm,
                seat,
                seatStatus,
                orderType
        );
    }

}
