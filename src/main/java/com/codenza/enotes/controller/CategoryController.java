package com.codenza.enotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codenza.enotes.dto.CategoryDTO;
import com.codenza.enotes.dto.CategoryResponseDTO;
import com.codenza.enotes.entity.Category;
import com.codenza.enotes.service.CategoryService;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;

	@PostMapping("/save-category")
	public ResponseEntity<?> saveCategory (@RequestBody CategoryDTO catetoryDto){
		
		Boolean saveCategory= categoryService.saveCategory(catetoryDto);
		
		if(saveCategory) {
			return new ResponseEntity<> ("Saved success", HttpStatus.CREATED);
		}else {
			return new ResponseEntity<> ("Not Saved", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping("/categories")
	public ResponseEntity<?> getAllCategory(){
		List<CategoryDTO> allCategory = categoryService.getAllCategory();
		
		if(CollectionUtils.isEmpty(allCategory)) {
			return ResponseEntity.noContent().build();
		}
		else {
			return new ResponseEntity<>(allCategory,HttpStatus.OK);
		}
			
	}
	
	@GetMapping("/active-categories")
	public ResponseEntity<?> getActiveCategory(){
		List<CategoryResponseDTO> allCategory = categoryService.getActiveCategory();
		
		if(CollectionUtils.isEmpty(allCategory)) {
			return ResponseEntity.noContent().build();
		}
		else {
			return new ResponseEntity<>(allCategory,HttpStatus.OK);
		}
			
	}
}
