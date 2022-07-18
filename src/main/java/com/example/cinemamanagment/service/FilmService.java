package com.example.cinemamanagment.service;

import com.example.cinemamanagment.model.dto.CinemaDTO;
import com.example.cinemamanagment.model.dto.FilmDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FilmService {

    String SERVICE_NAME = "film";

    FilmDTO save(FilmDTO dto);

    List<FilmDTO> findAll(Pageable pageable);

    FilmDTO findById(Long id);

    FilmDTO update(Long id, FilmDTO dto);

    void deleteById(Long id);
}
