package com.example.cinemamanagment.controller;

import com.example.cinemamanagment.model.dto.ApiResponseWrapperDTO;
import com.example.cinemamanagment.model.dto.HallDTO;
import com.example.cinemamanagment.model.dto.RowDTO;
import com.example.cinemamanagment.service.RowService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/api/row")
public class RowController {
    private static final String SERVICE_NAME = "row";

    @Autowired
    private RowService rowService;

    @PostMapping
    public HttpEntity<ApiResponseWrapperDTO> save(@Valid @RequestBody RowDTO dto) {
        log.debug("POST request come to controller from url=.../api/" + SERVICE_NAME + " to save, DTO:{}", dto);

        RowDTO saveRowDTO = rowService.save(dto);
        log.debug("Saved " + SERVICE_NAME + " response came from " + SERVICE_NAME + " service, DTO:{}", saveRowDTO);

        return ResponseEntity.ok(
                new ApiResponseWrapperDTO(
                        SERVICE_NAME + " saved",
                        HttpStatus.CREATED.value(),
                        saveRowDTO
                ));
    }

    @GetMapping
    public HttpEntity<?> findAll() {
        log.debug("GET request come to controller from url=.../api/" + SERVICE_NAME + " to get all " + SERVICE_NAME);

        return ResponseEntity
                .ok()
                .body(rowService.findAll());
    }

    @GetMapping("/{id}")
    public HttpEntity<RowDTO> findById(@PathVariable Long id) {
        log.debug("GET request come to controller from url=.../api/" + SERVICE_NAME + "/{id} to get by id: {},", id);

        RowDTO rowDTO = rowService.findById(id);
        log.debug(SERVICE_NAME + " response came from " + SERVICE_NAME + " service, " + SERVICE_NAME + "DTO:{}", rowDTO);

        return ResponseEntity.ok().body(rowDTO);
    }

    @PutMapping("/{id}")
    public HttpEntity<ApiResponseWrapperDTO> update(@PathVariable Long id, @Valid @RequestBody RowDTO dto) {
        log.debug("PUT request come to controller from url=.../api/" + SERVICE_NAME + "/{id} to update by id: {}, " + SERVICE_NAME + "DTO:{}", id, dto);

        RowDTO updateRowDTO = rowService.update(id, dto);
        log.debug("Updated " + SERVICE_NAME + " response came from " + SERVICE_NAME + " service, " + SERVICE_NAME + "DTO:{}", updateRowDTO);

        return ResponseEntity.ok(new ApiResponseWrapperDTO(
                SERVICE_NAME + " updated",
                HttpStatus.ACCEPTED.value(),
                updateRowDTO
        ));
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteById(@PathVariable Long id) {
        log.debug("DELETE request come to controller from url=.../api/" + SERVICE_NAME + "/{id} to delete by id: {}", id);

        rowService.deleteById(id);
        log.debug(SERVICE_NAME + " deleted at controller by id: {}", id);

        return ResponseEntity
                .noContent()
                .build();
    }
}
