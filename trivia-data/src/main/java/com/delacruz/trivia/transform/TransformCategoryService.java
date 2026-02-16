package com.ectrvia.trivia.transform;

import com.ectrvia.trivia.entity.CategoryData;
import com.ectrvia.trivia.model.Category;

public interface TransformCategoryService {
    Category transform(CategoryData categoryData);
    CategoryData transform(Category category);
}
