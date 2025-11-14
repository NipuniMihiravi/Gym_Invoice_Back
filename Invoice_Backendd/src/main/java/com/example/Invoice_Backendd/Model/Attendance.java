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

    private String memberName;
    private String email;

    private String memberId;
    private LocalDate date;
    private String time; // corrected type

    public Attendance(String id, String memberName, String email, String memberId, LocalDate date, String time) {
        this.id = id;
        this.memberName = memberName;
        this.email = email;
        this.memberId = memberId;
        this.date = date;
        this.time = time;
    }

    public Attendance(String memberId, LocalDate today, String currentTime) {
    }

    public Attendance() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
