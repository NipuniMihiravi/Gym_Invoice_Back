package com.example.Invoice_Backendd.Repository;

import com.example.Invoice_Backendd.Model.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

public interface PaymentRepository extends MongoRepository<Payment, String> {
    List<Payment> findByMemberId(String memberId);
    Payment findTopByMemberIdOrderByDateDesc(String memberId);

    boolean existsByMemberIdAndDateBetween(String memberId, LocalDate start, LocalDate end);





}
