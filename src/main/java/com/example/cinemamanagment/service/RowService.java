package com.example.cinemamanagment.service;

import com.example.cinemamanagment.model.dto.RowDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RowService {

    String SERVICE_NAME = "row";

    List<RowDTO> findAllRowsInHall(Long id, Pageable pageable);

    RowDTO save(RowDTO dto);

    List<RowDTO> findAll(Pageable pageable);

    RowDTO findById(Long id);

    RowDTO update(Long id, RowDTO dto);

    void deleteById(Long id);
}
