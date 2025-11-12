package com.example.Invoice_Backendd.Controller;

import com.example.Invoice_Backendd.DTO.PendingPaymentDTO;
import com.example.Invoice_Backendd.Model.Payment;
import com.example.Invoice_Backendd.Repository.PaymentRepository;
import com.example.Invoice_Backendd.Service.EmailService;
import com.example.Invoice_Backendd.Service.PaymentService;
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
    public ResponseEntity<Payment> createPayment(@RequestBody Map<String, Object> payload) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            Payment payment = new Payment();
            payment.setMemberId((String) payload.get("memberId"));
            payment.setAmount(Double.parseDouble(payload.get("amount").toString()));
            payment.setDate(LocalDate.parse((String) payload.get("date"), formatter));      // Next Due Date
            payment.setPayDate(LocalDate.parse((String) payload.get("payDate"), formatter)); // Actual payment date
            payment.setStatus((String) payload.get("status"));
            payment.setPaymentMethod((String) payload.get("paymentMethod"));

            Payment saved = paymentService.addPayment(payment);

            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping
    public ResponseEntity<Payment> createPaymentOrAbsent(@RequestBody Map<String, Object> payload) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            Payment payment = new Payment();
            payment.setMemberId((String) payload.get("memberId"));
            payment.setMemberName((String) payload.get("memberName"));
            payment.setMemberEmail((String) payload.get("memberEmail"));
            payment.setDate(LocalDate.parse((String) payload.get("date"), formatter));      // Next Due Date
            payment.setPayDate(payload.get("payDate") != null
                    ? LocalDate.parse((String) payload.get("payDate"), formatter)
                    : null); // Actual payment date
            payment.setPaymentMethod((String) payload.getOrDefault("paymentMethod", "Absent"));

            boolean isAbsent = Boolean.parseBoolean(payload.getOrDefault("absent", "false").toString());
            if (isAbsent) {
                payment.setStatus("Absent");
                payment.setAmount(0);
                payment.setPaymentMethod("Absent");
            } else {
                payment.setStatus("Done");
                payment.setAmount(Double.parseDouble(payload.get("amount").toString()));
            }

            Payment saved = paymentService.addPayment(payment);

            // ✅ Send email for both Done and Absent
            emailService.sendPaymentEmail(
                    payment.getMemberEmail(),
                    payment.getMemberId(),
                    payment.getMemberName(),
                    payment.getDate().getMonth().toString(),
                    payment.getStatus(),
                    payment.getAmount()
            );

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

}
