package com.codenza.enotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codenza.enotes.dto.CategoryDTO;
import com.codenza.enotes.dto.CategoryResponseDTO;
import com.codenza.enotes.entity.Category;
import com.codenza.enotes.exceptions.ResourceNotFoundException;
import com.codenza.enotes.service.CategoryService;
import com.codenza.enotes.util.CommonUtil;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;

	@PostMapping("/save")
	public ResponseEntity<?> saveCategory (@RequestBody CategoryDTO catetoryDto){
		
		Boolean saveCategory= categoryService.saveCategory(catetoryDto);
		
		if(saveCategory) {
//			return new ResponseEntity<> ("Saved success", HttpStatus.CREATED);
			return CommonUtil.createBuildResponseMessage("Saved Success", HttpStatus.CREATED);
		}else {
//			return new ResponseEntity<> ("Not Saved", HttpStatus.INTERNAL_SERVER_ERROR);
			return CommonUtil.createErrorResponseMessage("Not saved", HttpStatus.INTERNAL_SERVER_ERROR);

		}
		
	}
	
	@GetMapping("/")
	public ResponseEntity<?> getAllCategory(){
		List<CategoryDTO> allCategory = categoryService.getAllCategory();
		
		if(CollectionUtils.isEmpty(allCategory)) {
			return ResponseEntity.noContent().build();
		}
		else {
//			return new ResponseEntity<>(allCategory,HttpStatus.OK);
			return CommonUtil.createBuildResponse(allCategory, HttpStatus.OK);
		}
			
	}
	
	@GetMapping("/active")
	public ResponseEntity<?> getActiveCategory(){
		List<CategoryResponseDTO> allCategory = categoryService.getActiveCategory();
		
		if(CollectionUtils.isEmpty(allCategory)) {
			return ResponseEntity.noContent().build();
		}
		else {
//			return new ResponseEntity<>(allCategory,HttpStatus.OK);
			return CommonUtil.createBuildResponse(allCategory, HttpStatus.OK);
		}
			
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getCategoryById(@PathVariable Integer id) throws Exception{
			CategoryDTO categoryDto= categoryService.getCategoryById(id);
			
			if(ObjectUtils.isEmpty(categoryDto)) {
//				return new ResponseEntity<>("Category not found with id :"+id, HttpStatus.NOT_FOUND);
				return CommonUtil.createErrorResponseMessage("Category not found with id :"+id, HttpStatus.NOT_FOUND);
			}
//			return new ResponseEntity<>(categoryDto, HttpStatus.OK);
			return CommonUtil.createBuildResponse(categoryDto, HttpStatus.OK);

	
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCategoryById(@PathVariable Integer id){
		
		Boolean deleted = categoryService.deleteCategoryById(id);
		
		if(deleted) {
//			return new ResponseEntity<>("Category deleted Successfully..", HttpStatus.OK);
			return CommonUtil.createBuildResponse("Category deleted Successfully", HttpStatus.OK);
		}
//		return new ResponseEntity<>("Category not deleted..", HttpStatus.INTERNAL_SERVER_ERROR);
		return CommonUtil.createErrorResponseMessage("Category not deleted", HttpStatus.INTERNAL_SERVER_ERROR);
		
		
	}
}
