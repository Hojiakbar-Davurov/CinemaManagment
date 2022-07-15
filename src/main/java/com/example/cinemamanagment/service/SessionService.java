package com.example.cinemamanagment.service;

import com.example.cinemamanagment.model.dto.SessionDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SessionService {
    String SERVICE_NAME = "session";

    SessionDTO save(SessionDTO dto);

    List<SessionDTO> findAll();

    SessionDTO findById(Long id);

    SessionDTO update(Long id, SessionDTO dto);

    void deleteById(Long id);
}
