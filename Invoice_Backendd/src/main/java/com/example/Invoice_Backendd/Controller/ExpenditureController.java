package com.example.Invoice_Backendd.Controller;

import com.example.Invoice_Backendd.Model.Expenditure;
import com.example.Invoice_Backendd.Repository.ExpenditureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/expenditures")
@CrossOrigin(origins = "*") // allows requests from any origin
public class ExpenditureController {

    @Autowired
    private ExpenditureRepository repo;

    // ✅ Add expenditure
    @PostMapping
    public Expenditure addExpenditure(@RequestBody Expenditure exp) {
        return repo.save(exp);
    }

    // ✅ Get all expenditures
    @GetMapping
    public List<Expenditure> getAllExpenditures() {
        return repo.findAll();
    }

    @GetMapping("/filter")
    public List<Expenditure> filterExpenditures(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String fromDate,
            @RequestParam(required = false) String toDate) {

        LocalDate from;
        LocalDate to;

        try {
            from = (fromDate != null && !fromDate.isEmpty()) ? LocalDate.parse(fromDate) : LocalDate.of(1970, 1, 1);
            to = (toDate != null && !toDate.isEmpty()) ? LocalDate.parse(toDate) : LocalDate.now();
        } catch (Exception e) {
            throw new RuntimeException("Invalid date format. Use yyyy-MM-dd");
        }

        // ✅ Fetch records within date range
        List<Expenditure> list = repo.findByDateBetween(from, to);

        // ✅ Optional name filter (case-insensitive)
        if (name != null && !name.trim().isEmpty()) {
            list.removeIf(e -> e.getName() == null || !e.getName().toLowerCase().contains(name.toLowerCase()));
        }

        // ✅ Sort newest first by date
        list.sort((a, b) -> b.getDate().compareTo(a.getDate()));

        return list;
    }


    // ✅ Update expenditure
    @PutMapping("/{id}")
    public Expenditure updateExpenditure(@PathVariable String id, @RequestBody Expenditure updatedExp) {
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

    // ✅ Delete expenditure
    @DeleteMapping("/{id}")
    public String deleteExpenditure(@PathVariable String id) {
        Optional<Expenditure> optional = repo.findById(id);
        if (optional.isPresent()) {
            repo.deleteById(id);
            return "Expenditure deleted successfully";
        } else {
            return "Expenditure not found with id: " + id;
        }
    }
}
