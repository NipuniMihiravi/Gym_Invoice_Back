package com.example.Invoice_Backendd.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "payments")
public class Payment {
    @Id
    private String id;

    private String billNo;
    private String memberId;
    private String memberName;
    private String memberEmail;
    private LocalDate joinedDate;
    private String membershipType;
    private LocalDate date;      // Next Due Date
    private LocalDate payDate;   // Actual Payment Date
    private double amount;
    private String status;

    private String paymentMethod;
    private String participation;
    public Payment() {

    }

    public Payment(String id, String billNo, String memberName, String memberId, String memberEmail, LocalDate joinedDate, String membershipType, LocalDate date, LocalDate payDate, double amount, String status, String paymentMethod, String participation) {
        this.id = id;
        this.billNo = billNo;
        this.memberName = memberName;
        this.memberId = memberId;
        this.memberEmail = memberEmail;
        this.joinedDate = joinedDate;
        this.membershipType = membershipType;
        this.date = date;
        this.payDate = payDate;
        this.amount = amount;
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.participation = participation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }

    public LocalDate getJoinedDate() {
        return joinedDate;
    }

    public void setJoinedDate(LocalDate joinedDate) {
        this.joinedDate = joinedDate;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getPayDate() {
        return payDate;
    }

    public void setPayDate(LocalDate payDate) {
        this.payDate = payDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getParticipation() {
        return participation;
    }

    public void setParticipation(String participation) {
        this.participation = participation;
    }
}
