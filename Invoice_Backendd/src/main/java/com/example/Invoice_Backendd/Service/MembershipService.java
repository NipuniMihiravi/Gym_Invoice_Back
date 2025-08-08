package com.example.Invoice_Backendd.Service;

import com.example.Invoice_Backendd.Model.Membership;
import com.example.Invoice_Backendd.Repository.MembershipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MembershipService {

    @Autowired
    private MembershipRepository repository;

    public List<Membership> getAllMemberships() {
        return repository.findAll();
    }

    public Membership getMembershipById(String id) {
        return repository.findById(id).orElse(null);
    }

    public Membership addMembership(Membership membership) {
        return repository.save(membership);
    }

    public Membership updateMembership(String id, Membership membership) {
        Optional<Membership> existing = repository.findById(id);
        if (existing.isPresent()) {
            membership.setId(id);
            return repository.save(membership);
        }
        return null;
    }

    public void deleteMembership(String id) {
        repository.deleteById(id);
    }
}
