package com.codenza.enotes.endpoints;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codenza.enotes.dto.CategoryDTO;

import static com.codenza.enotes.util.Constants.ROLE_ADMIN;
import static com.codenza.enotes.util.Constants.ROLE_ADMIN_USER;

@RequestMapping("/api/v1/category")
public interface CategoryEndpoint {

	@PostMapping("/save")
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> saveCategory (@RequestBody CategoryDTO catetoryDto);
	
	@GetMapping("/")
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> getAllCategory();
	
	@GetMapping("/active")
	@PreAuthorize(ROLE_ADMIN_USER)
	public ResponseEntity<?> getActiveCategory();
	
	@GetMapping("/{id}")
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> getCategoryById(@PathVariable Integer id) throws Exception;
	
	@DeleteMapping("/{id}")
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> deleteCategoryById(@PathVariable Integer id);
	
}
