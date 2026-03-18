package com.codenza.enotes.service;

import java.util.List;

import com.codenza.enotes.entity.Category;

public interface CategoryService {

	public Boolean saveCategory (Category category);
	
	public List<Category> getAllCategory();
}
