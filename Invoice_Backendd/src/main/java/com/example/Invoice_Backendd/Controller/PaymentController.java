package com.example.Invoice_Backendd.Controller;

import com.example.Invoice_Backendd.DTO.DueDateRequest;
import com.example.Invoice_Backendd.DTO.PendingPaymentDTO;
import com.example.Invoice_Backendd.Model.Payment;
import com.example.Invoice_Backendd.Repository.PaymentRepository;
import com.example.Invoice_Backendd.Service.EmailService;
import com.example.Invoice_Backendd.Service.PaymentService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private EmailService emailService;  // ✅ inject EmailService


    @PostMapping
    public ResponseEntity<Payment> createPaymentOrAbsent(@RequestBody Map<String, Object> payload) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            Payment payment = new Payment();
            payment.setMemberId((String) payload.get("memberId"));
            payment.setBillNo((String) payload.get("billNo"));
            payment.setMemberName((String) payload.get("memberName"));
            payment.setMemberEmail((String) payload.get("memberEmail"));

            payment.setDate(LocalDate.parse((String) payload.get("date"), formatter));  // Paid Month
            payment.setPayDate(LocalDate.parse((String) payload.get("payDate"), formatter)); // Actual payment date

            String participation = (String) payload.get("participation");

            // ---------- PARTICIPATION LOGIC ----------
            if ("Absent".equalsIgnoreCase(participation)) {
                payment.setStatus("Absent");
                payment.setAmount(0);
                payment.setPaymentMethod("Absent");
            } else {
                payment.setStatus("Done");
                payment.setAmount(Double.parseDouble(payload.get("amount").toString()));
                payment.setPaymentMethod((String) payload.get("paymentMethod"));
            }

            // ---------- SAVE PAYMENT ----------
            Payment saved = paymentService.addPayment(payment);

            // ---------- SEND EMAIL AFTER SAVE ----------
            try {
                emailService.sendPaymentEmail(
                        saved.getMemberEmail(),
                        saved.getMemberId(),
                        saved.getBillNo(),
                        saved.getMemberName(),
                        saved.getAmount(),
                        saved.getPaymentMethod(),
                        saved.getDate(),
                        saved.getPayDate(),
                        saved.getStatus()
                );
                System.out.println("Email sent successfully!");
            } catch (MessagingException e) {
                System.out.println("❌ Email failed, but payment saved.");
                e.printStackTrace();
            }

            return ResponseEntity.ok(saved);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }


    @GetMapping
    public List<Payment> getAllPayments() {
        return paymentService.getAllPayments();
    }

    @GetMapping("/{id}")
    public Payment getPayment(@PathVariable String id) {
        return paymentService.getPaymentById(id);
    }

    @GetMapping("/member/{memberId}")
    public List<Payment> getPaymentsByMemberId(@PathVariable String memberId) {
        return paymentService.getPaymentsByMemberId(memberId);
    }

    @PutMapping("/{id}")
    public Payment updatePayment(@PathVariable String id, @RequestBody Payment payment) {
        return paymentService.updatePayment(id, payment);
    }

    @DeleteMapping("/{id}")
    public String deletePayment(@PathVariable String id) {
        return paymentService.deletePayment(id);
    }

    @GetMapping("/pending")
    public List<PendingPaymentDTO> getPendingPayments(
            @RequestParam String startDate,
            @RequestParam String endDate) {

        return paymentService.getPendingPayments(LocalDate.parse(startDate), LocalDate.parse(endDate));
    }

    @GetMapping("/last-bill")
    public ResponseEntity<?> getLastBill() {
        try {
            Payment lastPayment = paymentRepository.findTopByOrderByIdDesc(); // latest record

            if (lastPayment == null) {
                return ResponseEntity.ok(Map.of("billNo", "LFP1000"));
            }

            return ResponseEntity.ok(Map.of("billNo", lastPayment.getBillNo()));

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching last bill");
        }
    }
    // UPDATE NEXT DUE DATE
    @PutMapping("/update-due-date/{memberId}")
    public ResponseEntity<?> updateDueDate(
            @PathVariable String memberId,
            @RequestBody DueDateRequest request) {

        boolean updated = paymentService.updateDueDate(memberId, request.getDate());

        if (updated) {
            return ResponseEntity.ok("Due date updated successfully");
        } else {
            return ResponseEntity.status(404).body("Payment record not found");
        }
    }



}
