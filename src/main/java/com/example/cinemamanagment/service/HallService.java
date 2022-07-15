package com.example.cinemamanagment.service;

import com.example.cinemamanagment.model.dto.FilmDTO;
import com.example.cinemamanagment.model.dto.HallDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HallService {

    String SERVICE_NAME = "hall";

    HallDTO save(HallDTO dto);

    List<HallDTO> findAll();

    HallDTO findById(Long id);

    HallDTO update(Long id, HallDTO dto);

    void deleteById(Long id);
}
