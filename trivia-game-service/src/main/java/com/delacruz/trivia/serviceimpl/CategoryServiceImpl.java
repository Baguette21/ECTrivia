package com.delacruz.trivia.serviceimpl;

import com.delacruz.trivia.entity.CategoryData;
import com.delacruz.trivia.model.Category;
import com.delacruz.trivia.repository.CategoryRepository;
import com.delacruz.trivia.service.CategoryService;
import com.delacruz.trivia.transform.TransformCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TransformCategoryService transformCategoryService;

    @Override
    public List<Category> getAllActiveCategories() {
        return categoryRepository.findByIsActiveTrue()
                .stream()
                .map(transformCategoryService::transform)
                .collect(Collectors.toList());
    }

    @Override
    public Category getCategoryById(Long id) {
        CategoryData categoryData = categoryRepository.findById(id).orElse(null);
        return transformCategoryService.transform(categoryData);
    }

    @Override
    @Transactional
    public Category createCategory(String name, String description) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Category name is required");
        }

        String normalizedName = name.trim();
        CategoryData existingCategory = categoryRepository.findAll().stream()
                .filter(c -> c.getName() != null && c.getName().equalsIgnoreCase(normalizedName))
                .findFirst()
                .orElse(null);

        if (existingCategory != null) {
            if (Boolean.TRUE.equals(existingCategory.getIsActive())) {
                throw new IllegalArgumentException("Category name already exists");
            }

            existingCategory.setIsActive(true);
            existingCategory.setDescription(description != null ? description.trim() : "");
            existingCategory = categoryRepository.save(existingCategory);
            return transformCategoryService.transform(existingCategory);
        }

        CategoryData categoryData = new CategoryData();
        categoryData.setName(normalizedName);
        categoryData.setDescription(description != null ? description.trim() : "");
        categoryData.setIsActive(true);
        categoryData = categoryRepository.save(categoryData);

        return transformCategoryService.transform(categoryData);
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        CategoryData categoryData = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        categoryData.setIsActive(false);
        categoryRepository.save(categoryData);
    }
}
