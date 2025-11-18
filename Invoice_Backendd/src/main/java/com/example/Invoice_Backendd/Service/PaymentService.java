package com.example.Invoice_Backendd.Service;

import com.example.Invoice_Backendd.DTO.PendingPaymentDTO;
import com.example.Invoice_Backendd.Model.Member;
import com.example.Invoice_Backendd.Model.Payment;
import com.example.Invoice_Backendd.Repository.AttendanceRepository;
import com.example.Invoice_Backendd.Repository.MemberRepository;
import com.example.Invoice_Backendd.Repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    public Payment addPayment(Payment payment) {

        // ✅ Auto-fill member details from Member collection
        memberRepository.findByMemberId(payment.getMemberId()).ifPresent(member -> {
            payment.setMemberName(member.getName());
            payment.setMemberEmail(member.getEmail());
            payment.setJoinedDate(member.getJoinedDate());
            payment.setMembershipType(member.getMembershipType());
        });

        return paymentRepository.save(payment);
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment getPaymentById(String id) {
        return paymentRepository.findById(id).orElse(null);
    }

    public List<Payment> getPaymentsByMemberId(String memberId) {
        return paymentRepository.findByMemberId(memberId);
    }

    public Payment updatePayment(String id, Payment updatedPayment) {
        return paymentRepository.findById(id).map(payment -> {
            // Update all relevant fields
            payment.setAmount(updatedPayment.getAmount());
            payment.setBillNo(updatedPayment.getBillNo());
            payment.setDate(updatedPayment.getDate());       // due date
            payment.setPayDate(updatedPayment.getPayDate()); // actual payment date
            payment.setStatus(updatedPayment.getStatus());
            payment.setJoinedDate(updatedPayment.getJoinedDate());
            payment.setMembershipType(updatedPayment.getMembershipType());
            payment.setPaymentMethod(updatedPayment.getPaymentMethod());
            payment.setMemberId(updatedPayment.getMemberId());
            payment.setMemberName(updatedPayment.getMemberName());
            payment.setMemberEmail(updatedPayment.getMemberEmail());

            return paymentRepository.save(payment);
        }).orElse(null); // or throw an exception if you prefer
    }


    public String deletePayment(String id) {
        paymentRepository.deleteById(id);
        return "Deleted payment with ID: " + id;
    }

    public List<PendingPaymentDTO> getPendingPayments(LocalDate start, LocalDate end) {

        List<Member> allMembers = memberRepository.findAll();
        List<PendingPaymentDTO> result = new ArrayList<>();

        for (Member member : allMembers) {

            long attendanceCount = attendanceRepository.countByMemberIdAndDateBetween(
                    member.getId(), start, end);

            boolean hasPayment = paymentRepository.existsByMemberIdAndDateBetween(
                    member.getId(), start, end);

            if (!hasPayment) {
                result.add(new PendingPaymentDTO(
                        member.getId(),
                        member.getName(),
                        attendanceCount
                ));
            }
        }

        return result;
    }

    public Payment findByBillNo(String billNo) {
        return paymentRepository.findByBillNo(billNo);
    }






}
