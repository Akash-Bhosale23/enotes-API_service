package com.codenza.enotes.service;

import java.util.List;

import com.codenza.enotes.dto.TodoDTO;

public interface TodoService {

	Boolean saveTodo(TodoDTO todoDTO) throws Exception;
	
	TodoDTO getTodoById(Integer id) throws Exception;
	
	List<TodoDTO> getAllTodos();
	
}
