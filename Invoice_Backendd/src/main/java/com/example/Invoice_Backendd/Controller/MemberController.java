package com.example.Invoice_Backendd.Controller;


import com.example.Invoice_Backendd.Model.Member;
import com.example.Invoice_Backendd.Repository.MemberRepository;
import com.example.Invoice_Backendd.Repository.MembershipRepository;
import com.example.Invoice_Backendd.Service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.bson.Document;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@CrossOrigin(origins = "*")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;  // ✅ Injected

    @PostMapping("/register")
    public Member registerMember(@RequestBody Member member) {
        return memberService.addMember(member);
    }

    @GetMapping
    public List<Member> getAllMembers() {
        return memberService.getAllMembers();
    }

    @GetMapping("/active/count")
    public long getActiveMembersCount() {
        return memberRepository.countByMembershipStatus("ACTIVE"); // Assuming status column
    }
    @GetMapping("/{id}")
    public Member getMember(@PathVariable String id) {
        return memberService.getMemberById(id);
    }

    @GetMapping("/by-member-id/{memberId}")
    public Member getMemberByMemberId(@PathVariable String memberId) {
        return memberService.getMemberByMemberId(memberId);
    }

    @PutMapping("/{id}")
    public Member updateMember(@PathVariable String id, @RequestBody Member member) {
        return memberService.updateMember(id, member);
    }

    @DeleteMapping("/{id}")
    public String deleteMember(@PathVariable String id) {
        return memberService.deleteMember(id);
    }

    @GetMapping("/due-members")
    public ResponseEntity<?> getDueMembers() {
        return ResponseEntity.ok(memberService.getDueMembers());
    }

    @GetMapping("/due-attendance")
    public List<Document> getDueAttendanceMembers() {
        return memberService.getOverdueAttendanceMembers();
    }



}
