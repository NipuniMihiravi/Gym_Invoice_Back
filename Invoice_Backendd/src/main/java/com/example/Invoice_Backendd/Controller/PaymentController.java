package com.example.Invoice_Backendd.Controller;

import com.example.Invoice_Backendd.DTO.PendingPaymentDTO;
import com.example.Invoice_Backendd.Model.Payment;
import com.example.Invoice_Backendd.Service.EmailService;
import com.example.Invoice_Backendd.Service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;




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
