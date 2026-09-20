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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import static com.codenza.enotes.util.Constants.ROLE_ADMIN;
import static com.codenza.enotes.util.Constants.ROLE_ADMIN_USER;

@Tag(name="Category", description = "All category operation APIs")
@RequestMapping("/api/v1/category")
public interface CategoryEndpoint {

	@Operation(summary = "Create a new category", description = "Create a new category requires 'Admin' role", responses = {
	        @ApiResponse (responseCode = "201", description = "Category created successfully"),
	        @ApiResponse(responseCode = "400", description = "Invalid category data"),
	        @ApiResponse(responseCode = "403", description = "Access denied - ADMIN role required")
	    })
	@PostMapping("/save")
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> saveCategory (@RequestBody CategoryDTO categoryDto);
	
	@Operation(summary = "Get all category", description = "View all categories requires 'Admin' role")
	@GetMapping("/all-category")
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> getAllCategory();
	
	@Operation(summary = "Get active category", description = "Get active category")
	@GetMapping("/active")
	@PreAuthorize(ROLE_ADMIN_USER)
	public ResponseEntity<?> getActiveCategory();
	
	@Operation(summary = "Get category", description = "Get category by id requires 'Admin' role ")
	@GetMapping("/{id}")
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> getCategoryById(@PathVariable Integer id) throws Exception;
	
	@Operation(summary = "Delete category", description = "Delete category requires 'Admin' role")
	@DeleteMapping("/{id}")
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> deleteCategoryById(@PathVariable Integer id);
	
}
