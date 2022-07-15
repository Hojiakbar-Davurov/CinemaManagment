package com.example.cinemamanagment.service;

import com.example.cinemamanagment.model.dto.CinemaDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CinemaService {

    String SERVICE_NAME = "cinema";

    CinemaDTO save(CinemaDTO cinemaDTO);

    List<CinemaDTO> findAll();

    CinemaDTO findById(Long id);

    CinemaDTO update(Long id, CinemaDTO cinemaDTO);

    void deleteById(Long id);
}
