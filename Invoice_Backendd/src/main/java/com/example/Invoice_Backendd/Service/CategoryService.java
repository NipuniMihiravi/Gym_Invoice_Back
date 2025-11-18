package com.example.Invoice_Backendd.Service;



import com.example.Invoice_Backendd.Model.Category;
import com.example.Invoice_Backendd.Repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository repo;

    public CategoryService(CategoryRepository repo) {
        this.repo = repo;
    }

    public List<Category> getAll() {
        return repo.findAll();
    }

    public Category addCategory(Category cat) {
        return repo.save(cat);
    }

    public Category updateCategory(Long id, Category updated) {
        return repo.findById(id).map(cat -> {
            cat.setName(updated.getName());
            return repo.save(cat);
        }).orElse(null);
    }

    public boolean deleteCategory(Long id) {
        if (!repo.existsById(id)) return false;
        repo.deleteById(id);
        return true;
    }
}
