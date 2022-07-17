package com.example.cinemamanagment.controller;

import com.example.cinemamanagment.model.dto.ApiResponseWrapperDTO;
import com.example.cinemamanagment.model.dto.TicketDTO;
import com.example.cinemamanagment.service.TicketService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/api/ticket")
public class TicketController {
    private static final String SERVICE_NAME = "ticket";
    @Autowired
    private TicketService ticketService;

    @PostMapping
    public HttpEntity<ApiResponseWrapperDTO> save(@Valid @RequestBody TicketDTO dto) {
        log.debug("POST request come to controller from url=.../api/" + SERVICE_NAME + " to save, DTO:{}", dto);

        TicketDTO saveTicketDTO = ticketService.save(dto);
        log.debug("Saved " + SERVICE_NAME + " response came from " + SERVICE_NAME + " service, DTO:{}", saveTicketDTO);

        return ResponseEntity.ok(
                new ApiResponseWrapperDTO(
                        SERVICE_NAME + " saved",
                        HttpStatus.CREATED.value(),
                        saveTicketDTO
                ));
    }

    @GetMapping
    public HttpEntity<?> findAll() {
        log.debug("GET request come to controller from url=.../api/" + SERVICE_NAME + " to get all " + SERVICE_NAME);

        return ResponseEntity
                .ok()
                .body(ticketService.findAll());
    }

    @GetMapping("/{id}")
    public HttpEntity<TicketDTO> findById(@PathVariable Long id) {
        log.debug("GET request come to controller from url=.../api/" + SERVICE_NAME + "/{id} to get by id: {},", id);

        TicketDTO ticketDTO = ticketService.findById(id);
        log.debug(SERVICE_NAME + " response came from " + SERVICE_NAME + " service, " + SERVICE_NAME + "DTO:{}", ticketDTO);

        return ResponseEntity.ok().body(ticketDTO);
    }

    @PutMapping("/{id}")
    public HttpEntity<ApiResponseWrapperDTO> update(@PathVariable Long id, @Valid @RequestBody TicketDTO dto) {
        log.debug("PUT request come to controller from url=.../api/" + SERVICE_NAME + "/{id} to update by id: {}, " + SERVICE_NAME + "DTO:{}", id, dto);

        TicketDTO updateTicketDTO = ticketService.update(id, dto);
        log.debug("Updated " + SERVICE_NAME + " response came from " + SERVICE_NAME + " service, " + SERVICE_NAME + "DTO:{}", updateTicketDTO);

        return ResponseEntity.ok(new ApiResponseWrapperDTO(
                SERVICE_NAME + " updated",
                HttpStatus.ACCEPTED.value(),
                updateTicketDTO
        ));
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteById(@PathVariable Long id) {
        log.debug("DELETE request come to controller from url=.../api/" + SERVICE_NAME + "/{id} to delete by id: {}", id);

        ticketService.deleteById(id);
        log.debug(SERVICE_NAME + " deleted at controller by id: {}", id);

        return ResponseEntity
                .noContent()
                .build();
    }
}
