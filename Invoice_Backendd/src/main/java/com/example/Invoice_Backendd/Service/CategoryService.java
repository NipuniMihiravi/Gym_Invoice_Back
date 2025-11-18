package com.example.Invoice_Backendd.Service;

import com.example.Invoice_Backendd.Model.Activity;
import com.example.Invoice_Backendd.Model.Category;
import com.example.Invoice_Backendd.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepo;

    // Get all
    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    // Add new category
    public Category addCategory(Category category) {
        category.setId(UUID.randomUUID().toString());
        return categoryRepo.save(category);
    }

    // Update category
    public Category updateCategory(String id, Category updatedCat) {
        Optional<Category> existing = categoryRepo.findById(id);
        if (existing.isEmpty()) return null;

        Category cat = existing.get();
        cat.setName(updatedCat.getName());
        return categoryRepo.save(cat);
    }

    // Delete category
    public boolean deleteCategory(String id) {
        categoryRepo.deleteById(id);
        return true;
    }

    // Add Activity
    public Category addActivity(String categoryId, Activity activity) {
        Optional<Category> optionalCategory = categoryRepo.findById(categoryId);
        if (optionalCategory.isEmpty()) return null;

        Category category = optionalCategory.get();

        activity.setId(UUID.randomUUID().toString());
        category.getActivities().add(activity);

        return categoryRepo.save(category);
    }

    // Update activity
    public Category updateActivity(String categoryId, Activity updatedActivity) {
        Optional<Category> optionalCategory = categoryRepo.findById(categoryId);
        if (optionalCategory.isEmpty()) return null;

        Category category = optionalCategory.get();

        category.getActivities().replaceAll(act ->
                act.getId().equals(updatedActivity.getId()) ? updatedActivity : act
        );

        return categoryRepo.save(category);
    }

    // Delete activity
    public Category deleteActivity(String categoryId, String activityId) {
        Optional<Category> optionalCategory = categoryRepo.findById(categoryId);
        if (optionalCategory.isEmpty()) return null;

        Category category = optionalCategory.get();

        category.setActivities(
                category.getActivities().stream()
                        .filter(a -> !a.getId().equals(activityId))
                        .toList()
        );

        return categoryRepo.save(category);
    }
}
