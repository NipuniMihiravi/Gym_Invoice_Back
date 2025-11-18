package com.example.Invoice_Backendd.Repository;



import com.example.Invoice_Backendd.Model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category, Long> {}
