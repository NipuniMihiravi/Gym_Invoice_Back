package com.example.Invoice_Backendd.Controller;

import com.example.Invoice_Backendd.Model.Expenditure;
import com.example.Invoice_Backendd.Repository.ExpenditureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/expenditures")
@CrossOrigin(origins = "*")
public class ExpenditureController {

    @Autowired
    private ExpenditureRepository repo;

    @PostMapping
    public Expenditure addExpenditure(@RequestBody Expenditure exp) {
        return repo.save(exp);
    }

    @GetMapping
    public List<Expenditure> getAllExpenditures() {
        return repo.findAll();
    }

    @GetMapping("/filter")
    public List<Expenditure> filterExpenditures(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String fromDate,
            @RequestParam(required = false) String toDate) {

        LocalDate from = (fromDate != null && !fromDate.isEmpty()) ? LocalDate.parse(fromDate) : LocalDate.MIN;
        LocalDate to = (toDate != null && !toDate.isEmpty()) ? LocalDate.parse(toDate) : LocalDate.now();

        List<Expenditure> list = repo.findByDateBetween(from, to);

        if (name != null && !name.isEmpty()) {
            list.removeIf(e -> !e.getName().toLowerCase().contains(name.toLowerCase()));
        }

        return list;
    }
}
