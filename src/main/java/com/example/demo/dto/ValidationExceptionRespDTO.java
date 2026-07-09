package com.example.demo.dto;

import java.time.LocalDateTime;
import java.util.Map;

public class ValidationExceptionRespDTO {
    private LocalDateTime timestamp;
    private String error;
    private String message;
    private int statuscode;
    private String path;
    private Map<String,String> fieldErrors;

    public ValidationExceptionRespDTO(LocalDateTime timestamp, int statusCode, String error, String message, String path, Map<String, String> fieldErrors) {
        this.timestamp = timestamp;
        this.statuscode = statusCode;
        this.error = error;
        this.message = message;
        this.path = path;
        this.fieldErrors = fieldErrors;
    }
}
