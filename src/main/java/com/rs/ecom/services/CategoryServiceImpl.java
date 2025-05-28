package com.rs.ecom.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.rs.ecom.exceptions.APIException;
import com.rs.ecom.exceptions.ResourceNotFoundException;
import com.rs.ecom.model.Category;
import com.rs.ecom.payload.CategoryDTO;
import com.rs.ecom.payload.CategoryResponse;
import com.rs.ecom.repositories.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ModelMapper modelMapepr;

	@Override
	public CategoryResponse getAllCategories() {
		List<Category> categories = categoryRepository.findAll();
		if (categories.isEmpty()) {
			throw new APIException("No category created till now");
		}

		List<CategoryDTO> categoryDTOS = categories.stream()
				.map(category -> modelMapepr.map(category, CategoryDTO.class)).collect(Collectors.toList());

		CategoryResponse categoryResponse = new CategoryResponse();
		categoryResponse.setContent(categoryDTOS);
		return categoryResponse;
	}

	@Override
	public void createCategory(Category category) {
		Category savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());
		if (savedCategory != null) {
			throw new APIException("Category with the name " + category.getCategoryName() + " already exist");
		}
		categoryRepository.save(category);

	}

	@Override
	public String deleteCategory(Long categoryId) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

		categoryRepository.delete(category);
		return "Category with Id: " + categoryId + " deleted successfully";
	}

	@Override
	public Category updateCategory(Category category, Long categoryId) {
		// fetching particular id as optional
		Optional<Category> savedCategoryOptional = categoryRepository.findById(categoryId);

		// checking id present or not, if not throw an exception
		Category savedCategory = savedCategoryOptional
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

		category.setCategoryId(categoryId);
		savedCategory = categoryRepository.save(category);
		return savedCategory;

	}

}
