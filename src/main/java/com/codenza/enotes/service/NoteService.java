package com.codenza.enotes.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.codenza.enotes.dto.NoteDTO;

public interface NoteService {

	public Boolean saveNote(String notes, MultipartFile file) throws Exception;
	
	List<NoteDTO> getAllNotes();
	
}
