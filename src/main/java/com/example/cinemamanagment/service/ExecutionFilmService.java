package com.example.cinemamanagment.service;

import com.example.cinemamanagment.model.dto.ExecutionFilmDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ExecutionFilmService {

    String SERVICE_NAME = "execution film";

    ExecutionFilmDTO save(ExecutionFilmDTO dto);

    List<ExecutionFilmDTO> findAll();

    ExecutionFilmDTO findById(Long id);

    ExecutionFilmDTO update(Long id, ExecutionFilmDTO dto);

    void deleteById(Long id);
}
