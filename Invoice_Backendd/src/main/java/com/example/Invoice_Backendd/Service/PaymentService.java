package com.example.Invoice_Backendd.Service;

import com.example.Invoice_Backendd.DTO.PendingPaymentDTO;
import com.example.Invoice_Backendd.Model.Payment;
import com.example.Invoice_Backendd.Repository.AttendanceRepository;
import com.example.Invoice_Backendd.Repository.MemberRepository;
import com.example.Invoice_Backendd.Repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
            payment.setMemberEmail(member.getUsername());
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
        Optional<Payment> optionalPayment = paymentRepository.findById(id);

        if (optionalPayment.isPresent()) {
            Payment payment = optionalPayment.get();

            // ✅ Update all relevant fields
            payment.setAmount(updatedPayment.getAmount());
            payment.setDate(updatedPayment.getDate());
            payment.setMonth(updatedPayment.getMonth());
            payment.setYear(updatedPayment.getYear());
            payment.setStatus(updatedPayment.getStatus());
            payment.setJoinedDate(updatedPayment.getJoinedDate());
            payment.setMembershipType(updatedPayment.getMembershipType());

            return paymentRepository.save(payment);
        }
        return null;
    }

    public String deletePayment(String id) {
        paymentRepository.deleteById(id);
        return "Deleted payment with ID: " + id;
    }

    public List<PendingPaymentDTO> getPendingPaymentMembers() {
        LocalDate today = LocalDate.now();

        return memberRepository.findAll().stream().map(member -> {
                    LocalDate joined = member.getJoinedDate();
                    LocalDate endDate = joined.plusDays(30);

                    long attendanceCount = attendanceRepository
                            .countByMemberIdAndDateBetween(member.getMemberId(), joined, endDate);

                    boolean paymentDone = paymentRepository
                            .existsByMemberIdAndStatus(member.getMemberId(), "Done");

                    return new PendingPaymentDTO(
                            member.getMemberId(),
                            member.getName(),
                            member.getMembershipType(),
                            joined.toString(),
                            attendanceCount,
                            paymentDone
                    );
                })
                .filter(m -> m.getAttendanceCount() > 0 && !m.isPaymentDone())
                .toList();
    }
}
