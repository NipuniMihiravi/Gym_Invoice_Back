package com.example.Invoice_Backendd.Repository;

import com.example.Invoice_Backendd.Model.Membership;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembershipRepository extends MongoRepository<Membership, String> {
}
