package com.rs.ecom.services;

import com.rs.ecom.model.Category;
import com.rs.ecom.payload.CategoryDTO;
import com.rs.ecom.payload.CategoryResponse;

public interface CategoryService {

	CategoryResponse getAllCategories();

	CategoryDTO createCategory(CategoryDTO categoryDTO);

	String deleteCategory(Long categoryId);

	Category updateCategory(Category category, Long categoryId);

}
