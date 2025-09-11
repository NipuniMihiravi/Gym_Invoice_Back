package com.example.Invoice_Backendd.Service;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;

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

        // ✅ Generate PDF bill with Member ID
        byte[] pdfBytes = generatePaymentPdf(memberId, memberName, month, status, amount);

        // ✅ Attach PDF
        helper.addAttachment("Payment_Invoice.pdf", new ByteArrayResource(pdfBytes));

        // ✅ Send email
        mailSender.send(message);
    }

    private byte[] generatePaymentPdf(String memberId, String memberName, String month, String status, double amount) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        document.add(new Paragraph("Pulse Fitness - Payment Invoice").setBold().setFontSize(18));
        document.add(new Paragraph("Member ID: " + memberId));
        document.add(new Paragraph("Member Name: " + memberName));
        document.add(new Paragraph("Month: " + month));
        document.add(new Paragraph("Amount: Rs. " + amount));
        document.add(new Paragraph("Status: " + status));
        document.add(new Paragraph("\nThank you for your payment!"));

        document.close();

        return out.toByteArray();
    }
}
