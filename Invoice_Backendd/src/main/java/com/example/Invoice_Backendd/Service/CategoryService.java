package com.example.Invoice_Backendd.Service;

import com.example.Invoice_Backendd.Model.Category;
import com.example.Invoice_Backendd.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepo;

    // Add new category
    public Category addCategory(Category category) {
        return categoryRepo.save(category);
    }

    // Get all categories
    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    // Update category
    public Category updateCategory(String id, Category updated) {
        return categoryRepo.findById(id).map(cat -> {
            cat.setName(updated.getName());
            return categoryRepo.save(cat);
        }).orElse(null);
    }

    // Delete category
    public boolean deleteCategory(String id) {
        if (!categoryRepo.existsById(id)) return false;
        categoryRepo.deleteById(id);
        return true;
    }
}
