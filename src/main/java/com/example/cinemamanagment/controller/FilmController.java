package com.example.cinemamanagment.controller;

import com.example.cinemamanagment.model.dto.ApiResponseWrapperDTO;
import com.example.cinemamanagment.model.dto.FilmDTO;
import com.example.cinemamanagment.service.FilmService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/api/film")
public class FilmController {
    private static final String SERVICE_NAME = "film";
    @Autowired
    private FilmService filmService;

    @PostMapping
    public HttpEntity<ApiResponseWrapperDTO> save(@Valid @RequestBody FilmDTO dto) {
        log.debug("POST request come to controller from url=.../api/" + SERVICE_NAME + " to save, DTO:{}", dto);

        FilmDTO saveFilmDTO = filmService.save(dto);
        log.debug("Saved " + SERVICE_NAME + " response came from " + SERVICE_NAME + " service, DTO:{}", saveFilmDTO);

        return ResponseEntity.ok(
                new ApiResponseWrapperDTO(
                        SERVICE_NAME + " saved",
                        HttpStatus.CREATED.value(),
                        saveFilmDTO
                ));
    }

    @GetMapping
    public HttpEntity<?> findAll() {
        log.debug("GET request come to controller from url=.../api/" + SERVICE_NAME + " to get all " + SERVICE_NAME);

        return ResponseEntity
                .ok()
                .body(filmService.findAll());
    }

    @GetMapping("/{id}")
    public HttpEntity<FilmDTO> findById(@PathVariable Long id) {
        log.debug("GET request come to controller from url=.../api/" + SERVICE_NAME + "/{id} to get by id: {},", id);

        FilmDTO filmDTO = filmService.findById(id);
        log.debug(SERVICE_NAME + " response came from " + SERVICE_NAME + " service, " + SERVICE_NAME + "DTO:{}", filmDTO);

        return ResponseEntity.ok().body(filmDTO);
    }

    @PutMapping("/{id}")
    public HttpEntity<ApiResponseWrapperDTO> update(@PathVariable Long id, @Valid @RequestBody FilmDTO dto) {
        log.debug("PUT request come to controller from url=.../api/" + SERVICE_NAME + "/{id} to update by id: {}, " + SERVICE_NAME + "DTO:{}", id, dto);

        FilmDTO updateFilmDTO = filmService.update(id, dto);
        log.debug("Updated " + SERVICE_NAME + " response came from " + SERVICE_NAME + " service, " + SERVICE_NAME + "DTO:{}", updateFilmDTO);

        return ResponseEntity.ok(new ApiResponseWrapperDTO(
                SERVICE_NAME + " updated",
                HttpStatus.ACCEPTED.value(),
                updateFilmDTO
        ));
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteById(@PathVariable Long id) {
        log.debug("DELETE request come to controller from url=.../api/" + SERVICE_NAME + "/{id} to delete by id: {}", id);

        filmService.deleteById(id);
        log.debug(SERVICE_NAME + " deleted at controller by id: {}", id);

        return ResponseEntity
                .noContent()
                .build();
    }
}
