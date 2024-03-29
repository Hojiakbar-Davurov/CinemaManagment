package com.example.cinemamanagment.service.impl;

import com.example.cinemamanagment.exeptions.ResourceAlreadyExistsException;
import com.example.cinemamanagment.exeptions.ResourceNotFoundException;
import com.example.cinemamanagment.model.domain.*;
import com.example.cinemamanagment.model.dto.FreeSeatInExecutionFilmDTO;
import com.example.cinemamanagment.model.dto.TicketDTO;
import com.example.cinemamanagment.repository.*;
import com.example.cinemamanagment.service.TicketService;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@EnableAsync
@RequiredArgsConstructor
public class TicketServiceImp implements TicketService {
    private final TicketRepository ticketRepository;
    private final ExecutionFilmRepository executionFilmRepository;
    private final SeatStatusRepository seatStatusRepository;
    private final SeatRepository seatRepository;
    private final OrderTypeRepository orderTypeRepository;

    @Override
    @Scheduled(fixedRate = 1000 * 60 * 10)
    public void cancelNotPayment() {
        LocalTime now = LocalTime.now();

        log.debug("Scheduled time is running at: {}", now);

        List<Long> notPaymentTicketId = ticketRepository.findNotPaymentByTime(now);

        for (Long i : notPaymentTicketId) {
            ticketRepository.cancelNotPayment(i);
            log.debug(SERVICE_NAME + " is cancelled because of not payment, ticket id: {}", i);
        }
    }

    @SneakyThrows
    @Override
    public ByteArrayInputStream downloadTicket(Long ticketId) {
        log.debug("Request to download by ticket id: {}", ticketId);

        // find ticket by id
        TicketDTO ticketDTO = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException(SERVICE_NAME + "not found by id: " + ticketId)).map2DTO();

        String inputFilePath = "src/main/resources/static/ticketDemo.pdf"; // Existing file
        PdfReader pdfReader = new PdfReader(inputFilePath);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfStamper pdfStamper = new PdfStamper(pdfReader, baos);

        for (int i = 1; i <= pdfReader.getNumberOfPages(); i++) {
            PdfContentByte pdfContentByte = pdfStamper.getOverContent(i);

            // Add text in existing PDF
            pdfContentByte.beginText();
            pdfContentByte.setFontAndSize(BaseFont.createFont(BaseFont.TIMES_BOLD, BaseFont.CP1257, BaseFont.EMBEDDED), 12);

            // set Cinema name
            pdfContentByte.setTextMatrix(61, 773);
            pdfContentByte.showText(ticketDTO.getCinema());

            // set hall
            pdfContentByte.setTextMatrix(82, 733);
            pdfContentByte.showText(ticketDTO.getHall());

            // set ticket id
            pdfContentByte.setTextMatrix(130, 720);
            pdfContentByte.showText(String.valueOf(ticketDTO.getId()));

            // set row
            pdfContentByte.setTextMatrix(89, 682);
            pdfContentByte.showText(ticketDTO.getRow());

            // set seat
            pdfContentByte.setTextMatrix(103, 669);
            pdfContentByte.showText(ticketDTO.getSeatName());

            // set ticket id
            pdfContentByte.setTextMatrix(315, 771);
            pdfContentByte.showText(String.valueOf(ticketDTO.getId()));

            // set film
            pdfContentByte.setTextMatrix(274, 745);
            pdfContentByte.showText(ticketDTO.getFilm());

            // set session
            pdfContentByte.setTextMatrix(302, 732);
            pdfContentByte.showText(ticketDTO.getSession());

            pdfContentByte.endText();
        }
        pdfStamper.close();

