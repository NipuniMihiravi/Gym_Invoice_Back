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
    public void sendPaymentEmail(String to, String memberId, String billNo, String memberName, String date, String status, double amount) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject("Pulse Fitness - Payment Notification");

        String body;
        if ("Absent".equals(status)) {
            body = "Hello " + memberName + " (Member ID: " + memberId + "),<br><br>" +
                    "You have been marked as <b>Absent</b> for the month of <b>" + date + "</b>.<br>" +

                    "Thank you for staying with <b>Pulse Fitness</b>!";
        } else {
            body = "Hello " + memberName + " (Member ID: " + memberId + "),<br><br>" +
                    "Your payment details:<br>" +
                    "Bill No: <b>" + billNo + "</b><br>" +
                    "📅 Month: <b>" + date + "</b><br>" +
                    "💰 Amount: <b>Rs. " + amount + "</b><br>" +
                    "✅ Status: <b>" + status + "</b><br>" +
                    "💳 Payment Method: <b>" + (amount > 0 ? "Cash/Online" : "-") + "</b><br><br>" +
                    "Thank you for staying fit with <b>Pulse Fitness</b>!";
        }

        helper.setText(body, true); // enable HTML
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
