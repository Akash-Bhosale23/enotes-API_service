package com.codenza.enotes.util;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.codenza.enotes.dto.CategoryDTO;
import com.codenza.enotes.exceptions.CategoryValidationException;

@Component
public class CategoryValidation {

	public void categoryValidation(CategoryDTO categoryDTO) {
		
		Map<String, Object> error=new LinkedHashMap<>();
		
		if(ObjectUtils.isEmpty(categoryDTO)) {
			throw new IllegalArgumentException("Category object/JSON should not be null or empty");
		}
		//validation for name field in category
		else {
			if(ObjectUtils.isEmpty(categoryDTO.getName())) {
				error.put("name" , "name field is empty or null");
			}else {
				if(categoryDTO.getName().length()<3) {
					error.put("name", "Enter at least 3 characters for 'Name'");
				}
				if(categoryDTO.getName().length()>25) {
					error.put("name", "'Name' length should not be more than 25");
				}
			}
			
			//validation for description field in category
			if(ObjectUtils.isEmpty(categoryDTO.getDescription())){
				error.put("Description", "Description should not be empty or null");
			}
			
			//validation for isActive field in category
			if(ObjectUtils.isEmpty(categoryDTO.getName())) {
				error.put("name" , "name field is empty or null");
			}else {
				if(categoryDTO.getIsActive()!=Boolean.TRUE.booleanValue() && categoryDTO.getIsActive()!=Boolean.FALSE.booleanValue()) {
					error.put("isActive", "isActive field required only True or False value");
				}
			}
		}
		
		if(!error.isEmpty()) {
			throw new CategoryValidationException(error);
		}
		
	}
}
