package com.example.cinemamanagment.service;

import com.example.cinemamanagment.model.dto.OrderTypeDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderTypeService {

    String SERVICE_NAME = "order type";

    void autoSave();

    OrderTypeDTO save(OrderTypeDTO dto);

    List<OrderTypeDTO> findAll(Pageable pageable);

    OrderTypeDTO findById(Long id);

    OrderTypeDTO update(Long id, OrderTypeDTO dto);

    void deleteById(Long id);
}
