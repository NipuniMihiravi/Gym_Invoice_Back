package com.example.Invoice_Backendd.Repository;

import com.example.Invoice_Backendd.Model.Member;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MemberRepository extends MongoRepository<Member, String> {
    Optional<Member> findByMemberId(String memberId);
    long countByMembershipStatus(String membershipStatus);
}