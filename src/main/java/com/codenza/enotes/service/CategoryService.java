package com.codenza.enotes.service;

import java.util.List;

import com.codenza.enotes.dto.CategoryDTO;
import com.codenza.enotes.dto.CategoryResponseDTO;


public interface CategoryService {

	public Boolean saveCategory (CategoryDTO categoryDTO);
	
	public List<CategoryDTO> getAllCategory();

	public List<CategoryResponseDTO> getActiveCategory();

	public CategoryDTO getCategoryById(Integer id) throws Exception;

	public Boolean deleteCategoryById(Integer id);
}

