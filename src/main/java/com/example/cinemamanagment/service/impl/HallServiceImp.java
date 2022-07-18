package com.example.cinemamanagment.service.impl;

import com.example.cinemamanagment.exeptions.ResourceAlreadyExistsException;
import com.example.cinemamanagment.exeptions.ResourceNotFoundException;
import com.example.cinemamanagment.model.domain.Cinema;
import com.example.cinemamanagment.model.domain.Hall;
import com.example.cinemamanagment.model.dto.HallDTO;
import com.example.cinemamanagment.repository.CinemaRepository;
import com.example.cinemamanagment.repository.HallRepository;
import com.example.cinemamanagment.service.HallService;
import com.example.cinemamanagment.service.RowService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class HallServiceImp implements HallService {
    @Autowired
    private RowService rowService;
    @Autowired
    private HallRepository hallRepository;
    @Autowired
    private CinemaRepository cinemaRepository;

    @Override
    public HallDTO save(HallDTO hallDTO) {
        log.debug("Request come to " + SERVICE_NAME + " service to save, DTO:{}", hallDTO);

        // check film exists by name and cinema id
        if (hallRepository.existsByNameAndCinemaId(hallDTO.getName(), hallDTO.getCinemaId())) {
            throw new ResourceAlreadyExistsException(SERVICE_NAME + " already exists by name: " + hallDTO.getName() + "  and cinema id: " + hallDTO.getCinemaId());
        }

        //find cinema by id
        Cinema cinema = cinemaRepository.findById(hallDTO.getCinemaId()).orElseThrow(() -> new ResourceNotFoundException("Cinema not found by id: " + hallDTO.getCinemaId()));

        // save hall
        HallDTO savedHallDTO = hallRepository.save(hallDTO.map2Entity(cinema)).map2DTO();
        log.debug(SERVICE_NAME + " saved, DTO:{}", savedHallDTO);

        return savedHallDTO;
    }

    @Override
    public List<HallDTO> findAll(Pageable pageable) {
        log.debug("Request come to " + SERVICE_NAME + " service to get all, page: {}", pageable);

        return hallRepository.findAll(pageable)
                .stream()
                .map(Hall::map2DTO)
                .collect(Collectors.toList());
    }

    @Override
    public HallDTO findById(Long id) {
        log.debug("Request come to " + SERVICE_NAME + " service to find by id:{}", id);

        Hall hall = hallRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(SERVICE_NAME + " not found by id:" + id));

        HallDTO hallDTO = hall.map2DTO();
        log.debug(SERVICE_NAME + " found, DTO:{}", hallDTO);

        return hallDTO;
    }

    @Override
    public HallDTO update(Long id, HallDTO hallDTO) {
        log.debug("Request come to " + SERVICE_NAME + " service to update by id:{},  DTO:{}", id, hallDTO);

        // find hall by id
        Hall optionalHall = hallRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(SERVICE_NAME + " not found by id:" + id));

        // find cinema by id
        Cinema cinema = cinemaRepository.findById(hallDTO.getCinemaId()).orElseThrow(() -> new ResourceNotFoundException("Cinema not found by id: " + hallDTO.getCinemaId()));

        // update hall
        Hall hall = hallDTO.map2Entity(cinema);
        hall.setId(optionalHall.getId());

        HallDTO saveHallDTO = hallRepository.save(hall).map2DTO();
        log.debug(SERVICE_NAME + " updated, DTO:{}", saveHallDTO);

        return saveHallDTO;
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Request come to " + SERVICE_NAME + " service to delete by id:{}", id);

        if (!hallRepository.existsById(id))
            throw new ResourceNotFoundException(SERVICE_NAME + " not found by id:" + id);

        hallRepository.deleteById(id);
        log.debug(SERVICE_NAME + " deleted by id:{}", id);
    }
}
