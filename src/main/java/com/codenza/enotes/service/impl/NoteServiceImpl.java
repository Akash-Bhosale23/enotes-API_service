package com.codenza.enotes.service.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.codenza.enotes.dto.NoteDTO;
import com.codenza.enotes.dto.NoteDTO.CategoryDTO;
import com.codenza.enotes.entity.Category;
import com.codenza.enotes.entity.Note;
import com.codenza.enotes.exceptions.ResourceNotFoundException;
import com.codenza.enotes.repository.CategoryRepository;
import com.codenza.enotes.repository.NoteRepository;
import com.codenza.enotes.service.NoteService;

@Service
public class NoteServiceImpl implements NoteService {
	
	@Autowired
	private NoteRepository noteRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public Boolean saveNote(NoteDTO noteDTO) throws Exception {
		
		// Category validation method
		checkCategoryExists(noteDTO.getCategory());
		
		
		Note note = mapper.map(noteDTO, Note.class);
		
		Note savedNote = noteRepository.save(note);
		
		if(!ObjectUtils.isEmpty(savedNote)) {
			return true;
		}
		
		return false;
	}

	private void checkCategoryExists(CategoryDTO category) throws Exception {
		categoryRepository.findById(category.getId()).orElseThrow(()-> new ResourceNotFoundException("Category id is invalid"));
		
	}

	@Override
	public List<NoteDTO> getAllNotes() {

		List<NoteDTO> allNotes = noteRepository.findAll().stream().map(note->mapper.map(note, NoteDTO.class)).toList();
		
		return allNotes;
	}

}
