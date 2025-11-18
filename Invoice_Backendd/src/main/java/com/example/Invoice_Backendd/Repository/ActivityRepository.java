package com.example.Invoice_Backendd.Repository;

import com.example.Invoice_Backendd.Model.Activity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends MongoRepository<Activity, String> {
}

