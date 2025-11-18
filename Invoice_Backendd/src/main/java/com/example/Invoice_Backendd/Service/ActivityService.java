package com.example.Invoice_Backendd.Service;

import com.example.Invoice_Backendd.Model.Activity;
import com.example.Invoice_Backendd.Model.Category;
import com.example.Invoice_Backendd.Repository.ActivityRepository;
import com.example.Invoice_Backendd.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ActivityService {

    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    private ActivityRepository activityRepo;

    // Add Activity under Category
    public Activity addActivity(String categoryId, Activity act) {
        Category cat = categoryRepo.findById(categoryId).orElse(null);
        if (cat == null) return null;

        act.setCategoryId(categoryId);
        Activity saved = activityRepo.save(act);

        // Add activityId to category list
        if (cat.getActivityIds() == null) {
            cat.setActivityIds(new ArrayList<>());
        }
        cat.getActivityIds().add(saved.getId());
        categoryRepo.save(cat);

        return saved;
    }

    // Update Activity
    public Activity updateActivity(String actId, Activity updated) {
        return activityRepo.findById(actId).map(a -> {
            a.setName(updated.getName());
            a.setTime(updated.getTime());
            return activityRepo.save(a);
        }).orElse(null);
    }

    // Delete Activity
    public boolean deleteActivity(String actId) {
        Activity act = activityRepo.findById(actId).orElse(null);
        if (act == null) return false;

        // Remove ID from category
        Category cat = categoryRepo.findById(act.getCategoryId()).orElse(null);
        if (cat != null && cat.getActivityIds() != null) {
            cat.getActivityIds().remove(act.getId());
            categoryRepo.save(cat);
        }

        activityRepo.deleteById(actId);
        return true;
    }
}
