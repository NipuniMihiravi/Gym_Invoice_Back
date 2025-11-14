package com.example.Invoice_Backendd.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.time.LocalDate;

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
            double amount,
            String paymentMethod,
            LocalDate dueDate,
            LocalDate payDate,
            String status
    ) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject("LIFE FITNESS PARTNER - Payment Notification");

        String body =
                "Hello " + memberName + " (Member ID: " + memberId + "),<br><br>" +
                        "<b>Your Payment Receipt</b><br><br>" +

                        "🧾 <b>Bill No:</b> " + billNo + "<br>" +
                        "📅 <b>Month Paid For:</b> " + (dueDate != null ? dueDate.toString() : "-") + "<br>" +
                        "📅 <b>Payment Date:</b> " + (payDate != null ? payDate.toString() : "-") + "<br>" +
                        "💰 <b>Amount:</b> Rs. " + amount + "<br>" +
                        "💳 <b>Payment Method:</b> " + (paymentMethod != null ? paymentMethod : "-") + "<br>" +
                        "🔖 <b>Status:</b> " + status + "<br><br>" +

                        "Thank you for choosing <b>Pulse Fitness</b>!<br>" +
                        "Stay healthy. Stay strong. 💪";

        helper.setText(body, true);
        mailSender.send(message);
    }



    // ✅ Send Member QR Code as Email Attachment
    public void sendActiveRegistrationEmail(String toEmail, String name, String memberId,
                                            LocalDate joinedDate, String membershipType) throws MessagingException {

        String subject = "✅ Registration Activated!";
        String body = "Hello " + name + ",\n\n" +
                "Your membership has been activated successfully.\n" +
                "Member ID: " + memberId + "\n" +
                "Joined Date: " + joinedDate + "\n" +
                "Membership Type: " + membershipType + "\n\n" +
                "Welcome to our Gym!";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(body);

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
