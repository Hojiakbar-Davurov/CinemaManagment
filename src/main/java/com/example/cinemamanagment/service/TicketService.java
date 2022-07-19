package com.example.cinemamanagment.service;

import com.example.cinemamanagment.model.dto.FreeSeatInExecutionFilmDTO;
import com.example.cinemamanagment.model.dto.TicketDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;

@Service
public interface TicketService {

    String SERVICE_NAME = "ticket";

    void cancelNotPayment();

    Page<FreeSeatInExecutionFilmDTO> findFreeSeatByExecutionFilm(Long executionFilmId, Pageable pageable);

    TicketDTO save(TicketDTO dto);

    List<TicketDTO> findAll(Pageable pageable);

    TicketDTO findById(Long id);

    TicketDTO update(Long id, TicketDTO dto);

    void deleteById(Long id);

    ByteArrayInputStream downloadTicket(Long ticketId);
}
