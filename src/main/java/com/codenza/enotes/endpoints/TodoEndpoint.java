package com.codenza.enotes.endpoints;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codenza.enotes.dto.TodoDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import static com.codenza.enotes.util.Constants.ROLE_USER;

@Tag(name = "Todo", description = "APIs for creating and retrieving user todos")
@RequestMapping("/api/v1/todos")
public interface TodoEndpoint {

	@Operation(summary = "Create todo", description = "Creates a new todo for the logged-in user")
	@PostMapping("/create")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> createTodo(@RequestBody TodoDTO todoDTO) throws Exception;
	
	@Operation(summary = "Get todo by id", description = "Retrieves a single todo belonging to the logged-in user by its id")
	@GetMapping("/{id}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getTodoById(@PathVariable Integer id) throws Exception;
	
	@Operation(summary = "Get all todos", description = "Retrieves all todos belonging to the logged-in user")
	@GetMapping()
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getAllTodos();
	
	
}
