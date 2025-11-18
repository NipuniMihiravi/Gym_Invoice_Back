package com.example.Invoice_Backendd.Controller;

import com.example.Invoice_Backendd.Model.Activity;
import com.example.Invoice_Backendd.Service.ActivityService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/activity")
public class ActivityController {

    private final ActivityService service;

    public ActivityController(ActivityService service) {
        this.service = service;
    }

    @PostMapping("/{categoryId}")
    public Activity add(@PathVariable Long categoryId, @RequestBody Activity act) {
        return service.addActivity(categoryId, act);
    }

    @PutMapping("/{id}")
    public Activity update(@PathVariable Long id, @RequestBody Activity act) {
        return service.updateActivity(id, act);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return service.deleteActivity(id);
    }
}
