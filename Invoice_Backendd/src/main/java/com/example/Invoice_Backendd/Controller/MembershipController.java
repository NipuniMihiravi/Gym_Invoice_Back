package com.example.Invoice_Backendd.Controller;

import com.example.Invoice_Backendd.Model.Membership;
import com.example.Invoice_Backendd.Repository.MemberRepository;
import com.example.Invoice_Backendd.Repository.MembershipRepository;
import com.example.Invoice_Backendd.Service.MembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/memberships")
@CrossOrigin(origins = "*") // Allow all origins for development
public class MembershipController {

    @Autowired
    private MembershipService service;



    @GetMapping
    public List<Membership> getAll() {
        return service.getAllMemberships();
    }

    @GetMapping("/{id}")
    public Membership getById(@PathVariable String id) {
        return service.getMembershipById(id);
    }


    @PostMapping
    public Membership create(@RequestBody Membership membership) {
        return service.addMembership(membership);
    }

    @PutMapping("/{id}")
    public Membership update(@PathVariable String id, @RequestBody Membership membership) {
        return service.updateMembership(id, membership);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.deleteMembership(id);
    }
}
