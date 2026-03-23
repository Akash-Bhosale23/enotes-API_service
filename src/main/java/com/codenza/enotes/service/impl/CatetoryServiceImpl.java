package com.codenza.enotes.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.codenza.enotes.dto.CategoryDTO;
import com.codenza.enotes.dto.CategoryResponseDTO;
import com.codenza.enotes.entity.Category;
import com.codenza.enotes.exceptions.ExistDataException;
import com.codenza.enotes.exceptions.ResourceNotFoundException;
import com.codenza.enotes.repository.CategoryRepository;
import com.codenza.enotes.service.CategoryService;
import com.codenza.enotes.util.CategoryValidation;

@Service
public class CatetoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private CategoryValidation validation;
	
	@Override
	public Boolean saveCategory(CategoryDTO categoryDTO) {

		//validation
		
		validation.categoryValidation(categoryDTO);
		
		//Check category is already exist or not
		
		Boolean exist= categoryRepo.existsByName(categoryDTO.getName().trim());
		
		if(exist) {
			throw new ExistDataException("Category is already exist");
		}
		
		Category category= modelMapper.map(categoryDTO, Category.class);
		
		if(ObjectUtils.isEmpty(category.getId())) {
			category.setIsDeleted(false);
//			category.setCreatedBy(1);
//			category.setCreatedOn(new Date());
		}else {
			updateCategory(category);
		}
		
	
		
		Category savedCategory= categoryRepo.save(category);
		
		if(ObjectUtils.isEmpty(savedCategory)) {
			return false;
		}
		
		return true;
	}

	private void updateCategory(Category category) {
		Optional<Category> byId = categoryRepo.findById(category.getId());
		
		if(byId.isPresent()) {
			Category existCategory = byId.get();
			category.setCreatedBy(existCategory.getCreatedBy());
			category.setCreatedOn(existCategory.getCreatedOn());
			category.setIsDeleted(existCategory.getIsDeleted());
//			category.setUpdatedBy(1);
//			category.setUpdatedOn(new Date());
			
		}
		
	}

	@Override
	public List<CategoryDTO> getAllCategory() {
		List<Category> categories= categoryRepo.findByIsDeletedFalse();
		List<CategoryDTO> categoryDTOList= categories.stream().map(cat->modelMapper.map(cat,CategoryDTO.class)).toList();
		
		return categoryDTOList;
	}

	@Override
	public List<CategoryResponseDTO> getActiveCategory() {
	
		List<Category> categories= categoryRepo.findByIsActiveTrueAndIsDeletedFalse();
		
		List<CategoryResponseDTO> categoryList = categories.stream().map(cat->modelMapper.map(cat,CategoryResponseDTO.class)).toList();
		
		return categoryList;
	}

	@Override
	public CategoryDTO getCategoryById(Integer id) throws Exception {
		Category category = categoryRepo.findByIdAndIsDeletedFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found with id : "+id));

		if (!ObjectUtils.isEmpty(category)) {
			if(category.getName()==null) {
				throw new IllegalArgumentException("Name is null..");
			}
			return modelMapper.map(category, CategoryDTO.class);
		}
		return null;
	}

	@Override
	public Boolean deleteCategoryById(Integer id) {
		Optional<Category> findCategoryById = categoryRepo.findById(id);

		if (findCategoryById.isPresent()) {
			Category category = findCategoryById.get();
			category.setIsDeleted(true);
			categoryRepo.save(category);
			return true;
		}
		return false;
	}

}
