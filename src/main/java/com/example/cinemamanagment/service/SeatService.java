package com.example.cinemamanagment.service;

import com.example.cinemamanagment.model.dto.SeatDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SeatService {

    String SERVICE_NAME = "seat";

    List<SeatDTO> findAllSeatInRow(Long id, Pageable pageable);

    SeatDTO save(SeatDTO dto);

    List<SeatDTO> findAll(Pageable pageable);

    SeatDTO findById(Long id);

    SeatDTO update(Long id, SeatDTO dto);

    void deleteById(Long id);
}
