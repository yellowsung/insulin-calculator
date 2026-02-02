package com.insulin.backend.dto;

public class InsulinResponse {
    private int insulin;
    private String status;
    private String message;

    public InsulinResponse(int insulin, String status, String message) {
        this.insulin = insulin;
        this.status = status;
        this.message = message;
    }

    public int getInsulin() {
        return insulin;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
