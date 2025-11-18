package com.example.Invoice_Backendd.Repository;



import com.example.Invoice_Backendd.Model.Activity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ActivityRepository extends MongoRepository<Activity, Long> {}
