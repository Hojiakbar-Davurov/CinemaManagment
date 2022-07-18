package com.example.cinemamanagment.service.impl;

import com.example.cinemamanagment.exeptions.ResourceAlreadyExistsException;
import com.example.cinemamanagment.exeptions.ResourceNotFoundException;
import com.example.cinemamanagment.model.domain.Row;
import com.example.cinemamanagment.model.domain.Seat;
import com.example.cinemamanagment.model.dto.SeatDTO;
import com.example.cinemamanagment.repository.RowRepository;
import com.example.cinemamanagment.repository.SeatRepository;
import com.example.cinemamanagment.service.SeatService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class SeatServiceImp implements SeatService {
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private RowRepository rowRepository;

    @Override
    public List<SeatDTO> findAllSeatInRow(Long rowId, Pageable pageable) {
        log.debug("Request come to " + SERVICE_NAME + " service to get all seats by row id: {}, page{}", rowId, pageable);

        return seatRepository.findAllByRowId(rowId, pageable)
                .stream()
                .map(Seat::map2DTO)
                .collect(Collectors.toList());
    }

    @Override
    public SeatDTO save(SeatDTO seatDTO) {
        log.debug("Request come to " + SERVICE_NAME + " service to save, DTO:{}", seatDTO);

        // check seat exists by name and row id
        if (seatRepository.existsByNameAndRowId(seatDTO.getName(), seatDTO.getRowId())) {
            throw new ResourceAlreadyExistsException(SERVICE_NAME + " already exists by name: " + seatDTO.getName() + "  and row id: " + seatDTO.getRowId());
        }

        // find row by id
        Row row = rowRepository.findById(seatDTO.getRowId()).
                orElseThrow(() -> new ResourceNotFoundException("Row not found by id: " + seatDTO.getRowId()));

        // save seat
        SeatDTO savedSeatDTO = seatRepository.save(seatDTO.map2Entity(row)).map2DTO();
        log.debug(SERVICE_NAME + " saved, DTO:{}", savedSeatDTO);

        return savedSeatDTO;
    }

    @Override
    public List<SeatDTO> findAll(Pageable pageable) {
        log.debug("Request come to " + SERVICE_NAME + " service to get all, page: {}", pageable);

        return seatRepository.findAll(pageable)
                .stream()
                .map(Seat::map2DTO)
                .collect(Collectors.toList());
    }

    @Override
    public SeatDTO findById(Long id) {
        log.debug("Request come to " + SERVICE_NAME + " service to find by id:{}", id);

        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(SERVICE_NAME + " not found by id:" + id));

        SeatDTO seatDTO = seat.map2DTO();
        log.debug(SERVICE_NAME + " found, DTO:{}", seatDTO);

        return seatDTO;
    }

    @Override
    public SeatDTO update(Long id, SeatDTO seatDTO) {
        log.debug("Request come to " + SERVICE_NAME + " service to update by id:{},  DTO:{}", id, seatDTO);

        // find row by id
        Row optionalRow = rowRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Row not found by id: " + id));

        // find seat by id
        Seat optionalSeat = seatRepository.findById(seatDTO.getId()).
                orElseThrow(() -> new ResourceNotFoundException(SERVICE_NAME + " not found by id: " + seatDTO.getId()));

        // update seat
        Seat seat = seatDTO.map2Entity(optionalRow);
        seat.setId(optionalSeat.getId());

        SeatDTO saveSeatDTO = seatRepository.save(seat).map2DTO();
        log.debug(SERVICE_NAME + " updated, DTO:{}", saveSeatDTO);

        return saveSeatDTO;
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Request come to " + SERVICE_NAME + " service to delete by id:{}", id);

        if (!seatRepository.existsById(id))
            throw new ResourceNotFoundException(SERVICE_NAME + " not found by id:" + id);

        seatRepository.deleteById(id);
        log.debug(SERVICE_NAME + " deleted by id:{}", id);
    }
}
