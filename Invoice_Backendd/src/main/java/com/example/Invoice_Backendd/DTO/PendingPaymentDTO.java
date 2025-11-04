package com.example.Invoice_Backendd.DTO;

public class PendingPaymentDTO {
    private String memberId;
    private String memberName;
    private long attendanceCount;

    public PendingPaymentDTO(String memberId, String memberName, long attendanceCount) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.attendanceCount = attendanceCount;
    }
}
