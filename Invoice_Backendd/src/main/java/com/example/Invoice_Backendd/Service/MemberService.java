package com.example.Invoice_Backendd.Service;

import com.example.Invoice_Backendd.Model.Member;
import com.example.Invoice_Backendd.Model.Payment;
import com.example.Invoice_Backendd.Repository.MemberRepository;
import com.example.Invoice_Backendd.Repository.PaymentRepository;
import com.example.Invoice_Backendd.Util.QRCodeGenerator;
import com.google.zxing.WriterException;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private EmailService emailService;  // ✅ inject EmailService

    // Check inactive members based on payments
    public void updateInactiveMembers() {
        List<Member> allMembers = memberRepository.findAll();

        for (Member member : allMembers) {
            Payment lastPayment = paymentRepository.findTopByMemberIdOrderByDateDesc(member.getMemberId());
            if (lastPayment != null) {
                LocalDate lastPaymentDate = lastPayment.getDate();
                long yearsDiff = ChronoUnit.YEARS.between(lastPaymentDate, LocalDate.now());
                if (yearsDiff >= 1 && member.getMembershipStatus().equalsIgnoreCase("ACTIVE")) {
                    member.setMembershipStatus("Inactive");
                    memberRepository.save(member);
                }
            } else {
                if (!member.getMembershipStatus().equalsIgnoreCase("Inactive")) {
                    member.setMembershipStatus("Inactive");
                    memberRepository.save(member);
                }
            }
        }
    }

    // Add new member with QR code saved in DB
    public Member addMember(Member member) {
        if (member.getMemberId() == null || member.getMemberId().isEmpty()) {
            member.setMemberId(generateMemberId());
        }

        try {
            String qrCodeBase64 = QRCodeGenerator.generateQRCodeBase64(
                    "MemberID: " + member.getMemberId() + "\nName: " + member.getName(),
                    250, 250
            );
            member.setQrCode(qrCodeBase64);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }

        return memberRepository.save(member);
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Member getMemberById(String id) {
        return memberRepository.findById(id).orElse(null);
    }

    public Member getMemberByMemberId(String memberId) {
        return memberRepository.findByMemberId(memberId).orElse(null);
    }

    // ✅ Update member and send email if ACTIVE
    public Member updateMember(String id, Member updatedMember) {
        return memberRepository.findById(id).map(member -> {
            updatedMember.setId(id);

            if (updatedMember.getMemberId() == null || updatedMember.getMemberId().isEmpty()) {
                updatedMember.setMemberId(member.getMemberId());
            }

            Member saved = memberRepository.save(updatedMember);

            // 🔥 If status is ACTIVE, send email with QR Code as PNG
            if ("ACTIVE".equalsIgnoreCase(saved.getMembershipStatus())) {
                try {
                    String qrData = "MemberID: " + saved.getMemberId() + "\nName: " + saved.getName();
                    byte[] qrCodeImage = QRCodeGenerator.generateQRCodeImage(qrData, 300, 300);

                    emailService.sendMemberQRCode(
                            saved.getUsername(),
                            "Welcome to Pulse Fitness - Membership Activated!",
                            "<h3>Hello " + saved.getName() + ",</h3>" +
                                    "<p>Your membership is now <b>ACTIVE</b>. Please find your QR code attached.</p>",
                            qrCodeImage
                    );
                } catch (WriterException | IOException | MessagingException e) {
                    e.printStackTrace();
                }
            }

            return saved;
        }).orElse(null);
    }

    public String deleteMember(String id) {
        memberRepository.deleteById(id);
        return "Deleted member with ID: " + id;
    }

    private String generateMemberId() {
        Random random = new Random();
        return "M" + (10000000 + random.nextInt(90000000));
    }
}
