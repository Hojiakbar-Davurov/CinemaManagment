package com.example.cinemamanagment.service.impl;

import com.example.cinemamanagment.exeptions.ResourceAlreadyExistsException;
import com.example.cinemamanagment.exeptions.ResourceNotFoundException;
import com.example.cinemamanagment.model.domain.ExecutionFilm;
import com.example.cinemamanagment.model.domain.Film;
import com.example.cinemamanagment.model.domain.Hall;
import com.example.cinemamanagment.model.domain.Session;
import com.example.cinemamanagment.model.dto.ExecutionFilmDTO;
import com.example.cinemamanagment.repository.ExecutionFilmRepository;
import com.example.cinemamanagment.repository.FilmRepository;
import com.example.cinemamanagment.repository.HallRepository;
import com.example.cinemamanagment.repository.SessionRepository;
import com.example.cinemamanagment.service.ExecutionFilmService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class ExecutionFilmServiceImp implements ExecutionFilmService {
    @Autowired
    private ExecutionFilmRepository executionFilmRepository;
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private HallRepository hallRepository;
    @Autowired
    private SessionRepository sessionRepository;

    @Override
    public ExecutionFilmDTO save(ExecutionFilmDTO dto) {
        log.debug("Request come to " + SERVICE_NAME + " service to save, DTO:{}", dto);

        // check execution film exists by hall id, film id, session id
        if (executionFilmRepository.existsByHallIdAndFilmIdAndSessionId(dto.getHallId(), dto.getFilmId(), dto.getSessionId())) {
            throw new ResourceAlreadyExistsException(SERVICE_NAME + " already exists by" +
                    " hall id: " + dto.getHallId() +
                    " film id: " + dto.getFilmId() +
                    " session id: " + dto.getSessionId()
            );
        }

        // find hall by id
        Hall hall = hallRepository.findById(dto.getHallId()).
                orElseThrow(() -> new ResourceNotFoundException("hall not found by id: " + dto.getHallId()));

        // find film by id
        Film film = filmRepository.findById(dto.getFilmId()).
                orElseThrow(() -> new ResourceNotFoundException("film not found by id: " + dto.getFilmId()));

        // find hall by id
        Session session = sessionRepository.findById(dto.getSessionId()).
                orElseThrow(() -> new ResourceNotFoundException("session not found by id: " + dto.getSessionId()));

        // save execution film
        ExecutionFilmDTO savedExecutionFilmDTO = executionFilmRepository.save(dto.map2Entity(hall, film, session)).map2DTO();
        log.debug(SERVICE_NAME + " saved, DTO:{}", savedExecutionFilmDTO);

        return savedExecutionFilmDTO;
    }

    @Override
    public List<ExecutionFilmDTO> findAll() {
        log.debug("Request come to " + SERVICE_NAME + " service to get all");

        return executionFilmRepository.findAll()
                .stream()
                .map(ExecutionFilm::map2DTO)
                .collect(Collectors.toList());
    }

    @Override
    public ExecutionFilmDTO findById(Long id) {
        log.debug("Request come to " + SERVICE_NAME + " service to find by id:{}", id);

        ExecutionFilm executionFilm = executionFilmRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(SERVICE_NAME + " not found by id:" + id));

        ExecutionFilmDTO executionFilmDTO = executionFilm.map2DTO();
        log.debug(SERVICE_NAME + " found, DTO:{}", executionFilmDTO);

        return executionFilmDTO;
    }

    @Override
    public ExecutionFilmDTO update(Long id, ExecutionFilmDTO dto) {
        log.debug("Request come to " + SERVICE_NAME + " service to update by id:{},  DTO:{}", id, dto);

        // find execution film by id
        ExecutionFilm optionalExecutionFilm = executionFilmRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(SERVICE_NAME + " not found by id:" + id));

        // find hall by id
        Hall hall = hallRepository.findById(dto.getHallId()).
                orElseThrow(() -> new ResourceNotFoundException("hall not found by id: " + dto.getHallId()));

        // find film by id
        Film film = filmRepository.findById(dto.getFilmId()).
                orElseThrow(() -> new ResourceNotFoundException("film not found by id: " + dto.getFilmId()));

        // find session by id
        Session session = sessionRepository.findById(dto.getSessionId()).
                orElseThrow(() -> new ResourceNotFoundException("session not found by id: " + dto.getSessionId()));

        // update execution film
        ExecutionFilm executionFilm = dto.map2Entity(hall, film, session);
        executionFilm.setId(optionalExecutionFilm.getId());

        ExecutionFilmDTO saveExecutionFilmDTO = executionFilmRepository.save(executionFilm).map2DTO();
        log.debug(SERVICE_NAME + " updated, DTO:{}", saveExecutionFilmDTO);

        return saveExecutionFilmDTO;
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Request come to " + SERVICE_NAME + " service to delete by id:{}", id);

        if (!executionFilmRepository.existsById(id))
            throw new ResourceNotFoundException(SERVICE_NAME + " not found by id:" + id);

        executionFilmRepository.deleteById(id);
        log.debug(SERVICE_NAME + " deleted by id:{}", id);
    }
}
