package com.example.Invoice_Backendd.Controller;

import com.example.Invoice_Backendd.Model.Attendance;
import com.example.Invoice_Backendd.Repository.AttendanceRepository;
import com.example.Invoice_Backendd.Service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // POST: Mark attendance
    @PostMapping("/mark")
    public ResponseEntity<String> markAttendance(@RequestBody Map<String, String> request) {
        String memberId = request.get("memberId");
        LocalDate today = LocalDate.now();

        // Check if attendance already exists for today
        boolean alreadyMarked = attendanceRepository.existsByMemberIdAndDate(memberId, today);
        if (alreadyMarked) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("You have already marked attendance today.");
        }

        // Save attendance
        Attendance attendance = new Attendance();
        attendance.setMemberId(memberId);
        attendance.setDate(today);
        attendance.setTime(LocalTime.now());

        attendanceRepository.save(attendance);
        return ResponseEntity.ok("Attendance marked successfully!");
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
}
