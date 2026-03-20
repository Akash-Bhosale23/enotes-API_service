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
import com.codenza.enotes.repository.CategoryRepository;
import com.codenza.enotes.service.CategoryService;

@Service
public class CatetoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public Boolean saveCategory(CategoryDTO categoryDTO) {
		
//		Category category =new Category();
//		category.setName(categoryDTO.getName());
//		category.setDescription(categoryDTO.getDescription());
//		category.setIsActive(categoryDTO.getIsActive());
		
		Category category= modelMapper.map(categoryDTO, Category.class);
		
		if(ObjectUtils.isEmpty(category.getId())) {
			category.setIsDeleted(false);
			category.setCreatedBy(1);
			category.setCreatedOn(new Date());
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
			category.setUpdatedBy(1);
			category.setUpdatedOn(new Date());
			
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
	public CategoryDTO getCategoryById(Integer id) {
		Optional<Category> findCategoryById = categoryRepo.findByIdAndIsDeletedFalse(id);

		if (findCategoryById.isPresent()) {
			Category category = findCategoryById.get();
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
