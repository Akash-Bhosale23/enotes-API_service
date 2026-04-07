package com.codenza.enotes.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.codenza.enotes.dto.CategoryDTO;
import com.codenza.enotes.dto.TodoDTO;
import com.codenza.enotes.dto.TodoDTO.StatusDTO;
import com.codenza.enotes.dto.UserDTO;
import com.codenza.enotes.enums.TodoStatus;
import com.codenza.enotes.exceptions.CategoryValidationException;
import com.codenza.enotes.exceptions.ExistDataException;
import com.codenza.enotes.exceptions.ResourceNotFoundException;
import com.codenza.enotes.repository.RoleRepository;
import com.codenza.enotes.repository.UserRespository;

@Component
public class Validation {
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRespository userRespository;

	public void categoryValidation(CategoryDTO categoryDTO) {

		Map<String, Object> error = new LinkedHashMap<>();

		if (ObjectUtils.isEmpty(categoryDTO)) {
			throw new IllegalArgumentException("Category object/JSON should not be null or empty");
		}
		// validation for name field in category
		else {
			if (ObjectUtils.isEmpty(categoryDTO.getName())) {
				error.put("name", "name field is empty or null");
			} else {
				if (categoryDTO.getName().length() < 3) {
					error.put("name", "Enter at least 3 characters for 'Name'");
				}
				if (categoryDTO.getName().length() > 25) {
					error.put("name", "'Name' length should not be more than 25");
				}
			}

			// validation for description field in category
			if (ObjectUtils.isEmpty(categoryDTO.getDescription())) {
				error.put("Description", "Description should not be empty or null");
			}

			// validation for isActive field in category
			if (ObjectUtils.isEmpty(categoryDTO.getIsActive())) {
				error.put("isActive", "isActive field is empty or null");
			} else {
				if (categoryDTO.getIsActive() != Boolean.TRUE.booleanValue()
						&& categoryDTO.getIsActive() != Boolean.FALSE.booleanValue()) {
					error.put("isActive", "isActive field required only True or False value");
				}
			}
		}

		if (!error.isEmpty()) {
			throw new CategoryValidationException(error);
		}

	}

	public void todoValidation(TodoDTO todo) throws Exception {
		StatusDTO status = todo.getStatus();

		Boolean statusFound = false;

		for (TodoStatus st : TodoStatus.values()) {
			if (st.getId().equals(status.getId())) {
				statusFound = true;
			}
		}

		if (!statusFound) {
			throw new ResourceNotFoundException("Invalid status ! please enter among 1,2 or 3");
		}
	}

	public void userValidation(UserDTO userDTO) {

		if (!StringUtils.hasText(userDTO.getFirstName())) {
			throw new IllegalArgumentException("First name is invalid");
		}

		if (!StringUtils.hasText(userDTO.getLastName())) {
			throw new IllegalArgumentException("Last name is invalid");
		}

		if (!StringUtils.hasText(userDTO.getEmail()) || !userDTO.getEmail().matches(Constants.EMAIL_REGEX)) {
			throw new IllegalArgumentException("Email is invalid");
		}else {
			// check email is already exists or not
			Boolean existEmail= userRespository.existsByEmail(userDTO.getEmail());
			
			if(existEmail) {
				throw new ExistDataException("Email is already exist");
			}
		}

		if (!StringUtils.hasText(userDTO.getMobNo()) || !userDTO.getMobNo().matches(Constants.MOBILE_NO)) {
			throw new IllegalArgumentException("Mobile number is invalid");
		}
		
		if (CollectionUtils.isEmpty(userDTO.getRoles())) {
			throw new IllegalArgumentException("Role is invalid");
		}else {
			List<Integer> roleIds=roleRepository.findAll().stream().map(r->r.getId()).toList();
			
			List<Integer> invalidReqRoleIDs = userDTO.getRoles().stream().map(r->r.getId()).filter(roleId->!roleIds.contains(roleId)).toList();
		
			if(!CollectionUtils.isEmpty(invalidReqRoleIDs)) {
				throw new IllegalArgumentException("Role is invalid "+invalidReqRoleIDs);

			}
		}
	}

}
