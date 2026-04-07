package com.codenza.enotes.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.codenza.enotes.dto.UserDTO;
import com.codenza.enotes.entity.Role;
import com.codenza.enotes.entity.User;
import com.codenza.enotes.repository.RoleRepository;
import com.codenza.enotes.repository.UserRespository;
import com.codenza.enotes.service.UserService;
import com.codenza.enotes.util.Validation;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRespository userRespository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private Validation validation;

	@Autowired
	private ModelMapper mapper;

	@Override
	public Boolean register(UserDTO userDTO) {

		validation.userValidation(userDTO);

		User user = mapper.map(userDTO, User.class);
		
		serRole(userDTO, user);

		User savedUser = userRespository.save(user);

		if (!ObjectUtils.isEmpty(savedUser)) {
			return true;
		} else {

			return false;
		}

	}

	private void serRole(UserDTO userDTO, User user) {

		List<Integer> reqRoleId = userDTO.getRoles().stream().map(r->r.getId()).toList();
		
		List<Role> roles = roleRepository.findAllById(reqRoleId);
		user.setRoles(roles);
	}

}
