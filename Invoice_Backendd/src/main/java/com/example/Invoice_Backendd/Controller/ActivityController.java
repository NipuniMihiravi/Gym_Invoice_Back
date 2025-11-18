package com.example.Invoice_Backendd.Controller;

import com.example.Invoice_Backendd.Model.Activity;
import com.example.Invoice_Backendd.Service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/activity")
@CrossOrigin(origins = "*")
public class ActivityController {

    @Autowired
    private ActivityService service;

    @PostMapping("/add/{categoryId}")
    public Activity add(@PathVariable String categoryId, @RequestBody Activity act) {
        return service.addActivity(categoryId, act);
    }

    @PutMapping("/update/{id}")
    public Activity update(@PathVariable String id, @RequestBody Activity act) {
        return service.updateActivity(id, act);
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable String id) {
        return service.deleteActivity(id);
    }
}
