package com.example.Invoice_Backendd.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;


@Document(collection = "members")
public class Member {
    @Id
    private String id;

    // Existing fields (NOT removed)
    private String name;
    private String gender;
    private String username;
    private String password;
    private String phone;
    private String address;
    private LocalDate joinedDate;
    private String fees;
    private String specialDescription;
    private String membershipType;
    private String memberId;
    private Double regFee;
    private String regStatus;
    private String membershipStatus;
    private String qrCode;

    // ✅ Newly added form fields

    private String residence;
    private String city;
    private String landPhone;
    private String mobile;
    private String dob;
    private String civilStatus;
    private String idType;
    private String idNumber;
    private String email;
    private String officeAddress;
    private String officeMobile;

    // ✅ Reasons for joining
    private boolean reasonEndurance;
    private boolean reasonFitness;
    private boolean reasonWeightLoss;
    private boolean reasonStrength;
    private boolean reasonMuscle;

    // ✅ How did you hear about us
    private boolean newspaper;
    private boolean leaflet;
    private boolean friend;
    private boolean member;
    private boolean facebook;

    // ✅ Emergency contact
    private String emergencyName;
    private String emergencyRelationship;
    private String emergencyMobile;
    private String emergencyLand;

    // ✅ PAR-Q questions (7 Yes/No)
    private List<String> parq;

    // ✅ Measurements
    private String weight;
    private String height;
    private String fat;

    // ✅ Liability form fields
    private Boolean termsAccepted;
    private String liabilityDate;
    private String memberSignature;

    // ✅ Office use
    private String note;
    private String dateOffice;
    private String signatureowner;

    public Member(String id, String name,String gender, String username, String password, String phone, String address,LocalDate joinedDate,String fees, String specialDescription, String membershipType, String memberId, Double regFee, String regStatus, String membershipStatus, String qrCode) {
        this.id = id;
        this.gender = gender;
        this.name = name;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.joinedDate = joinedDate;
        this.fees = fees;

        this.specialDescription = specialDescription;
        this.membershipType = membershipType;
        this.memberId = memberId;
        this.regFee = regFee;
        this.regStatus = regStatus;
        this.membershipStatus = membershipStatus;
        this.qrCode = qrCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getJoinedDate() {
        return joinedDate;
    }

    public void setJoinedDate(LocalDate joinedDate) {
        this.joinedDate = joinedDate;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }



    public String getSpecialDescription() {
        return specialDescription;
    }

    public void setSpecialDescription(String specialDescription) {
        this.specialDescription = specialDescription;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Double getRegFee() {
        return regFee;
    }

    public void setRegFee(Double regFee) {
        this.regFee = regFee;
    }

    public String getRegStatus() {
        return regStatus;
    }

    public void setRegStatus(String regStatus) {
        this.regStatus = regStatus;
    }

    public String getMembershipStatus() {
        return membershipStatus;
    }

    public void setMembershipStatus(String membershipStatus) {
        this.membershipStatus = membershipStatus;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }
}

