package com.example.practiceproject.exception;

import lombok.Data;

import java.util.Date;

/**
 * ApplicationError represents an error response in the application.
 * It contains information about the error status, message, and timestamp of the error occurrence.
 */
@Data
public class ApplicationError {
    private int status;
    private String message;
    private Date timestamp;

    public ApplicationError(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = new Date();
    }
}
