package com.example.Invoice_Backendd.Service;

import com.example.Invoice_Backendd.Model.Attendance;
import com.example.Invoice_Backendd.Repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    // ✅ Mark attendance with duplicate check
    public String markAttendance(String memberId) {
        LocalDate today = LocalDate.now();

        // Check if attendance is already marked for today
        boolean alreadyMarked = attendanceRepository.existsByMemberIdAndDate(memberId, today);
        if (alreadyMarked) {
            return "Attendance already marked for today!";
        }

        // ✅ Get current time as string
        String currentTime = LocalTime.now().toString();

        // ✅ Create new attendance record
        Attendance attendance = new Attendance(memberId, today, currentTime);
        attendanceRepository.save(attendance);

        return "Attendance marked for Member: " + memberId + " at " + currentTime;
    }

    // ✅ Get all attendance records
    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }

    // ✅ Get attendance records for a specific member
    public List<Attendance> getAttendanceByMember(String memberId) {
        return attendanceRepository.findByMemberId(memberId);
    }
}