        return new ByteArrayInputStream(baos.toByteArray());
    }

    @Override
    public Page<FreeSeatInExecutionFilmDTO> findFreeSeatByExecutionFilm(Long executionFilmId, Pageable pageable) {
        log.debug("Request to get all free seats by execution film id: {}, page{}: ", executionFilmId, pageable);

        return ticketRepository.findFreeSeatByExecutionFilm(executionFilmId, pageable);
    }

    @Override
    public TicketDTO save(TicketDTO dto) {
        log.debug("Request come to " + SERVICE_NAME + " service to save, DTO:{}", dto);

        // find execution film by id
        ExecutionFilm executionFilm = executionFilmRepository.findById(dto.getExecutionFilmId()).
                orElseThrow(() -> new ResourceNotFoundException("execution film not found by id: " + dto.getExecutionFilmId()));

        // find seat by id
        Seat seat = seatRepository.findById(dto.getSeatId()).
                orElseThrow(() -> new ResourceNotFoundException("seat not found by id: " + dto.getSeatId()));

        // find seat status by id
        SeatStatus seatStatus = seatStatusRepository.findById(dto.getSeatStatusId()).
                orElseThrow(() -> new ResourceNotFoundException("seat status not found by id: " + dto.getSeatStatusId()));

        // find order type by id
        OrderType orderType = orderTypeRepository.findById(dto.getOrderTypeId()).
                orElseThrow(() -> new ResourceNotFoundException("order type not found by id: " + dto.getOrderTypeId()));

        Ticket ticket = dto.map2Entity(executionFilm, seat, seatStatus, orderType);

        // find ticket by execution film id and seat id
        Optional<Ticket> optionalTicket = ticketRepository.findByExecutionFilmIdAndSeatId(dto.getExecutionFilmId(), dto.getSeatId());

        // check ticket exists by execution film id, seat id
        if (optionalTicket.isPresent()) {
            if (!optionalTicket.get().getIsCancelled()) {
                throw new ResourceAlreadyExistsException(SERVICE_NAME + " already exists by" +
                        " execution film id: " + dto.getExecutionFilmId() +
                        " seat id: " + dto.getSeatId());
            } else {
                ticket.setId(optionalTicket.get().getId());
            }
        }

        // save ticket
        TicketDTO savedTicketDTO = ticketRepository.save(ticket).map2DTO();
        log.debug(SERVICE_NAME + " saved, DTO:{}", savedTicketDTO);

        return savedTicketDTO;
    }

    @Override
    public List<TicketDTO> findAll(Pageable pageable) {
        log.debug("Request come to " + SERVICE_NAME + " service to get all, page: {}", pageable);

        return ticketRepository.findAll(pageable)
                .stream()
                .map(Ticket::map2DTO)
                .collect(Collectors.toList());
    }

    @Override
    public TicketDTO findById(Long id) {
        log.debug("Request come to " + SERVICE_NAME + " service to find by id:{}", id);

        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(SERVICE_NAME + " not found by id:" + id));

        TicketDTO ticketDTO = ticket.map2DTO();
        log.debug(SERVICE_NAME + " found, DTO:{}", ticketDTO);

        return ticketDTO;
    }

    @Override
    public TicketDTO update(Long id, TicketDTO dto) {
        log.debug("Request come to " + SERVICE_NAME + " service to update by id:{},  DTO:{}", id, dto);

        // find ticket by id
        Ticket optionalTicket = ticketRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException(SERVICE_NAME + "  not found by id: " + id));

        // find execution film by id
        ExecutionFilm executionFilm = executionFilmRepository.findById(dto.getExecutionFilmId()).
                orElseThrow(() -> new ResourceNotFoundException("execution film not found by id: " + dto.getExecutionFilmId()));

        // find seat by id
        Seat seat = seatRepository.findById(dto.getSeatId()).
                orElseThrow(() -> new ResourceNotFoundException("seat not found by id: " + dto.getSeatId()));

        // find seat status by id
        SeatStatus seatStatus = seatStatusRepository.findById(dto.getSeatStatusId()).
                orElseThrow(() -> new ResourceNotFoundException("seat status not found by id: " + dto.getSeatStatusId()));

        // find order type by id
        OrderType orderType = orderTypeRepository.findById(dto.getOrderTypeId()).
                orElseThrow(() -> new ResourceNotFoundException("order type not found by id: " + dto.getOrderTypeId()));

        // make ticket to update
        Ticket ticket = dto.map2Entity(executionFilm, seat, seatStatus, orderType);
        ticket.setId(optionalTicket.getId());

        // check new update ticket
        boolean exists = ticketRepository.existsByExecutionFilmIdAndSeatId(ticket.getExecutionFilm().getId(), ticket.getSeat().getId());
        if (exists)
            throw new ResourceAlreadyExistsException(SERVICE_NAME + " cannot update to new, because " +
                    "execution film id: " + dto.getExecutionFilmId() + " and " +
                    "seat id: " + dto.getSeatId() + " elready exists");

        // update ticket
        TicketDTO saveTicketDTO = ticketRepository.save(ticket).map2DTO();
        log.debug(SERVICE_NAME + " updated, DTO:{}", saveTicketDTO);

        return saveTicketDTO;
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Request come to " + SERVICE_NAME + " service to delete by id:{}", id);

        if (!ticketRepository.existsById(id))
            throw new ResourceNotFoundException(SERVICE_NAME + " not found by id:" + id);

        ticketRepository.deleteById(id);
        log.debug(SERVICE_NAME + " deleted by id:{}", id);
    }
}
