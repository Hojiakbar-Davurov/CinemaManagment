package com.example.cinemamanagment.service;

import com.example.cinemamanagment.model.dto.CinemaDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CinemaService {

    String SERVICE_NAME = "cinema";

    CinemaDTO save(CinemaDTO cinemaDTO);

    List<CinemaDTO> findAll(Pageable pageable);

    Optional<CinemaDTO> findById(Long id);

    CinemaDTO update(Long id, CinemaDTO cinemaDTO);

    void deleteById(Long id);
}
