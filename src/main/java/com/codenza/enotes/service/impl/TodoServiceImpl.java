package com.codenza.enotes.service.impl;

import java.util.List;
import java.util.stream.Stream;

import org.apache.logging.log4j.status.StatusData;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.codenza.enotes.dto.TodoDTO;
import com.codenza.enotes.dto.TodoDTO.StatusDTO;
import com.codenza.enotes.entity.Todo;
import com.codenza.enotes.enums.TodoStatus;
import com.codenza.enotes.exceptions.ResourceNotFoundException;
import com.codenza.enotes.repository.TodoRepository;
import com.codenza.enotes.service.TodoService;
import com.codenza.enotes.util.Validation;

@Service
public class TodoServiceImpl implements TodoService {

	@Autowired
	private TodoRepository todoRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private Validation validation;
	
	@Override
	public Boolean saveTodo(TodoDTO todoDTO) throws Exception {
		
		validation.todoValidation(todoDTO);
		
		Todo todo = mapper.map(todoDTO, Todo.class);
		
		todo.setStatusId(todoDTO.getStatus().getId());
		
		Todo savedTodo = todoRepository.save(todo);
		
		if(!ObjectUtils.isEmpty(savedTodo)) {
			return true;
		}
		
		return false;
	}

	@Override
	public TodoDTO getTodoById(Integer id) throws Exception {
		
		Todo todo = todoRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Id is invalid ! Please enter valid id"));
		
		TodoDTO todoDto = mapper.map(todo, TodoDTO.class);
		
		setStatus(todoDto, todo);
		
		return todoDto;
	}

	private void setStatus(TodoDTO todoDto, Todo todo) {

		for(TodoStatus st: TodoStatus.values()) {
			if(st.getId().equals(todo.getStatusId())) {
				StatusDTO statusDTO =StatusDTO.builder().id(st.getId()).name(st.getName()).build();
				todoDto.setStatus(statusDTO);
			}
		}
		
	}

	@Override
	public List<TodoDTO> getAllTodos() {
		
		Integer userId=1;
		
		List<Todo> todos= todoRepository.findByCreatedBy(userId);
		
		List<TodoDTO> allTodos =todos.stream().map(allT->mapper.map(allT, TodoDTO.class)).toList();
		
		return allTodos;
	}

}
