package com.example.Invoice_Backendd.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    // ✅ Send Payment Confirmation Email
    public void sendPaymentEmail(String to, String memberId, String memberName, String month, String status, double amount) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject("Pulse Fitness - Payment Confirmation");

        String body =
                "Hello " + memberName + " (Member ID: " + memberId + "),<br><br>" +
                        "Your payment details:<br>" +
                        "📅 Month: <b>" + month + "</b><br>" +
                        "💰 Amount: <b>Rs. " + amount + "</b><br>" +
                        "✅ Status: <b>" + status + "</b><br><br>" +
                        "Thank you for staying fit with <b>Pulse Fitness</b>!";

        helper.setText(body, true); // true = enable HTML

        mailSender.send(message);
    }

    // ✅ Send Member QR Code as Email Attachment
    public void sendMemberQRCode(String toEmail, String subject, String body, byte[] qrCode) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(body, true);

        // Attach QR Code as PNG
        helper.addAttachment("membership_qrcode.png", new ByteArrayResource(qrCode));

        mailSender.send(message);
    }
}
