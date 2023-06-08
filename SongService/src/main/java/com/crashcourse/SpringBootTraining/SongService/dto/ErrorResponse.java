package com.crashcourse.SpringBootTraining.SongService.dto;

public class ErrorResponse {
    private String message;
    public ErrorResponse(String message) {
        this.message = message;
    }
}
