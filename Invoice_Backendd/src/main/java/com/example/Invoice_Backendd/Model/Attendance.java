package com.example.Invoice_Backendd.Model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.time.LocalTime;

@Document(collection = "attendance")
public class Attendance {

    @Id
    private String id;

    private String memberId;
    private LocalDate date;
    private LocalTime time; // corrected type

    // Default constructor (required by Spring Data)
    public Attendance() {
    }

    // Constructor with memberId and date
    public Attendance(String memberId, LocalDate date, LocalTime time) {
        this.memberId = memberId;
        this.date = date;
        this.time = time;
    }

    // Getters and Setters
    public String getId() { return id; }

    public String getMemberId() { return memberId; }
    public void setMemberId(String memberId) { this.memberId = memberId; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public LocalTime getTime() { return time; }
    public void setTime(LocalTime time) { this.time = time; }
}
