package com.example.Invoice_Backendd.Model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Document(collection = "attendance")
public class Attendance {

    @Id
    private String id;
    private String memberId;
    private LocalDate date;

    public Attendance(String memberId, LocalDate date) {
        this.memberId = memberId;
        this.date = date;
    }

    // getters & setters
    public String getId() { return id; }
    public String getMemberId() { return memberId; }
    public void setMemberId(String memberId) { this.memberId = memberId; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
}
