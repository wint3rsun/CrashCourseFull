package com.crashcourse.SpringBootTraining.MovieService.dto;

public class ErrorResponse {
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }
}
