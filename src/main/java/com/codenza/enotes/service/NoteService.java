package com.codenza.enotes.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.codenza.enotes.dto.NoteDTO;
import com.codenza.enotes.dto.NoteResponse;
import com.codenza.enotes.entity.FileDetails;
import com.codenza.enotes.exceptions.ResourceNotFoundException;

public interface NoteService {

	public Boolean saveNote(String notes, MultipartFile file) throws Exception;
	
	List<NoteDTO> getAllNotes();

	public byte[] downloadFile(FileDetails fileDetails) throws Exception;

	public FileDetails getFileDetails(Integer id) throws Exception;

	public NoteResponse getAllNotesByUser(Integer userId, Integer pageNo, Integer pageSize);

	public void softDelete(Integer id) throws Exception;

	public void restoreNote(Integer id) throws Exception;

	public List<NoteDTO> getNotesFromRecycleBin(Integer userId);

	public void hardDelete(Integer id) throws Exception;

	public void deleteNotesFromRecycleBin(Integer userId);
	
}
