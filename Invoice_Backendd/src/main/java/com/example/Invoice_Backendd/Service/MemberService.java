package com.example.Invoice_Backendd.Service;

import com.example.Invoice_Backendd.Model.Member;
import com.example.Invoice_Backendd.Model.Payment;
import com.example.Invoice_Backendd.Repository.MemberRepository;
import com.example.Invoice_Backendd.Repository.PaymentRepository;
import com.example.Invoice_Backendd.Util.QRCodeGenerator;
import com.google.zxing.WriterException;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Field;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import jakarta.mail.MessagingException;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.bson.Document;


import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private EmailService emailService;  // ✅ inject EmailService

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    private MongoTemplate mongoTemplate;

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
                            saved.getName(),
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

    public String generateMemberId() {
        long newId = sequenceGeneratorService.getNextSequence("memberId");
        return String.valueOf(newId); // e.g., 1, 2, 3, 4...
    }


    public List<Document> getOverdueAttendanceMembers() {

        List<Bson> pipeline = Arrays.asList(

                // 1️⃣ Join with payments collection
                Aggregates.lookup("payments", "memberId", "memberId", "payments"),

                // 2️⃣ Join with attendance collection
                Aggregates.lookup("attendance", "memberId", "memberId", "attendance"),

                // 3️⃣ Add lastPaymentDate (latest payment or joinedDate)
                Aggregates.addFields(new Field<>("lastPaymentDate",
                        new Document("$ifNull", Arrays.asList(
                                new Document("$max", new Document("$map", new Document()
                                        .append("input", "$payments")
                                        .append("as", "p")
                                        .append("in", new Document("$toDate", "$$p.date"))
                                )),
                                new Document("$toDate", "$joinedDate")
                        ))
                )),

                // 4️⃣ Calculate nextDueDate based on membership type (1, 3, 6, 12 months)
                Aggregates.addFields(new Field<>("nextDueDate",
                        new Document("$dateAdd", new Document()
                                .append("startDate", "$lastPaymentDate")
                                .append("unit", "month")
                                .append("amount", new Document("$switch", new Document()
                                        .append("branches", Arrays.asList(
                                                new Document("case", new Document("$in", Arrays.asList(
                                                        "$membershipType", Arrays.asList("one-one", "one-two")
                                                ))).append("then", 1),
                                                new Document("case", new Document("$in", Arrays.asList(
                                                        "$membershipType", Arrays.asList("three-one", "three-two")
                                                ))).append("then", 3),
                                                new Document("case", new Document("$in", Arrays.asList(
                                                        "$membershipType", Arrays.asList("six-one", "six-two")
                                                ))).append("then", 6),
                                                new Document("case", new Document("$in", Arrays.asList(
                                                        "$membershipType", Arrays.asList("twelve-one", "twelve-two")
                                                ))).append("then", 12)
                                        ))
                                        .append("default", 1)
                                ))
                        )
                )),

                // 5️⃣ Add computed field: hasRecentAttendance
                // True if the member has any attendance AFTER nextDueDate
                Aggregates.addFields(new Field<>("hasRecentAttendance",
                        new Document("$gt", Arrays.asList(
                                new Document("$size", new Document("$filter", new Document()
                                        .append("input", "$attendance")
                                        .append("as", "a")
                                        .append("cond", new Document("$gt", Arrays.asList(
                                                new Document("$toDate", "$$a.date"), "$nextDueDate"
                                        )))
                                )),
                                0
                        ))
                )),

                // 6️⃣ Filter overdue & active members
                Aggregates.match(Filters.and(
                        Filters.lt("nextDueDate", new Date()),
                        Filters.eq("hasRecentAttendance", true),
                        Filters.ne("membershipStatus", "Inactive")
                )),

                // 7️⃣ Project final result fields
                Aggregates.project(Projections.fields(
                        Projections.include("memberId", "name", "mobile", "email","joinedDate",
                                "membershipType", "lastPaymentDate", "nextDueDate"),
                        new Document("daysOverdue",
                                new Document("$divide", Arrays.asList(
                                        new Document("$subtract", Arrays.asList(new Date(), "$nextDueDate")),
                                        1000 * 60 * 60 * 24
                                ))
                        )
                ))
        );

        return mongoTemplate.getCollection("members")
                .aggregate(pipeline)
                .into(new ArrayList<>());
    }


}
