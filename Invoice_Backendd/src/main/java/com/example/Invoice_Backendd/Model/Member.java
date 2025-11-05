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
    private String signatureOwner;

    public Member(String id, String name, String gender, String address,LocalDate joinedDate, String fees, String specialDescription, String membershipType, String memberId, Double regFee, String regStatus, String membershipStatus, String qrCode, String residence, String city, String landPhone, String mobile, String dob, String civilStatus, String idType, String idNumber, String email, String officeAddress, String officeMobile, boolean reasonEndurance, boolean reasonFitness, boolean reasonWeightLoss, boolean reasonStrength, boolean reasonMuscle, boolean newspaper, boolean leaflet, boolean friend, boolean member, boolean facebook, String emergencyName, String emergencyRelationship, String emergencyMobile, String emergencyLand, List<String> parq, String weight, String height, String fat, Boolean termsAccepted, String liabilityDate, String memberSignature, String note, String dateOffice, String signatureOwner) {
        this.id = id;
        this.name = name;
        this.gender = gender;
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
        this.residence = residence;
        this.city = city;
        this.landPhone = landPhone;
        this.mobile = mobile;
        this.dob = dob;
        this.civilStatus = civilStatus;
        this.idType = idType;
        this.idNumber = idNumber;
        this.email = email;
        this.officeAddress = officeAddress;
        this.officeMobile = officeMobile;
        this.reasonEndurance = reasonEndurance;
        this.reasonFitness = reasonFitness;
        this.reasonWeightLoss = reasonWeightLoss;
        this.reasonStrength = reasonStrength;
        this.reasonMuscle = reasonMuscle;
        this.newspaper = newspaper;
        this.leaflet = leaflet;
        this.friend = friend;
        this.member = member;
        this.facebook = facebook;
        this.emergencyName = emergencyName;
        this.emergencyRelationship = emergencyRelationship;
        this.emergencyMobile = emergencyMobile;
        this.emergencyLand = emergencyLand;
        this.parq = parq;
        this.weight = weight;
        this.height = height;
        this.fat = fat;
        this.termsAccepted = termsAccepted;
        this.liabilityDate = liabilityDate;
        this.memberSignature = memberSignature;
        this.note = note;
        this.dateOffice = dateOffice;
        this.signatureOwner = signatureOwner;
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

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLandPhone() {
        return landPhone;
    }

    public void setLandPhone(String landPhone) {
        this.landPhone = landPhone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getCivilStatus() {
        return civilStatus;
    }

    public void setCivilStatus(String civilStatus) {
        this.civilStatus = civilStatus;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOfficeAddress() {
        return officeAddress;
    }

    public void setOfficeAddress(String officeAddress) {
        this.officeAddress = officeAddress;
    }

    public String getOfficeMobile() {
        return officeMobile;
    }

    public void setOfficeMobile(String officeMobile) {
        this.officeMobile = officeMobile;
    }

    public boolean isReasonEndurance() {
        return reasonEndurance;
    }

    public void setReasonEndurance(boolean reasonEndurance) {
        this.reasonEndurance = reasonEndurance;
    }

    public boolean isReasonFitness() {
        return reasonFitness;
    }

    public void setReasonFitness(boolean reasonFitness) {
        this.reasonFitness = reasonFitness;
    }

    public boolean isReasonWeightLoss() {
        return reasonWeightLoss;
    }

    public void setReasonWeightLoss(boolean reasonWeightLoss) {
        this.reasonWeightLoss = reasonWeightLoss;
    }

    public boolean isReasonStrength() {
        return reasonStrength;
    }

    public void setReasonStrength(boolean reasonStrength) {
        this.reasonStrength = reasonStrength;
    }

    public boolean isReasonMuscle() {
        return reasonMuscle;
    }

    public void setReasonMuscle(boolean reasonMuscle) {
        this.reasonMuscle = reasonMuscle;
    }

    public boolean isLeaflet() {
        return leaflet;
    }

    public void setLeaflet(boolean leaflet) {
        this.leaflet = leaflet;
    }

    public boolean isNewspaper() {
        return newspaper;
    }

    public void setNewspaper(boolean newspaper) {
        this.newspaper = newspaper;
    }

    public boolean isFriend() {
        return friend;
    }

    public void setFriend(boolean friend) {
        this.friend = friend;
    }

    public boolean isMember() {
        return member;
    }

    public void setMember(boolean member) {
        this.member = member;
    }

    public boolean isFacebook() {
        return facebook;
    }

    public void setFacebook(boolean facebook) {
        this.facebook = facebook;
    }

    public String getEmergencyName() {
        return emergencyName;
    }

    public void setEmergencyName(String emergencyName) {
        this.emergencyName = emergencyName;
    }

    public String getEmergencyRelationship() {
        return emergencyRelationship;
    }

    public void setEmergencyRelationship(String emergencyRelationship) {
        this.emergencyRelationship = emergencyRelationship;
    }

    public String getEmergencyMobile() {
        return emergencyMobile;
    }

    public void setEmergencyMobile(String emergencyMobile) {
        this.emergencyMobile = emergencyMobile;
    }

    public String getEmergencyLand() {
        return emergencyLand;
    }

    public void setEmergencyLand(String emergencyLand) {
        this.emergencyLand = emergencyLand;
    }

    public List<String> getParq() {
        return parq;
    }

    public void setParq(List<String> parq) {
        this.parq = parq;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public Boolean getTermsAccepted() {
        return termsAccepted;
    }

    public void setTermsAccepted(Boolean termsAccepted) {
        this.termsAccepted = termsAccepted;
    }

    public String getLiabilityDate() {
        return liabilityDate;
    }

    public void setLiabilityDate(String liabilityDate) {
        this.liabilityDate = liabilityDate;
    }

    public String getMemberSignature() {
        return memberSignature;
    }

    public void setMemberSignature(String memberSignature) {
        this.memberSignature = memberSignature;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDateOffice() {
        return dateOffice;
    }

    public void setDateOffice(String dateOffice) {
        this.dateOffice = dateOffice;
    }

    public String getSignatureowner() {
        return signatureOwner;
    }

    public void setSignatureOwner(String signatureOwner) {
        this.signatureOwner = signatureOwner;
    }
}

