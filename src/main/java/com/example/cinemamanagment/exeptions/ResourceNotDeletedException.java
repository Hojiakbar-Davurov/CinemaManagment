package com.example.cinemamanagment.exeptions;

public class ResourceNotDeletedException extends RuntimeException {
    public ResourceNotDeletedException(String message) {
        super(message);
    }
}
