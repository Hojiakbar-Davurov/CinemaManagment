package com.example.cinemamanagment.controller;

import com.example.cinemamanagment.model.dto.ApiResponseWrapperDTO;
import com.example.cinemamanagment.model.dto.CinemaDTO;
import com.example.cinemamanagment.service.CinemaService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/api/cinema")
public class CinemaController {
    private static final String SERVICE_NAME = "cinema";
    @Autowired
    private CinemaService cinemaService;

    @PostMapping
    public HttpEntity<ApiResponseWrapperDTO> save(@Valid @RequestBody CinemaDTO cinemaDTO) {
        log.debug("POST request come to controller from url=.../api/" + SERVICE_NAME + " to save, DTO:{}", cinemaDTO);

        CinemaDTO saveCinemaDTO = cinemaService.save(cinemaDTO);
        log.debug("Saved " + SERVICE_NAME + " response came from " + SERVICE_NAME + " service, DTO:{}", saveCinemaDTO);

        return ResponseEntity.ok(
                new ApiResponseWrapperDTO(
                        SERVICE_NAME + " saved",
                        HttpStatus.CREATED.value(),
                        saveCinemaDTO
                ));
    }

    @GetMapping
    public HttpEntity<?> findAll() {
        log.debug("GET request come to controller from url=.../api/" + SERVICE_NAME + " to get all " + SERVICE_NAME);

        return ResponseEntity
                .ok()
                .body(cinemaService.findAll());
    }

    @GetMapping("/{id}")
    public HttpEntity<CinemaDTO> findById(@PathVariable Long id) {
        log.debug("GET request come to controller from url=.../api/" + SERVICE_NAME + "/{id} to get by id: {},", id);

        CinemaDTO cinemaDTO = cinemaService.findById(id);
        log.debug(SERVICE_NAME + " response came from " + SERVICE_NAME + " service, " + SERVICE_NAME + "DTO:{}", cinemaDTO);

        return ResponseEntity.ok().body(cinemaDTO);
    }

    @PutMapping("/{id}")
    public HttpEntity<ApiResponseWrapperDTO> update(@PathVariable Long id, @Valid @RequestBody CinemaDTO cinemaDTO) {
        log.debug("PUT request come to controller from url=.../api/" + SERVICE_NAME + "/{id} to update by id: {}, " + SERVICE_NAME + "DTO:{}", id, cinemaDTO);

        CinemaDTO updateCinemaDTO = cinemaService.update(id, cinemaDTO);
        log.debug("Updated " + SERVICE_NAME + " response came from " + SERVICE_NAME + " service, " + SERVICE_NAME + "DTO:{}", updateCinemaDTO);

        return ResponseEntity.ok(new ApiResponseWrapperDTO(
                SERVICE_NAME + " updated",
                HttpStatus.ACCEPTED.value(),
                updateCinemaDTO
        ));
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteById(@PathVariable Long id) {
        log.debug("DELETE request come to controller from url=.../api/" + SERVICE_NAME + "/{id} to delete by id: {}", id);

        cinemaService.deleteById(id);
        log.debug(SERVICE_NAME + " deleted at controller by id: {}", id);

        return ResponseEntity
                .noContent()
                .build();
    }
}
