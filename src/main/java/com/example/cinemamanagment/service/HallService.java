package com.example.cinemamanagment.service;

import com.example.cinemamanagment.model.dto.HallDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HallService {

    String SERVICE_NAME = "hall";

    HallDTO save(HallDTO dto);

    List<HallDTO> findAll(Pageable pageable);

    HallDTO findById(Long id);

    HallDTO update(Long id, HallDTO dto);

    void deleteById(Long id);
}
