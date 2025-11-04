package com.example.Invoice_Backendd.DTO;

public class PendingPaymentDTO {
    private String memberId;
    private String memberName;
    private String membershipType;
    private String joinedDate;
    private long attendanceCount;
    private boolean paymentDone;

    public PendingPaymentDTO(String memberId, String memberName, String membershipType, String joinedDate,
                             long attendanceCount, boolean paymentDone) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.membershipType = membershipType;
        this.joinedDate = joinedDate;
        this.attendanceCount = attendanceCount;
        this.paymentDone = paymentDone;
    }

    // getters
    public String getMemberId() { return memberId; }
    public String getMemberName() { return memberName; }
    public String getMembershipType() { return membershipType; }
    public String getJoinedDate() { return joinedDate; }
    public long getAttendanceCount() { return attendanceCount; }
    public boolean isPaymentDone() { return paymentDone; }
}
