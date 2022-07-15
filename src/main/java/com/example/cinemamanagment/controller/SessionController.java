package com.example.cinemamanagment.controller;

import com.example.cinemamanagment.model.dto.ApiResponseWrapperDTO;
import com.example.cinemamanagment.model.dto.SessionDTO;
import com.example.cinemamanagment.service.SessionService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/api/session")
public class SessionController {
    private static final String SERVICE_NAME = "session";
    @Autowired
    private SessionService sessionService;

    @PostMapping
    public HttpEntity<ApiResponseWrapperDTO> save(@Valid @RequestBody SessionDTO dto) {
        log.debug("POST request come to controller from url=.../api/" + SERVICE_NAME + " to save, DTO:{}", dto);

        SessionDTO saveSessionDTO = sessionService.save(dto);
        log.debug("Saved " + SERVICE_NAME + " response came from " + SERVICE_NAME + " service, DTO:{}", saveSessionDTO);

        return ResponseEntity.ok(
                new ApiResponseWrapperDTO(
                        SERVICE_NAME + " saved",
                        HttpStatus.CREATED.value(),
                        saveSessionDTO
                ));
    }

    @GetMapping
    public HttpEntity<?> findAll() {
        log.debug("GET request come to controller from url=.../api/" + SERVICE_NAME + " to get all " + SERVICE_NAME);

        return ResponseEntity
                .ok()
                .body(sessionService.findAll());
    }

    @GetMapping("/{id}")
    public HttpEntity<SessionDTO> findById(@PathVariable Long id) {
        log.debug("GET request come to controller from url=.../api/" + SERVICE_NAME + "/{id} to get by id: {},", id);

        SessionDTO sessionDTO = sessionService.findById(id);
        log.debug(SERVICE_NAME + " response came from " + SERVICE_NAME + " service, " + SERVICE_NAME + "DTO:{}", sessionDTO);

        return ResponseEntity.ok().body(sessionDTO);
    }

    @PutMapping("/{id}")
    public HttpEntity<ApiResponseWrapperDTO> update(@PathVariable Long id, @Valid @RequestBody SessionDTO dto) {
        log.debug("PUT request come to controller from url=.../api/" + SERVICE_NAME + "/{id} to update by id: {}, " + SERVICE_NAME + "DTO:{}", id, dto);

        SessionDTO updateSessionDTO = sessionService.update(id, dto);
        log.debug("Updated " + SERVICE_NAME + " response came from " + SERVICE_NAME + " service, " + SERVICE_NAME + "DTO:{}", updateSessionDTO);

        return ResponseEntity.ok(new ApiResponseWrapperDTO(
                SERVICE_NAME + " updated",
                HttpStatus.ACCEPTED.value(),
                updateSessionDTO
        ));
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteById(@PathVariable Long id) {
        log.debug("DELETE request come to controller from url=.../api/" + SERVICE_NAME + "/{id} to delete by id: {}", id);

        sessionService.deleteById(id);
        log.debug(SERVICE_NAME + " deleted at controller by id: {}", id);

        return ResponseEntity
                .noContent()
                .build();
    }
}
