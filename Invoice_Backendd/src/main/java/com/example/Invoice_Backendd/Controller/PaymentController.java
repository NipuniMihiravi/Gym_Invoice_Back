package com.example.Invoice_Backendd.Controller;

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

            // Selected month (Due Date)
            payment.setDate(LocalDate.parse((String) payload.get("date"), formatter));

            // Pay Date (only for paid)
            payment.setPayDate(payload.get("payDate") != null
                    ? LocalDate.parse((String) payload.get("payDate"), formatter)
                    : null);

            boolean isAbsent = Boolean.parseBoolean(payload.getOrDefault("absent", "false").toString());

            if (isAbsent) {
                // ----------------------------
                //  ⭐ ABSENT PAYMENT LOGIC ⭐
                // ----------------------------
                payment.setStatus("Absent");
                payment.setAmount(0);
                payment.setPaymentMethod("Absent");
            } else {
                // ----------------------------
                //  ⭐ NORMAL PAYMENT LOGIC ⭐
                // ----------------------------
                payment.setStatus("Done");
                payment.setAmount(Double.parseDouble(payload.get("amount").toString()));
                payment.setPaymentMethod((String) payload.get("paymentMethod"));
            }

            // Save payment
            Payment saved = paymentService.addPayment(payment);

            // Prepare email values
            String monthName = payment.getDate().getMonth().toString(); // JANUARY → convert to readable form

            // Send email
            try {
                emailService.sendPaymentEmail(
                        payment.getMemberEmail(),
                        payment.getMemberId(),
                        payment.getBillNo(),
                        payment.getMemberName(),
                        monthName,
                        payment.getStatus(),
                        payment.getAmount()
                );
                System.out.println("Email sent successfully!");
            } catch (MessagingException e) {
                e.printStackTrace();
                System.out.println("Email failed, but payment saved.");
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
                return ResponseEntity.ok(Map.of("billNo", "LTF1000"));
            }

            return ResponseEntity.ok(Map.of("billNo", lastPayment.getBillNo()));

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching last bill");
        }
    }


}
