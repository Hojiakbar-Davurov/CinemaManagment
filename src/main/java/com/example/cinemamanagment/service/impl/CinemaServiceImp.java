package com.example.cinemamanagment.service.impl;

import com.example.cinemamanagment.exeptions.ResourceAlreadyExistsException;
import com.example.cinemamanagment.exeptions.ResourceNotFoundException;
import com.example.cinemamanagment.model.domain.Cinema;
import com.example.cinemamanagment.model.dto.CinemaDTO;
import com.example.cinemamanagment.repository.CinemaRepository;
import com.example.cinemamanagment.service.CinemaService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class CinemaServiceImp implements CinemaService {
    @Autowired
    private CinemaRepository cinemaRepository;

    @Override
    public CinemaDTO save(CinemaDTO cinemaDTO) {
        log.debug("Request come to " + SERVICE_NAME + " service to save, DTO:{}", cinemaDTO);

        // check cinema exists by name
        if (cinemaRepository.existsByName(cinemaDTO.getName())) {
            throw new ResourceAlreadyExistsException(SERVICE_NAME + " already exists by name: " + cinemaDTO.getName());
        }

        // save cinema
        CinemaDTO savedCinemaDTO = cinemaRepository.save(cinemaDTO.map2Entity()).map2DTO();
        log.debug(SERVICE_NAME + " saved, DTO:{}", savedCinemaDTO);

        return savedCinemaDTO;
    }

    @Override
    public List<CinemaDTO> findAll(Pageable pageable) {
        log.debug("Request come to " + SERVICE_NAME + " service to get all, page{}", pageable);

        return cinemaRepository.findAll(pageable)
                .stream()
                .map(Cinema::map2DTO)
                .collect(Collectors.toList());
    }

    @Override
    public CinemaDTO findById(Long id) {
        log.debug("Request come to " + SERVICE_NAME + " service to find by id:{}", id);

        Cinema cinema = cinemaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(SERVICE_NAME + " not found by id:" + id));

        CinemaDTO cinemaDTO = cinema.map2DTO();
        log.debug(SERVICE_NAME + " found, DTO:{}", cinemaDTO);

        return cinemaDTO;
    }

    @Override
    public CinemaDTO update(Long id, CinemaDTO cinemaDTO) {
        log.debug("Request come to " + SERVICE_NAME + " service to update by id:{},  DTO:{}", id, cinemaDTO);

        // find cinema by id
        Cinema optionalCinema = cinemaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(SERVICE_NAME + " not found by id:" + id));

        // update cinema
        Cinema cinema = cinemaDTO.map2Entity();
        cinema.setId(optionalCinema.getId());

        CinemaDTO saveCinemaDTO = cinemaRepository.save(cinema).map2DTO();
        log.debug(SERVICE_NAME + " updated, DTO:{}", saveCinemaDTO);

        return saveCinemaDTO;
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Request come to " + SERVICE_NAME + " service to delete by id:{}", id);

        if (!cinemaRepository.existsById(id))
            throw new ResourceNotFoundException(SERVICE_NAME + " not found by id: " + id);

        cinemaRepository.deleteById(id);
        log.debug(SERVICE_NAME + " deleted by id:{}", id);
    }
}
