package com.example.cinemamanagment.service.impl;

import com.example.cinemamanagment.exeptions.ResourceAlreadyExistsException;
import com.example.cinemamanagment.exeptions.ResourceNotFoundException;
import com.example.cinemamanagment.model.domain.SeatStatus;
import com.example.cinemamanagment.model.dto.SeatStatusDTO;
import com.example.cinemamanagment.model.enums.SeatStatusEnum;
import com.example.cinemamanagment.repository.SeatStatusRepository;
import com.example.cinemamanagment.service.SeatStatusService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class SeatStatusServiceImp implements SeatStatusService {
    @Autowired
    private SeatStatusRepository seatStatusRepository;

    @Override
    public void autoSave() {

        List<SeatStatusEnum> statuses = Arrays.stream(SeatStatusEnum.values()).toList();
        for (SeatStatusEnum status : statuses) {
            SeatStatusDTO savedSeatStatusDTO = seatStatusRepository.save(new SeatStatus(status.name())).map2DTO();
            log.debug(SERVICE_NAME + " auto saved, DTO:{}", savedSeatStatusDTO);
        }
    }

    @Override
    public SeatStatusDTO save(SeatStatusDTO seatStatusDTO) {
        log.debug("Request come to " + SERVICE_NAME + " service to save, DTO:{}", seatStatusDTO);

        // check seat status exists by name
        if (seatStatusRepository.existsByName(seatStatusDTO.getName())) {
            throw new ResourceAlreadyExistsException(SERVICE_NAME + " already exists by name: " + seatStatusDTO.getName());
        }

        // save seat status
        SeatStatusDTO savedSeatStatusDTO = seatStatusRepository.save(seatStatusDTO.map2Entity()).map2DTO();
        log.debug(SERVICE_NAME + " saved, DTO:{}", savedSeatStatusDTO);

        return savedSeatStatusDTO;
    }

    @Override
    public List<SeatStatusDTO> findAll() {
        log.debug("Request come to " + SERVICE_NAME + " service to get all");

        return seatStatusRepository.findAll()
                .stream()
                .map(SeatStatus::map2DTO)
                .collect(Collectors.toList());
    }

    @Override
    public SeatStatusDTO findById(Long id) {
        log.debug("Request come to " + SERVICE_NAME + " service to find by id:{}", id);

        SeatStatus seatStatus = seatStatusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(SERVICE_NAME + " not found by id:" + id));

        SeatStatusDTO seatStatusDTO = seatStatus.map2DTO();
        log.debug(SERVICE_NAME + " found, DTO:{}", seatStatusDTO);

        return seatStatusDTO;
    }

    @Override
    public SeatStatusDTO update(Long id, SeatStatusDTO seatStatusDTO) {
        log.debug("Request come to " + SERVICE_NAME + " service to update by id:{},  DTO:{}", id, seatStatusDTO);

        // find seat status by id
        SeatStatus optionalSeatStatus = seatStatusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(SERVICE_NAME + " not found by id:" + id));

        // update seat status
        SeatStatus seatStatus = seatStatusDTO.map2Entity();
        seatStatus.setId(optionalSeatStatus.getId());

        SeatStatusDTO saveSeatStatusDTO = seatStatusRepository.save(seatStatus).map2DTO();
        log.debug(SERVICE_NAME + " updated, DTO:{}", saveSeatStatusDTO);

        return saveSeatStatusDTO;
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Request come to " + SERVICE_NAME + " service to delete by id:{}", id);

        if (!seatStatusRepository.existsById(id))
            throw new ResourceNotFoundException(SERVICE_NAME + " not found by id: " + id);

        seatStatusRepository.deleteById(id);
        log.debug(SERVICE_NAME + " deleted by id:{}", id);
    }
}
