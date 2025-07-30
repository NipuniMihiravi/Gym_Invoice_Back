package com.example.Invoice_Backendd.Service;

import com.example.Invoice_Backendd.Model.Payment;
import com.example.Invoice_Backendd.Repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public Payment addPayment(Payment payment) {
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
            payment.setAmount(updatedPayment.getAmount());
            payment.setDate(updatedPayment.getDate());
            payment.setMonth(updatedPayment.getMonth());
            payment.setYear(updatedPayment.getYear());
            payment.setStatus(updatedPayment.getStatus());
            return paymentRepository.save(payment);
        }
        return null;
    }

    public String deletePayment(String id) {
        paymentRepository.deleteById(id);
        return "Deleted payment with ID: " + id;
    }
}
