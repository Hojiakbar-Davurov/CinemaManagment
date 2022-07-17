package com.example.cinemamanagment.exeptions;

import com.example.cinemamanagment.model.dto.ApiResponseWrapperDTO;
import org.springframework.http.*;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CinemaManagementExceptionsHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public HttpEntity<?> resourceNotFoundExceptionHandler(ResourceNotFoundException e) {
        ApiResponseWrapperDTO apiResponseWrapperDTO = new ApiResponseWrapperDTO(
                e.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<Object>(apiResponseWrapperDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ResourceAlreadyExistsException.class)
    public HttpEntity<?> resourceAlreadyExistsExceptionHandler(ResourceAlreadyExistsException e) {
        ApiResponseWrapperDTO apiResponseWrapperDTO = new ApiResponseWrapperDTO(
                e.getMessage(),
                HttpStatus.CONFLICT.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiResponseWrapperDTO, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = ResourceNotDeletedException.class)
    public HttpEntity<?> resourceNotDeletedExceptionHandler(ResourceNotDeletedException e) {
        ApiResponseWrapperDTO apiResponseWrapperDTO = new ApiResponseWrapperDTO(
                e.getMessage(),
                HttpStatus.CONFLICT.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiResponseWrapperDTO, HttpStatus.CONFLICT);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<String> details = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        ValidErrorResponse error = new ValidErrorResponse("Validation Failed", details);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}

