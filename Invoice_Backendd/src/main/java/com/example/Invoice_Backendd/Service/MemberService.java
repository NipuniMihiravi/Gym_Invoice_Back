package com.example.Invoice_Backendd.Service;


import com.example.Invoice_Backendd.Model.Member;
import com.example.Invoice_Backendd.Model.Payment;
import com.example.Invoice_Backendd.Repository.MemberRepository;
import com.example.Invoice_Backendd.Repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    // Call this periodically or on member fetch
    public void updateInactiveMembers() {
        List<Member> allMembers = memberRepository.findAll();

        for (Member member : allMembers) {
            // Get latest payment
            Payment lastPayment = paymentRepository.findTopByMemberIdOrderByDateDesc(member.getMemberId());
            if (lastPayment != null) {
                LocalDate lastPaymentDate = lastPayment.getDate();
                long yearsDiff = ChronoUnit.YEARS.between(lastPaymentDate, LocalDate.now());
                if (yearsDiff >= 1 && member.getMembershipStatus().equals("ACTIVE")) {
                    member.setMembershipStatus("Inactive");
                    memberRepository.save(member);
                }
            } else {
                // No payment ever made → mark inactive
                if (!member.getMembershipStatus().equals("Inactive")) {
                    member.setMembershipStatus("Inactive");
                    memberRepository.save(member);
                }
            }
        }
    }

    public Member addMember(Member member) {
        if (member.getMemberId() == null || member.getMemberId().isEmpty()) {
            member.setMemberId(generateMemberId());
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

    public Member updateMember(String id, Member updatedMember) {
        return memberRepository.findById(id).map(member -> {
            updatedMember.setId(id);

            if (updatedMember.getMemberId() == null || updatedMember.getMemberId().isEmpty()) {
                updatedMember.setMemberId(member.getMemberId());
            }

            return memberRepository.save(updatedMember);
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
