package com.ectrvia.trivia.service;

import com.ectrvia.trivia.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllActiveCategories();
    Category getCategoryById(Long id);
    Category createCategory(String name, String description);
    void deleteCategory(Long id);
}
