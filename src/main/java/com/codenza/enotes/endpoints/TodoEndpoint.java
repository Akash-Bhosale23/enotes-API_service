package com.codenza.enotes.endpoints;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codenza.enotes.dto.TodoDTO;

import static com.codenza.enotes.util.Constants.ROLE_USER;

@RequestMapping("/api/v1/todos")
public interface TodoEndpoint {

	
	@PostMapping("/create")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> createTodo(@RequestBody TodoDTO todoDTO) throws Exception;
	
	@GetMapping("/{id}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getTodoByUserId(@PathVariable Integer id) throws Exception;
	
	@GetMapping()
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getAllTodos();
	
	
}
