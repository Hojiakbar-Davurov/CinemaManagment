package com.example.cinemamanagment.controller;

import com.example.cinemamanagment.model.dto.ApiResponseWrapperDTO;
import com.example.cinemamanagment.model.dto.FreeSeatInExecutionFilmDTO;
import com.example.cinemamanagment.model.dto.TicketDTO;
import com.example.cinemamanagment.service.TicketService;
import io.github.jhipster.web.util.PaginationUtil;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.ByteArrayInputStream;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api/ticket")
public class TicketController {
    private static final String SERVICE_NAME = "ticket";
    @Autowired
    private TicketService ticketService;

    @GetMapping("/download-ticket/{ticketId}")
    public HttpEntity<Resource> downloadTicketById(@PathVariable Long ticketId){
        ByteArrayInputStream stream=ticketService.downloadTicket(ticketId);
        InputStreamResource file=new InputStreamResource(stream);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=ticket.pdf")
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
    }

    @GetMapping("/free-seats/{executionFilmId}")
    public HttpEntity<List<FreeSeatInExecutionFilmDTO>> findFreeSeatByExecutionFilm(@PathVariable Long executionFilmId, Pageable pageable) {
        log.debug("REST request to get free seat  by execution film id: {}, page: {}", executionFilmId, pageable);

        var page = ticketService.findFreeSeatByExecutionFilm(executionFilmId, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok()
                .headers(headers)
                .body(page.getContent());
    }

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
    public HttpEntity<List<TicketDTO>> findAll(Pageable pageable) {
        log.debug("GET request come to controller from url=.../api/" + SERVICE_NAME + " to get all " + SERVICE_NAME + ", page{}", pageable);

        List<TicketDTO> dtoList = ticketService.findAll(pageable);
        PageImpl<TicketDTO> page = new PageImpl<>(dtoList, pageable, dtoList.size());

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok()
                .headers(headers)
                .body(page.getContent());
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
