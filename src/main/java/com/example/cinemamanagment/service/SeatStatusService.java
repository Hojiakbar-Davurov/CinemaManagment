package com.example.cinemamanagment.service;

import com.example.cinemamanagment.model.dto.SeatStatusDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SeatStatusService {

    String SERVICE_NAME = "seat status";

    void autoSave();

    SeatStatusDTO save(SeatStatusDTO dto);

    List<SeatStatusDTO> findAll();

    SeatStatusDTO findById(Long id);

    SeatStatusDTO update(Long id, SeatStatusDTO dto);

    void deleteById(Long id);
}
