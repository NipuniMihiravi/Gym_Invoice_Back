package com.example.Invoice_Backendd.Controller;

import com.example.Invoice_Backendd.DTO.PendingPaymentDTO;
import com.example.Invoice_Backendd.Model.Payment;
import com.example.Invoice_Backendd.Service.EmailService;
import com.example.Invoice_Backendd.Service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private EmailService emailService;

    @PostMapping
    public Payment createPayment(@RequestBody Payment payment) {
        Payment savedPayment = paymentService.addPayment(payment);

        // Send Email Notification
        try {
            emailService.sendPaymentEmail(
                    savedPayment.getMemberEmail(),   // email
                    savedPayment.getMemberId(),      // memberId (make sure your Payment model has this)
                    savedPayment.getMemberName(),    // memberName
                    savedPayment.getMonth(),
                    savedPayment.getStatus(),
                    savedPayment.getAmount()
            );
        } catch (Exception e) {
            System.out.println("Failed to send email: " + e.getMessage());
        }

        return savedPayment;
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

    @GetMapping("/pending-payments")
    public List<PendingPaymentDTO> getPendingPaymentMembers() {
        return paymentService.getPendingPaymentMembers();
    }
}
