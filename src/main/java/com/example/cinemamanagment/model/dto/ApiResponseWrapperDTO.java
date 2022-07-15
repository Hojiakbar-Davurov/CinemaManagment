package com.example.cinemamanagment.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseWrapperDTO {

    private String message;
    private int statusCode;

    private Object data;

    public ApiResponseWrapperDTO(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
