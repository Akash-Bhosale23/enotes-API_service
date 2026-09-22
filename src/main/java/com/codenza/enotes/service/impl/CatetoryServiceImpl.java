package com.codenza.enotes.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.codenza.enotes.dto.CategoryDTO;
import com.codenza.enotes.dto.CategoryResponseDTO;
import com.codenza.enotes.entity.Category;
import com.codenza.enotes.exceptions.ExistDataException;
import com.codenza.enotes.exceptions.ResourceNotFoundException;
import com.codenza.enotes.repository.CategoryRepository;
import com.codenza.enotes.service.CacheManagerService;
import com.codenza.enotes.service.CategoryService;
import com.codenza.enotes.util.Validation;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CatetoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepo;
	
	private final ModelMapper modelMapper;
	
	private final Validation validation;
	
	private final CacheManagerService cacheService;
	
	@Override
	public Boolean saveCategory(CategoryDTO categoryDTO) {
		
		validation.categoryValidation(categoryDTO);
				
		Boolean exist= categoryRepo.existsByName(categoryDTO.getName().trim());
		
		if(exist) {
			throw new ExistDataException("Category is already exist");
		}
		
		Category category= modelMapper.map(categoryDTO, Category.class);
		
		if(ObjectUtils.isEmpty(category.getId())) {
			category.setIsDeleted(false);
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
		}
		
	}

	@Override
	@Cacheable("allCategories")
	public List<CategoryDTO> getAllCategory() {
		List<Category> categories= categoryRepo.findByIsDeletedFalse();
		List<CategoryDTO> categoryDTOList= categories.stream().map(cat->modelMapper.map(cat,CategoryDTO.class)).toList();
		
		return categoryDTOList;
	}

	@Override
	@Cacheable("activeCategory")
	public List<CategoryResponseDTO> getActiveCategory() {
	
		List<Category> categories= categoryRepo.findByIsActiveTrueAndIsDeletedFalse();
		
		List<CategoryResponseDTO> categoryList = categories.stream().map(cat->modelMapper.map(cat,CategoryResponseDTO.class)).toList();
		
		return categoryList;
	}

	@Override
	@Cacheable(value = "getCategoryById", key="#id")
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
	@CacheEvict(value = "getCategoryById", key="#id")
	public Boolean deleteCategoryById(Integer id) {
		Optional<Category> findCategoryById = categoryRepo.findById(id);

		if (findCategoryById.isPresent()) {
			Category category = findCategoryById.get();
			category.setIsDeleted(true);
			categoryRepo.save(category);
			
			cacheService.removeCacheByName(Arrays.asList("allCategories","activeCategory"));
			
			return true;
		}
		return false;
	}

}
