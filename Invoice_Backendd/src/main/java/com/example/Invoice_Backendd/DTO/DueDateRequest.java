package com.example.Invoice_Backendd.DTO;


import java.time.LocalDate;

public class DueDateRequest {
    private LocalDate date;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}