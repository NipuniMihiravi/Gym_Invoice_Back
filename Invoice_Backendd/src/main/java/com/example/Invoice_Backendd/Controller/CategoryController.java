package com.example.Invoice_Backendd.Controller;

import com.example.Invoice_Backendd.Model.Category;
import com.example.Invoice_Backendd.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@CrossOrigin(origins = "*")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @PostMapping("/add")
    public Category add(@RequestBody Category cat) {
        return service.addCategory(cat);
    }

    @GetMapping("/all")
    public List<Category> getAll() {
        return service.getAllCategories();
    }

    @PutMapping("/update/{id}")
    public Category update(@PathVariable String id, @RequestBody Category cat) {
        return service.updateCategory(id, cat);
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable String id) {
        return service.deleteCategory(id);
    }
}
