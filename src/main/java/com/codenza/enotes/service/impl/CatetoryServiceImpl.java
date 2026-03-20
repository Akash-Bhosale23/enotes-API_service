package com.codenza.enotes.service.impl;

import java.util.Date;
import java.util.List;

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
		 
		category.setIsDeleted(false);
		category.setCreatedBy(1);
		category.setCreatedOn(new Date());
		
		Category savedCategory= categoryRepo.save(category);
		
		if(ObjectUtils.isEmpty(savedCategory)) {
			return false;
		}
		
		return true;
	}

	@Override
	public List<CategoryDTO> getAllCategory() {
		List<Category> categories= categoryRepo.findAll();
		List<CategoryDTO> categoryDTOList= categories.stream().map(cat->modelMapper.map(cat,CategoryDTO.class)).toList();
		
		return categoryDTOList;
	}

	@Override
	public List<CategoryResponseDTO> getActiveCategory() {
	
		List<Category> categories= categoryRepo.findByIsActiveTrue();
		
		List<CategoryResponseDTO> categoryList = categories.stream().map(cat->modelMapper.map(cat,CategoryResponseDTO.class)).toList();
		
		return categoryList;
	}

}
