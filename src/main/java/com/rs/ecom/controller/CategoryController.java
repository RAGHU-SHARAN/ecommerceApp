package com.rs.ecom.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rs.ecom.model.Category;
import com.rs.ecom.payload.CategoryDTO;
import com.rs.ecom.payload.CategoryResponse;
import com.rs.ecom.services.CategoryService;

import jakarta.validation.Valid;

@RestController
public class CategoryController {

	private CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@GetMapping("/api/public/categories")
	public ResponseEntity<CategoryResponse> getAllCategories() {
		CategoryResponse categoryResponse = categoryService.getAllCategories();

		return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
	}

	@PostMapping("/api/public/categories")
	public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
		CategoryDTO savedCategoryDTO =  categoryService.createCategory(categoryDTO);
		return new ResponseEntity<>(savedCategoryDTO,HttpStatus.CREATED);
	}

	@DeleteMapping("/api/admin/categories/{categoryId}")
	public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
		String status = categoryService.deleteCategory(categoryId);
		return new ResponseEntity<String>(status, HttpStatus.OK);
	}

//	public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
//		try {
//			String status = categoryService.deleteCategory(categoryId);
//			return new ResponseEntity<String>(status, HttpStatus.OK);
//		} catch (ResponseStatusException e) {
//			return new ResponseEntity<>(e.getReason(), e.getStatusCode());
//		}
//	}

	@PutMapping("/api/public/categories/{categoryId}")
	public ResponseEntity<String> updateCategory(@Valid @RequestBody Category category, @PathVariable Long categoryId) {
		Category saveCategory = categoryService.updateCategory(category, categoryId);
		return new ResponseEntity<>("Category with id: " + categoryId, HttpStatus.OK);
	}

}
