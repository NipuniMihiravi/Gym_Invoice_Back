package com.example.Invoice_Backendd.Controller;

import com.example.Invoice_Backendd.Model.Attendance;
import com.example.Invoice_Backendd.Service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin(origins = "*")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    // POST: Mark attendance
    @PostMapping("/mark")
    public String markAttendance(@RequestBody Attendance request) {
        return attendanceService.markAttendance(request.getMemberId());
    }

    // GET: Get all attendance records
    @GetMapping("/all")
    public List<Attendance> getAllAttendance() {
        return attendanceService.getAllAttendance();
    }

    // GET: Get attendance for a specific member
    @GetMapping("/member/{memberId}")
    public List<Attendance> getAttendanceByMember(@PathVariable String memberId) {
        return attendanceService.getAttendanceByMember(memberId);
    }
}
