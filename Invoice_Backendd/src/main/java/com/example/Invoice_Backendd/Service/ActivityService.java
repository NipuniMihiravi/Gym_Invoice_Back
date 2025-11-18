package com.example.Invoice_Backendd.Service;



import com.example.Invoice_Backendd.Model.Activity;
import com.example.Invoice_Backendd.Model.Category;
import com.example.Invoice_Backendd.Repository.ActivityRepository;
import com.example.Invoice_Backendd.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityService {

    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    private ActivityRepository activityRepo;

    public Activity addActivity(Long categoryId, Activity act) {
        Category cat = categoryRepo.findById(categoryId).orElse(null);
        if (cat == null) return null;

        act.setCategory(cat);
        return activityRepo.save(act);
    }

    public Activity updateActivity(Long actId, Activity updated) {
        return activityRepo.findById(actId).map(act -> {
            act.setName(updated.getName());
            act.setTime(updated.getTime());
            return activityRepo.save(act);
        }).orElse(null);
    }

    public boolean deleteActivity(Long id) {
        if (!activityRepo.existsById(id)) return false;
        activityRepo.deleteById(id);
        return true;
    }
}
