package com.example.Invoice_Backendd.Repository;

import com.example.Invoice_Backendd.Model.Attendance;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends MongoRepository<Attendance, String> {

    // Find attendance for a specific member on a specific date
    Optional<Attendance> findByMemberIdAndDate(String memberId, LocalDate date);

    // Find all attendance records for a specific member
    List<Attendance> findByMemberId(String memberId);

    // Optional: Find attendance records for a specific date (if needed)
    List<Attendance> findByDate(LocalDate date);
}
