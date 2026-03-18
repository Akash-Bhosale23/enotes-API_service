package com.codenza.enotes.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.codenza.enotes.entity.Category;
import com.codenza.enotes.repository.CategoryRepository;
import com.codenza.enotes.service.CategoryService;

@Service
public class CatetoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepo;
	
	@Override
	public Boolean saveCategory(Category category) {
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
	public List<Category> getAllCategory() {
		List<Category> categories= categoryRepo.findAll();
		return categories;
	}

}
