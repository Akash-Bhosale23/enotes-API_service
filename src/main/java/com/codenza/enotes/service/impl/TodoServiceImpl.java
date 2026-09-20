package com.codenza.enotes.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.codenza.enotes.dto.TodoDTO;
import com.codenza.enotes.dto.TodoDTO.StatusDTO;
import com.codenza.enotes.entity.Todo;
import com.codenza.enotes.enums.TodoStatus;
import com.codenza.enotes.exceptions.ResourceNotFoundException;
import com.codenza.enotes.repository.TodoRepository;
import com.codenza.enotes.service.TodoService;
import com.codenza.enotes.util.CommonUtil;
import com.codenza.enotes.util.Validation;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TodoServiceImpl implements TodoService {

	private final TodoRepository todoRepository;
	
	private final ModelMapper mapper;
	
	private final Validation validation;
	
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
	public List<TodoDTO> getAllTodosByUser() {
		
		Integer userId= CommonUtil.getLoggedInUser().getId();
		
		List<Todo> todos= todoRepository.findByCreatedBy(userId);
		
		List<TodoDTO> allTodos =todos.stream().map(allT->mapper.map(allT, TodoDTO.class)).toList();
		
		return allTodos;
	}

}
