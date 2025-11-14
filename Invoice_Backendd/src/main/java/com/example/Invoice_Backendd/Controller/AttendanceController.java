package com.example.Invoice_Backendd.Controller;

import com.example.Invoice_Backendd.Model.Attendance;
import com.example.Invoice_Backendd.Model.Member;
import com.example.Invoice_Backendd.Repository.AttendanceRepository;
import com.example.Invoice_Backendd.Repository.MemberRepository;
import com.example.Invoice_Backendd.Service.AttendanceService;
import com.example.Invoice_Backendd.Service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin(origins = "*")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private EmailService emailService;

    // POST: Mark attendance
    @PostMapping("/mark")
    public ResponseEntity<String> markAttendance(@RequestBody Map<String, String> request) {
        try {
            String memberId = request.get("memberId");
            LocalDate today = LocalDate.now();

            // Check duplicate
            if (attendanceRepository.existsByMemberIdAndDate(memberId, today)) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Attendance already marked for today.");
            }

            // Fetch member info
            Member member = memberRepository.findByMemberId(memberId)
                    .orElseThrow(() -> new RuntimeException("Member not found"));

            // Save attendance
            Attendance attendance = new Attendance();
            attendance.setMemberId(memberId);
            attendance.setMemberName(member.getName());
            attendance.setEmail(member.getEmail());
            attendance.setDate(today);
            ZoneId sriLankaZone = ZoneId.of("Asia/Colombo");
            LocalTime currentTime = LocalTime.now(sriLankaZone);

// Format as HH:mm:ss
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            attendance.setTime(currentTime.format(formatter));

            attendanceRepository.save(attendance);

            // Send email
            emailService.sendAttendanceEmail(
                    member.getEmail(),
                    member.getMemberId(),
                    member.getName(),
                    today.toString()
            );

            return ResponseEntity.ok("Attendance marked successfully!");
        } catch (MessagingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Attendance marked but email failed.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to mark attendance.");
        }
    }


    // GET: Get all attendance records
    @GetMapping
    public List<Attendance> getAllAttendance() {
        return attendanceService.getAllAttendance();
    }

    // GET: Get attendance for a specific member
    @GetMapping("/member/{memberId}")
    public List<Attendance> getAttendanceByMember(@PathVariable String memberId) {
        return attendanceService.getAttendanceByMember(memberId);
    }

    // GET: Get attendance by memberId and date range
    @GetMapping("/member/{memberId}/range")
    public List<Attendance> getAttendanceByMemberAndDateRange(
            @PathVariable String memberId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        return attendanceRepository.findByMemberIdAndDateBetween(memberId, startDate, endDate);
    }
    // DELETE: Delete attendance by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAttendance(@PathVariable String id) {
        try {
            if (!attendanceRepository.existsById(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Attendance record not found.");
            }

            attendanceRepository.deleteById(id);
            return ResponseEntity.ok("Attendance record deleted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete attendance.");
        }
    }

}
