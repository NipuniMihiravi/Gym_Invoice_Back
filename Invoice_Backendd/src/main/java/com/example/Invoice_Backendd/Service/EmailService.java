package com.example.Invoice_Backendd.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendPaymentEmail(String to, String name, String month, String status, double amount) {
        String subject = "Payment Status - Pulse Fitness";
        String body = String.format(
                "Hello %s,\n\nYour payment status for %s is marked as: %s.\nAmount: Rs. %.2f\n\nThank you,\nPulse Fitness",
                name, month, status, amount
        );

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("cocolocogarden123@gmail.com"); // Must match your Gmail
        message.setTo(to);                                 // recipient email
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }
}
