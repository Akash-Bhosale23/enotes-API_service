package com.codenza.enotes.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RestController;

import com.codenza.enotes.dto.CategoryDTO;
import com.codenza.enotes.dto.CategoryResponseDTO;
import com.codenza.enotes.endpoints.CategoryEndpoint;
import com.codenza.enotes.service.CategoryService;
import com.codenza.enotes.util.CommonUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class CategoryController implements CategoryEndpoint {
	
	private final CategoryService categoryService;

	@Override
	public ResponseEntity<?> saveCategory (CategoryDTO catetoryDto){
		
		Boolean saveCategory= categoryService.saveCategory(catetoryDto);
		
		if(saveCategory) {
			return CommonUtil.createBuildResponseMessage("Saved Success", HttpStatus.CREATED);
		}else {
			return CommonUtil.createErrorResponseMessage("Not saved", HttpStatus.INTERNAL_SERVER_ERROR);

		}
		
	}
	
	@Override
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
	
	@Override
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
	
	@Override
	public ResponseEntity<?> getCategoryById(Integer id) throws Exception{
			CategoryDTO categoryDto= categoryService.getCategoryById(id);
			
			if(ObjectUtils.isEmpty(categoryDto)) {
//				return new ResponseEntity<>("Category not found with id :"+id, HttpStatus.NOT_FOUND);
				return CommonUtil.createErrorResponseMessage("Category not found with id :"+id, HttpStatus.NOT_FOUND);
			}
//			return new ResponseEntity<>(categoryDto, HttpStatus.OK);
			return CommonUtil.createBuildResponse(categoryDto, HttpStatus.OK);

	
	}
	
	@Override
	public ResponseEntity<?> deleteCategoryById(Integer id){
		
		Boolean deleted = categoryService.deleteCategoryById(id);
		
		if(deleted) {
			return CommonUtil.createBuildResponse("Category deleted Successfully", HttpStatus.OK);
		}
		return CommonUtil.createErrorResponseMessage("Category not deleted", HttpStatus.INTERNAL_SERVER_ERROR);
		
		
	}
}
