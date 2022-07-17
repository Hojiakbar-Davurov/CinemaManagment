package com.example.cinemamanagment.service;

import com.example.cinemamanagment.model.dto.RowDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RowService {

    String SERVICE_NAME = "row";

    RowDTO save(RowDTO dto);

    List<RowDTO> findAll();

    RowDTO findById(Long id);

    RowDTO update(Long id, RowDTO dto);

    void deleteById(Long id);
}
