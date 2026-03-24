package com.codenza.enotes.service;

import java.util.List;

import com.codenza.enotes.dto.NoteDTO;

public interface NoteService {

	public Boolean saveNote(NoteDTO noteDTO) throws Exception;
	
	List<NoteDTO> getAllNotes();
	
}
