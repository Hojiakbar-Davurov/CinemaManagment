package com.example.cinemamanagment.service.impl;

import com.example.cinemamanagment.exeptions.ResourceAlreadyExistsException;
import com.example.cinemamanagment.exeptions.ResourceNotFoundException;
import com.example.cinemamanagment.model.domain.Hall;
import com.example.cinemamanagment.model.domain.Row;
import com.example.cinemamanagment.model.domain.Seat;
import com.example.cinemamanagment.model.dto.RowDTO;
import com.example.cinemamanagment.repository.HallRepository;
import com.example.cinemamanagment.repository.RowRepository;
import com.example.cinemamanagment.repository.SeatRepository;
import com.example.cinemamanagment.service.RowService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class RowServiceImp implements RowService {
    @Autowired
    private HallRepository hallRepository;
    @Autowired
    private RowRepository rowRepository;
    @Autowired
    private SeatRepository seatRepository;

    @Override
    public List<RowDTO> findAllRowsInHall(Long hallId, Pageable pageable) {
        log.debug("Request come to " + SERVICE_NAME + " service to get all rows by hall id: {}, page{}", hallId, pageable);

        return rowRepository.findAllByHallId(hallId, pageable)
                .stream()
                .map(Row::map2DTO)
                .collect(Collectors.toList());
    }

    @Override
    public RowDTO save(RowDTO rowDTO) {
        log.debug("Request come to " + SERVICE_NAME + " service to save, DTO:{}", rowDTO);

        // check row exists by name and hall id
        if (rowRepository.existsByNameAndHallId(rowDTO.getName(), rowDTO.getHallId())) {
            throw new ResourceAlreadyExistsException(SERVICE_NAME + " already exists by name: " + rowDTO.getName() + "  and hall id: " + rowDTO.getHallId());
        }

        //find hall by id
        Hall hall = hallRepository.findById(rowDTO.getHallId()).
                orElseThrow(() -> new ResourceNotFoundException("Hall not found by id: " + rowDTO.getHallId()));

        // save row
        Row savedRow = rowRepository.save(rowDTO.map2Entity(hall));

        //save seats by row id
        for (int i = 0; i < savedRow.getNumberOfSeats(); i++) {
            seatRepository.save(new Seat(String.valueOf(i + 1), savedRow));
        }

        log.debug(SERVICE_NAME + " saved, DTO:{}", savedRow.map2DTO());
        return savedRow.map2DTO();
    }

    @Override
    public List<RowDTO> findAll(Pageable pageable) {
        log.debug("Request come to " + SERVICE_NAME + " service to get all, page: {}", pageable);

        return rowRepository.findAll(pageable)
                .stream()
                .map(Row::map2DTO)
                .collect(Collectors.toList());
    }

    @Override
    public RowDTO findById(Long id) {
        log.debug("Request come to " + SERVICE_NAME + " service to find by id:{}", id);

        Row row = rowRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(SERVICE_NAME + " not found by id:" + id));

        RowDTO rowDTO = row.map2DTO();
        log.debug(SERVICE_NAME + " found, DTO:{}", rowDTO);

        return rowDTO;
    }

    @Override
    public RowDTO update(Long id, RowDTO rowDTO) {
        log.debug("Request come to " + SERVICE_NAME + " service to update by id:{},  DTO:{}", id, rowDTO);

        // find hall by id
        Hall optionalHall = hallRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hall not found by id: " + id));

        // find row by id
        Row optionalRow = rowRepository.findById(rowDTO.getId()).
                orElseThrow(() -> new ResourceNotFoundException(SERVICE_NAME + " not found by id: " + rowDTO.getId()));

        // update row
        Row row = rowDTO.map2Entity(optionalHall);
        row.setId(optionalRow.getId());

        RowDTO saveRowDTO = rowRepository.save(row).map2DTO();
        log.debug(SERVICE_NAME + " updated, DTO:{}", saveRowDTO);

        return saveRowDTO;
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Request come to " + SERVICE_NAME + " service to delete by id:{}", id);

        if (!rowRepository.existsById(id))
            throw new ResourceNotFoundException(SERVICE_NAME + " not found by id:" + id);

        rowRepository.deleteById(id);
        log.debug(SERVICE_NAME + " deleted by id:{}", id);
    }
}
