package com.example.cinemamanagment.model.domain;

import com.example.cinemamanagment.model.dto.TicketDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ticket")
public class Ticket extends AbstractEntity {

    /**
     * client name
     */
    @Column(name = "client_name", nullable = false)
    private String clientName;

    /**
     * payment status
     */
    @Column(name = "is_pay", nullable = false)
    private Boolean isPay;

    /**
     * isCancelled. This is automatically cancel if not pay until start
     */
    @Column(name = "is_cancelled", nullable = false)
    private Boolean isCancelled = Boolean.FALSE;

    /**
     * row id which seat related to
     */
    @Column(name = "row_id", nullable = false)
    private Long rowId;

    /**
     * film id
     */
    @Column(name = "film_id", nullable = false)
    private Long filmId;

    /**
     * session id of execution film
     */
    @Column(name = "session_id", nullable = false)
    private Long sessionId;

    /**
     * execution film id
     */
    @ManyToOne
    @JoinColumn(name = "execution_film_id", nullable = false)
    private ExecutionFilm executionFilm;

    /**
     * seat id
     */
    @ManyToOne
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat;

    /**
     * seat status id
     */
    @ManyToOne
    @JoinColumn(name = "seat_status_id", nullable = false)
    private SeatStatus seatStatus;

    /**
     * order type id
     */
    @ManyToOne
    @JoinColumn(name = "order_type_id", nullable = false)
    private OrderType orderType;

    public TicketDTO map2DTO() {
        return new TicketDTO(
                this.getId(),
                this.clientName,
                this.isPay,
                this.executionFilm.getId(),
                this.seat.getId(),
                this.seat.getName(),
                this.seatStatus.getId(),
                this.seatStatus.getName(),
                this.orderType.getId(),
                this.orderType.getName(),
                this.executionFilm.getFilm().getName(),
                this.executionFilm.getSession().getTime().toString(),
                this.seat.getRow().getName(),
                this.isCancelled,
                this.getCreateAt(),
                this.getUpdateAt()
        );
    }
}
