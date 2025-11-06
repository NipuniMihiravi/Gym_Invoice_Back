package com.example.Invoice_Backendd.Service;

import com.example.Invoice_Backendd.Model.Expenditure;
import com.example.Invoice_Backendd.Repository.ExpenditureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExpenditureService {

    @Autowired
    private ExpenditureRepository repo;

    // ✅ Add new expenditure
    public Expenditure addExpenditure(Expenditure expenditure) {
        return repo.save(expenditure);
    }

    // ✅ Get all expenditures
    public List<Expenditure> getAllExpenditures() {
        return repo.findAll();
    }

    // ✅ Filter expenditures by date range and optional name
    public List<Expenditure> filterExpenditures(String name, String fromDate, String toDate) {
        LocalDate from = (fromDate != null && !fromDate.isEmpty()) ? LocalDate.parse(fromDate) : LocalDate.MIN;
        LocalDate to = (toDate != null && !toDate.isEmpty()) ? LocalDate.parse(toDate) : LocalDate.now();

        List<Expenditure> filtered = repo.findByDateBetween(from, to);

        if (name != null && !name.isEmpty()) {
            filtered = filtered.stream()
                    .filter(e -> e.getName().toLowerCase().contains(name.toLowerCase()))
                    .collect(Collectors.toList());
        }

        return filtered;
    }

    // ✅ Delete an expenditure by ID
    public void deleteExpenditure(String id) {
        repo.deleteById(id);
    }

    // ✅ Update an existing expenditure by ID
    public Expenditure updateExpenditure(String id, Expenditure updatedExp) {
        Optional<Expenditure> optional = repo.findById(id);
        if (optional.isPresent()) {
            Expenditure exp = optional.get();
            exp.setName(updatedExp.getName());
            exp.setCost(updatedExp.getCost());
            exp.setDate(updatedExp.getDate());
            exp.setDescription(updatedExp.getDescription());
            return repo.save(exp);
        } else {
            throw new RuntimeException("Expenditure not found with id: " + id);
        }
    }

    // ✅ Get total expenditure amount (can be used in analytics)
    public double getTotalExpenditureAmount() {
        return repo.findAll().stream()
                .mapToDouble(Expenditure::getCost)
                .sum();
    }

    // ✅ Get expenditures by name (optional helper)
    public List<Expenditure> getExpendituresByName(String name) {
        return repo.findByNameContainingIgnoreCase(name);
    }

    // ✅ Get single expenditure by ID
    public Expenditure getExpenditureById(String id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Expenditure not found with id: " + id));
    }
}
