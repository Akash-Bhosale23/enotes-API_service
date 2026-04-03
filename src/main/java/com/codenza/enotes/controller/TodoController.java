package com.codenza.enotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codenza.enotes.dto.TodoDTO;
import com.codenza.enotes.service.TodoService;
import com.codenza.enotes.util.CommonUtil;

@RestController
@RequestMapping("/api/v1/todos")
public class TodoController {

	@Autowired
	private TodoService todoService;
	
	@PostMapping("/create")
	public ResponseEntity<?> createTodo(@RequestBody TodoDTO todoDTO) throws Exception{
		
		Boolean savedTodo = todoService.saveTodo(todoDTO);
		
		if(savedTodo) {
			return CommonUtil.createBuildResponseMessage("Saved Success", HttpStatus.CREATED);
		}else {
			return CommonUtil.createErrorResponseMessage("Failed to create", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getTodoById(@PathVariable Integer id) throws Exception{
		
		TodoDTO todo = todoService.getTodoById(id);
		
		if(!ObjectUtils.isEmpty(todo)){
			return CommonUtil.createBuildResponse(todo, HttpStatus.OK); 
		}
		else {
			return CommonUtil.createErrorResponseMessage("Todo not found with id :"+id, HttpStatus.NOT_FOUND);

		}
	}
	
	@GetMapping()
	public ResponseEntity<?> getAllTodos() {

		List<TodoDTO> allTodos = todoService.getAllTodos();

		if (CollectionUtils.isEmpty(allTodos)) {
			return ResponseEntity.noContent().build();

		} else {
			return CommonUtil.createBuildResponse(allTodos, HttpStatus.OK);

		}
	}
	
}
