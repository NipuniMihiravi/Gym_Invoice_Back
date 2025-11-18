package com.example.Invoice_Backendd.Controller;

import com.example.Invoice_Backendd.Model.Activity;
import com.example.Invoice_Backendd.Model.Category;
import com.example.Invoice_Backendd.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "*")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // GET ALL
    @GetMapping
    public List<Category> getAll() {
        return categoryService.getAllCategories();
    }

    // ADD CATEGORY
    @PostMapping
    public Category addCategory(@RequestBody Category category) {
        return categoryService.addCategory(category);
    }

    // UPDATE CATEGORY
    @PutMapping("/{id}")
    public Category updateCategory(
            @PathVariable String id,
            @RequestBody Category category
    ) {
        return categoryService.updateCategory(id, category);
    }

    // DELETE CATEGORY
    @DeleteMapping("/{id}")
    public String deleteCategory(@PathVariable String id) {
        categoryService.deleteCategory(id);
        return "Deleted";
    }

    // ADD ACTIVITY
    @PostMapping("/{id}/activities")
    public Category addActivity(@PathVariable String id, @RequestBody Activity activity) {
        return categoryService.addActivity(id, activity);
    }

    // UPDATE ACTIVITY
    @PutMapping("/{catId}/activities")
    public Category updateActivity(
            @PathVariable String catId,
            @RequestBody Activity activity
    ) {
        return categoryService.updateActivity(catId, activity);
    }

    // DELETE ACTIVITY
    @DeleteMapping("/{catId}/activities/{actId}")
    public Category deleteActivity(
            @PathVariable String catId,
            @PathVariable String actId
    ) {
        return categoryService.deleteActivity(catId, actId);
    }
}
