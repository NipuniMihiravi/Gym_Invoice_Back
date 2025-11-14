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
    public void sendPaymentEmail(
            String to,
            String memberId,
            String billNo,
            String memberName,
            String date,
            String status,
            double amount
    ) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject("Pulse Fitness - Payment Notification");

        String body;

        if ("Absent".equalsIgnoreCase(status)) {

            // ✔ Clean absent email
            body =
                    "Hello " + memberName + " (Member ID: " + memberId + "),<br><br>" +
                            "You have been marked as <b style='color:red;'>ABSENT</b> for the month of <b>" + date + "</b>.<br>" +
                            "Please make sure to stay consistent with your fitness routine.<br><br>" +
                            "Thanks,<br><b>Pulse Fitness</b>";

        } else {

            // ✔ Clean payment email
            body =
                    "Hello " + memberName + " (Member ID: " + memberId + "),<br><br>" +
                            "<b>Your Payment Receipt</b><br><br>" +
                            "🧾 <b>Bill No:</b> " + billNo + "<br>" +
                            "📅 <b>Month:</b> " + date + "<br>" +
                            "💰 <b>Amount:</b> Rs. " + amount + "<br>" +
                            "💳 <b>Payment Method:</b> Cash/Online<br>" +
                            "✅ <b>Status:</b> Paid<br><br>" +
                            "Thank you for choosing <b>Pulse Fitness</b>!";
        }

        helper.setText(body, true);
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
    public void sendAttendanceEmail(String to, String memberId, String memberName, String date) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject("Pulse Fitness - Attendance Notification");

        String body = "Hello " + memberName + " (Member ID: " + memberId + "),<br><br>" +
                "Your attendance has been marked on <b>" + date + "</b>.<br><br>" +
                "Thank you for staying fit with <b>Pulse Fitness</b>!";

        helper.setText(body, true);
        mailSender.send(message);
    }

}
