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
    // Update member
    public Member updateMember(String id, Member updatedMember) {
        return memberRepository.findById(id).map(member -> {

            String oldStatus = member.getMembershipStatus();
            String newStatus = updatedMember.getMembershipStatus();

            // Update other fields
            member.setName(updatedMember.getName() != null ? updatedMember.getName() : member.getName());
            member.setEmail(updatedMember.getEmail() != null ? updatedMember.getEmail() : member.getEmail());
            member.setMobile(updatedMember.getMobile() != null ? updatedMember.getMobile() : member.getMobile());
            member.setMembershipType(updatedMember.getMembershipType() != null ? updatedMember.getMembershipType() : member.getMembershipType());
            member.setMembershipStatus(newStatus != null ? newStatus : member.getMembershipStatus());

            // Save member first
            Member savedMember = memberRepository.save(member);

            // ✅ Send email ONLY when status changes to ACTIVE
            if (oldStatus != null && newStatus != null
                    && !oldStatus.equalsIgnoreCase(newStatus)
                    && newStatus.equalsIgnoreCase("ACTIVE")) {
                try {
                    emailService.sendActiveRegistrationEmail(
                            savedMember.getEmail(),
                            savedMember.getName(),
                            savedMember.getMemberId(),
                            savedMember.getJoinedDate(),
                            savedMember.getMembershipType()
                    );
                } catch (MessagingException e) {
                    e.printStackTrace();
                    // Optional: log the error
                }
            }

            return savedMember;
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

                // 5️⃣ Filter members who have attendance after due date
                Aggregates.addFields(new Field<>("attendanceAfterDueCount",
                        new Document("$size", new Document("$filter", new Document()
                                .append("input", "$attendance")
                                .append("as", "a")
                                .append("cond", new Document("$gt", Arrays.asList(
                                        new Document("$toDate", "$$a.date"), "$nextDueDate"
                                )))
                        ))
                )),

                // 6️⃣ Keep only active members who actually attended after due date
                Aggregates.match(Filters.and(
                        Filters.lt("nextDueDate", new Date()), // due date passed
                        Filters.gt("attendanceAfterDueCount", 0),
                        Filters.ne("membershipStatus", "Inactive")
                )),

                // 7️⃣ Project the final result fields
                Aggregates.project(Projections.fields(
                        Projections.include("memberId", "name", "mobile", "email", "joinedDate",
                                "membershipType", "lastPaymentDate", "nextDueDate", "attendanceAfterDueCount")
                ))
        );

        return mongoTemplate.getCollection("members")
                .aggregate(pipeline)
                .into(new ArrayList<>());
    }



}
