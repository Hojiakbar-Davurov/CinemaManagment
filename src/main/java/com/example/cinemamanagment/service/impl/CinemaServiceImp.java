package com.example.cinemamanagment.service.impl;

import com.example.cinemamanagment.exeptions.ResourceAlreadyExistsException;
import com.example.cinemamanagment.exeptions.ResourceNotFoundException;
import com.example.cinemamanagment.mapper.CinemaMapper;
import com.example.cinemamanagment.model.domain.Cinema;
import com.example.cinemamanagment.model.dto.CinemaDTO;
import com.example.cinemamanagment.repository.CinemaRepository;
import com.example.cinemamanagment.service.CinemaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class CinemaServiceImp implements CinemaService {
    private final CinemaRepository cinemaRepository;
    private final CinemaMapper mapper;

    @Override
    public CinemaDTO save(CinemaDTO dto) {
        log.debug("Request come to " + SERVICE_NAME + " service to save, DTO:{}", dto);

        // check cinema exists by name
        if (cinemaRepository.existsByName(dto.getName())) {
            throw new ResourceAlreadyExistsException(SERVICE_NAME + " already exists by name: " + dto.getName());
        }

        // save cinema
        final var entity = mapper.toEntity(dto);
        cinemaRepository.save(entity);
        return mapper.toDto(entity);
    }

    @Override
    public List<CinemaDTO> findAll(Pageable pageable) {
        log.debug("Request come to " + SERVICE_NAME + " service to get all, page{}", pageable);

        return cinemaRepository.findAll(pageable)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Optional<CinemaDTO> findById(Long id) {
        log.debug("Request come to " + SERVICE_NAME + " service to find by id:{}", id);

        Cinema cinema = cinemaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(SERVICE_NAME + " not found by id:" + id));

        return cinemaRepository.findById(id)
                .map(mapper::toDto);
    }

    @Override
    public CinemaDTO update(Long id, CinemaDTO cinemaDTO) {
        log.debug("Request come to " + SERVICE_NAME + " service to update by id:{},  DTO:{}", id, cinemaDTO);

        // find cinema by id
        Cinema optionalCinema = cinemaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(SERVICE_NAME + " not found by id:" + id));

        // update cinema
        Cinema cinema = mapper.toEntity(cinemaDTO);
        cinema.setId(optionalCinema.getId());

        var entity = mapper.toEntity(cinemaDTO);
        return mapper.toDto(
                cinemaRepository.save(entity));
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
