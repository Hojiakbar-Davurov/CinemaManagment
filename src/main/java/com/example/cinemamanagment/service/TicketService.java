package com.example.cinemamanagment.service;

import com.example.cinemamanagment.model.dto.TicketDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TicketService {

    String SERVICE_NAME = "ticket";

    TicketDTO save(TicketDTO dto);

    List<TicketDTO> findAll();

    TicketDTO findById(Long id);

    TicketDTO update(Long id, TicketDTO dto);

    void deleteById(Long id);
}
