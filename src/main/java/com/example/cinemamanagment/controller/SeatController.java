package com.example.cinemamanagment.controller;

import com.example.cinemamanagment.model.dto.ApiResponseWrapperDTO;
import com.example.cinemamanagment.model.dto.CinemaDTO;
import com.example.cinemamanagment.model.dto.SeatDTO;
import com.example.cinemamanagment.service.SeatService;
import io.github.jhipster.web.util.PaginationUtil;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api/seat")
public class SeatController {
    private static final String SERVICE_NAME = "seat";

    @Autowired
    private SeatService seatService;

    @GetMapping("/seats-in-row/{rowId}")
    public HttpEntity<List<SeatDTO>> findSeatsInRow(@PathVariable Long rowId, Pageable pageable) {
        log.debug("GET request come to controller from url=.../api/" + SERVICE_NAME + "/seats-in-row/{id} to get all seats by row id: {}, page{}", rowId, pageable);

        List<SeatDTO> dtoList = seatService.findAllSeatInRow(rowId, pageable);
        PageImpl<SeatDTO> page = new PageImpl<>(dtoList, pageable, dtoList.size());

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok()
                .headers(headers)
                .body(page.getContent());
    }

    @PostMapping
    public HttpEntity<ApiResponseWrapperDTO> save(@Valid @RequestBody SeatDTO dto) {
        log.debug("POST request come to controller from url=.../api/" + SERVICE_NAME + " to save, DTO:{}", dto);

        SeatDTO saveSeatDTO = seatService.save(dto);
        log.debug("Saved " + SERVICE_NAME + " response came from " + SERVICE_NAME + " service, DTO:{}", saveSeatDTO);

        return ResponseEntity.ok(
                new ApiResponseWrapperDTO(
                        SERVICE_NAME + " saved",
                        HttpStatus.CREATED.value(),
                        saveSeatDTO
                ));
    }

    @GetMapping
    public HttpEntity<List<SeatDTO>> findAll(Pageable pageable) {
        log.debug("GET request come to controller from url=.../api/" + SERVICE_NAME + " to get all " + SERVICE_NAME + ", page{}", pageable);

        List<SeatDTO> dtoList = seatService.findAll(pageable);
        PageImpl<SeatDTO> page = new PageImpl<>(dtoList, pageable, dtoList.size());

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok()
                .headers(headers)
                .body(page.getContent());
    }

    @GetMapping("/{id}")
    public HttpEntity<SeatDTO> findById(@PathVariable Long id) {
        log.debug("GET request come to controller from url=.../api/" + SERVICE_NAME + "/{id} to get by id: {},", id);

        SeatDTO seatDTO = seatService.findById(id);
        log.debug(SERVICE_NAME + " response came from " + SERVICE_NAME + " service, " + SERVICE_NAME + "DTO:{}", seatDTO);

        return ResponseEntity.ok().body(seatDTO);
    }

    @PutMapping("/{id}")
    public HttpEntity<ApiResponseWrapperDTO> update(@PathVariable Long id, @Valid @RequestBody SeatDTO dto) {
        log.debug("PUT request come to controller from url=.../api/" + SERVICE_NAME + "/{id} to update by id: {}, " + SERVICE_NAME + "DTO:{}", id, dto);

        SeatDTO updateSeatDTO = seatService.update(id, dto);
        log.debug("Updated " + SERVICE_NAME + " response came from " + SERVICE_NAME + " service, " + SERVICE_NAME + "DTO:{}", updateSeatDTO);

        return ResponseEntity.ok(new ApiResponseWrapperDTO(
                SERVICE_NAME + " updated",
                HttpStatus.ACCEPTED.value(),
                updateSeatDTO
        ));
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteById(@PathVariable Long id) {
        log.debug("DELETE request come to controller from url=.../api/" + SERVICE_NAME + "/{id} to delete by id: {}", id);

        seatService.deleteById(id);
        log.debug(SERVICE_NAME + " deleted at controller by id: {}", id);

        return ResponseEntity
                .noContent()
                .build();
    }
}
