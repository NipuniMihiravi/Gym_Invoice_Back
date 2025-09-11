package com.example.Invoice_Backendd.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendPaymentEmail(String to, String memberId, String memberName, String month, String status, double amount) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject("Pulse Fitness - Payment Confirmation");
        helper.setText(
                "Hello " + memberName + " (Member ID: " + memberId + "),\n\n" +
                        "Your payment details:\n" +
                        "Month: " + month + "\n" +
                        "Amount: Rs. " + amount + "\n" +
                        "Status: " + status + "\n\n" +
                        "Thank you for staying fit with Pulse Fitness!"
        );

        // ✅ Send email without PDF
        mailSender.send(message);
    }
}
