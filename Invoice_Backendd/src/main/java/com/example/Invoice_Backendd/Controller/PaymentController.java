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

    @PostMapping("/payments")
    public ResponseEntity<Payment> createPayment(@RequestBody Map<String, Object> payload) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Create a new Payment object
        Payment payment = new Payment();  // ✅ no-arg constructor must exist in Payment class

        // Set values from payload
        payment.setMemberId((String) payload.get("memberId"));
        payment.setMemberName((String) payload.get("memberName"));
        payment.setMemberEmail((String) payload.get("memberEmail"));
        payment.setMembershipType((String) payload.get("membershipType"));
        payment.setStatus((String) payload.get("status"));
        payment.setPaymentMethod((String) payload.get("paymentMethod"));
        payment.setAmount(Double.parseDouble(payload.get("amount").toString()));
        payment.setJoinedDate(LocalDate.parse((String) payload.get("joinedDate"), formatter));
        payment.setDate(LocalDate.parse((String) payload.get("date"), formatter));
        payment.setPayDate(LocalDate.parse((String) payload.get("payDate"), formatter));

        // Save to repository
        Payment saved = paymentRepository.save(payment);
        return ResponseEntity.ok(saved);
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
