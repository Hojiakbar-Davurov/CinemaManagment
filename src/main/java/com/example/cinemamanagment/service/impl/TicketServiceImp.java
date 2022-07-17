package com.example.cinemamanagment.service.impl;

import com.example.cinemamanagment.exeptions.ResourceAlreadyExistsException;
import com.example.cinemamanagment.exeptions.ResourceNotFoundException;
import com.example.cinemamanagment.model.domain.*;
import com.example.cinemamanagment.model.dto.TicketDTO;
import com.example.cinemamanagment.repository.*;
import com.example.cinemamanagment.service.TicketService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
public class TicketServiceImp implements TicketService {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private ExecutionFilmRepository executionFilmRepository;
    @Autowired
    private SeatStatusRepository seatStatusRepository;
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private OrderTypeRepository orderTypeRepository;

    @Override
    public TicketDTO save(TicketDTO dto) {
        log.debug("Request come to " + SERVICE_NAME + " service to save, DTO:{}", dto);

        // find execution film by id
        ExecutionFilm executionFilm = executionFilmRepository.findById(dto.getExecutionFilmId()).
                orElseThrow(() -> new ResourceNotFoundException("execution film not found by id: " + dto.getExecutionFilmId()));

        // find seat by id
        Seat seat = seatRepository.findById(dto.getSeatId()).
                orElseThrow(() -> new ResourceNotFoundException("seat not found by id: " + dto.getSeatId()));

        // find seat status by id
        SeatStatus seatStatus = seatStatusRepository.findById(dto.getSeatStatusId()).
                orElseThrow(() -> new ResourceNotFoundException("seat status not found by id: " + dto.getSeatStatusId()));

        // find order type by id
        OrderType orderType = orderTypeRepository.findById(dto.getOrderTypeId()).
                orElseThrow(() -> new ResourceNotFoundException("order type not found by id: " + dto.getOrderTypeId()));

        Ticket ticket = dto.map2Entity(executionFilm, seat, seatStatus, orderType);

        // find ticket by execution film id and seat id
        Optional<Ticket> optionalTicket = ticketRepository.findByExecutionFilmIdAndSeatId(dto.getExecutionFilmId(), dto.getSeatId());

        // check ticket exists by execution film id, seat id
        if (optionalTicket.isPresent()) {
            if (!optionalTicket.get().getIsCancelled()) {
                throw new ResourceAlreadyExistsException(SERVICE_NAME + " already exists by" +
                        " execution film id: " + dto.getExecutionFilmId() +
                        " seat id: " + dto.getSeatId());
            } else {
                ticket.setId(optionalTicket.get().getId());
            }
        }

        // save ticket
        TicketDTO savedTicketDTO = ticketRepository.save(ticket).map2DTO();
        log.debug(SERVICE_NAME + " saved, DTO:{}", savedTicketDTO);

        return savedTicketDTO;
    }

    @Override
    public List<TicketDTO> findAll() {
        log.debug("Request come to " + SERVICE_NAME + " service to get all");

        return ticketRepository.findAll()
                .stream()
                .map(Ticket::map2DTO)
                .collect(Collectors.toList());
    }

    @Override
    public TicketDTO findById(Long id) {
        log.debug("Request come to " + SERVICE_NAME + " service to find by id:{}", id);

        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(SERVICE_NAME + " not found by id:" + id));

        TicketDTO ticketDTO = ticket.map2DTO();
        log.debug(SERVICE_NAME + " found, DTO:{}", ticketDTO);

        return ticketDTO;
    }

    @Override
    public TicketDTO update(Long id, TicketDTO dto) {
        log.debug("Request come to " + SERVICE_NAME + " service to update by id:{},  DTO:{}", id, dto);

        // find ticket by id
        Ticket optionalTicket = ticketRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException(SERVICE_NAME + "  not found by id: " + id));

        // find execution film by id
        ExecutionFilm executionFilm = executionFilmRepository.findById(dto.getExecutionFilmId()).
                orElseThrow(() -> new ResourceNotFoundException("execution film not found by id: " + dto.getExecutionFilmId()));

        // find seat by id
        Seat seat = seatRepository.findById(dto.getSeatId()).
                orElseThrow(() -> new ResourceNotFoundException("seat not found by id: " + dto.getSeatId()));

        // find seat status by id
        SeatStatus seatStatus = seatStatusRepository.findById(dto.getSeatStatusId()).
                orElseThrow(() -> new ResourceNotFoundException("seat status not found by id: " + dto.getSeatStatusId()));

        // find order type by id
        OrderType orderType = orderTypeRepository.findById(dto.getOrderTypeId()).
                orElseThrow(() -> new ResourceNotFoundException("order type not found by id: " + dto.getOrderTypeId()));

        // make ticket to update
        Ticket ticket = dto.map2Entity(executionFilm, seat, seatStatus, orderType);
        ticket.setId(optionalTicket.getId());

        // check new update ticket
        boolean exists = ticketRepository.existsByExecutionFilmIdAndSeatId(ticket.getExecutionFilm().getId(), ticket.getSeat().getId());
        if (exists)
            throw new ResourceAlreadyExistsException(SERVICE_NAME + " cannot update to new, because " +
                    "execution film id: " + dto.getExecutionFilmId() + " and " +
                    "seat id: " + dto.getSeatId() + " elready exists");

        // update ticket
        TicketDTO saveTicketDTO = ticketRepository.save(ticket).map2DTO();
        log.debug(SERVICE_NAME + " updated, DTO:{}", saveTicketDTO);

        return saveTicketDTO;
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Request come to " + SERVICE_NAME + " service to delete by id:{}", id);

        if (!ticketRepository.existsById(id))
            throw new ResourceNotFoundException(SERVICE_NAME + " not found by id:" + id);

        ticketRepository.deleteById(id);
        log.debug(SERVICE_NAME + " deleted by id:{}", id);
    }
}
