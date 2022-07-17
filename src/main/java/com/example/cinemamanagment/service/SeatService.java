package com.example.cinemamanagment.service;

import com.example.cinemamanagment.model.dto.SeatDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SeatService {

    String SERVICE_NAME = "seat";

    SeatDTO save(SeatDTO dto);

    List<SeatDTO> findAll();

    SeatDTO findById(Long id);

    SeatDTO update(Long id, SeatDTO dto);

    void deleteById(Long id);
}
