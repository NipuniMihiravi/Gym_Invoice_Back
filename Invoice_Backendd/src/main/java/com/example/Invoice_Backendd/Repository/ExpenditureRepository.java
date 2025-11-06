package com.example.Invoice_Backendd.Repository;

import com.example.Invoice_Backendd.Model.Expenditure;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenditureRepository extends MongoRepository<Expenditure, String> {
    List<Expenditure> findByNameContainingIgnoreCase(String name);
    List<Expenditure> findByDateBetween(LocalDate from, LocalDate to);
}
