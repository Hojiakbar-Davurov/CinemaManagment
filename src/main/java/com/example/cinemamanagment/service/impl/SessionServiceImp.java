package com.example.cinemamanagment.service.impl;

import com.example.cinemamanagment.exeptions.ResourceAlreadyExistsException;
import com.example.cinemamanagment.exeptions.ResourceNotFoundException;
import com.example.cinemamanagment.model.domain.Session;
import com.example.cinemamanagment.model.dto.SessionDTO;
import com.example.cinemamanagment.repository.SessionRepository;
import com.example.cinemamanagment.service.SessionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class SessionServiceImp implements SessionService {
    @Autowired
    private SessionRepository sessionRepository;

    @Override
    public SessionDTO save(SessionDTO sessionDTO) {
        log.debug("Request come to " + SERVICE_NAME + " service to save, DTO:{}", sessionDTO);

        // check session exists by name
        if (sessionRepository.existsBySessionTime(sessionDTO.getSessionTime())) {
            throw new ResourceAlreadyExistsException(SERVICE_NAME + " already exists by name: " + sessionDTO.getSessionTime());
        }

        // save session
        SessionDTO savedSessionDTO = sessionRepository.save(sessionDTO.map2Entity()).map2DTO();
        log.debug(SERVICE_NAME + " saved, DTO:{}", savedSessionDTO);

        return savedSessionDTO;
    }

    @Override
    public List<SessionDTO> findAll() {
        log.debug("Request come to " + SERVICE_NAME + " service to get all");

        return sessionRepository.findAll()
                .stream()
                .map(Session::map2DTO)
                .collect(Collectors.toList());
    }

    @Override
    public SessionDTO findById(Long id) {
        log.debug("Request come to " + SERVICE_NAME + " service to find by id:{}", id);

        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(SERVICE_NAME + " not found by id:" + id));

        SessionDTO sessionDTO = session.map2DTO();
        log.debug(SERVICE_NAME + " found, DTO:{}", sessionDTO);

        return sessionDTO;
    }

    @Override
    public SessionDTO update(Long id, SessionDTO sessionDTO) {
        log.debug("Request come to " + SERVICE_NAME + " service to update by id:{},  DTO:{}", id, sessionDTO);

        // find session by id
        Session optionalSession = sessionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(SERVICE_NAME + " not found by id:" + id));
        // update session
        Session session = sessionDTO.map2Entity();
        session.setId(optionalSession.getId());

        SessionDTO saveSessionDTO = sessionRepository.save(session).map2DTO();
        log.debug(SERVICE_NAME + " updated, DTO:{}", saveSessionDTO);

        return saveSessionDTO;
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Request come to " + SERVICE_NAME + " service to delete by id:{}", id);

        if (!sessionRepository.existsById(id))
            throw new ResourceNotFoundException(SERVICE_NAME + " not found by id:" + id);

        sessionRepository.deleteById(id);
        log.debug(SERVICE_NAME + " deleted by id:{}", id);
    }
}
