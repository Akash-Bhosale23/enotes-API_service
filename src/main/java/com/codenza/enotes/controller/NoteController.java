package com.codenza.enotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codenza.enotes.dto.NoteDTO;
import com.codenza.enotes.dto.NoteResponse;
import com.codenza.enotes.entity.FileDetails;
import com.codenza.enotes.entity.Note;
import com.codenza.enotes.exceptions.ResourceNotFoundException;
import com.codenza.enotes.service.NoteService;
import com.codenza.enotes.util.CommonUtil;

@RestController
@RequestMapping("/api/v1/note")
public class NoteController {

	@Autowired
	private NoteService noteService;
	
	@PostMapping("/create")
	public ResponseEntity<?> saveNote(@RequestParam String notes, @RequestParam(required=false) MultipartFile file) throws Exception{
		
		Boolean savedNote = noteService.saveNote(notes, file);
		
		if(savedNote) {
			return CommonUtil.createBuildResponseMessage("Saved success", HttpStatus.CREATED);
		}else {
			return CommonUtil.createErrorResponseMessage("Not saved", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/download/{id}")
	public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception{
		
		FileDetails fileDetails= noteService.getFileDetails(id);
		
		byte[] fileData= noteService.downloadFile(fileDetails);
		
		HttpHeaders headers=new HttpHeaders();
		
		String contentType = CommonUtil.getContentType(fileDetails.getOriginalFileName());
		
		headers.setContentType(MediaType.parseMediaType(contentType));
		headers.setContentDispositionFormData("Attachement", fileDetails.getOriginalFileName());
		
		return ResponseEntity.ok().headers(headers).body(fileData);
		
		
	}
	
	@GetMapping("/")
	public ResponseEntity<?> getAllNotes(){
		List<NoteDTO> notes = noteService.getAllNotes();
		
		if(CollectionUtils.isEmpty(notes)) {
		return	ResponseEntity.noContent().build();			
		}
		return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
	}
	
	@GetMapping("/user-notes")
	public ResponseEntity<?> getAllNotesByUser(@RequestParam(name="pageNo", defaultValue = "0") Integer pageNo, @RequestParam(name="pageSize", defaultValue = "10") Integer pageSize)
	{
		
		Integer userId=1;
		
		NoteResponse notes = noteService.getAllNotesByUser(userId, pageNo, pageSize);
		
		if(ObjectUtils.isEmpty(notes)) {
		return	ResponseEntity.noContent().build();			
		}
		return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteNoteById(@PathVariable Integer id) throws Exception{
		
		noteService.softDelete(id);
		
		return CommonUtil.createBuildResponseMessage("Deleted", HttpStatus.OK);
	}

	@PutMapping("/restore/{id}")
	public ResponseEntity<?> restoreNoteById(@PathVariable Integer id) throws Exception{
		
		noteService.restoreNote(id);
		
		return CommonUtil.createBuildResponseMessage("Restored", HttpStatus.OK);
	}
	
	@GetMapping("/restored")
	public ResponseEntity<?> getRestoredNotesByUser() throws Exception{
		
		Integer userId=1;
		List<NoteDTO> notes= noteService.getRestoredNotesByUser(userId);
		
		if(CollectionUtils.isEmpty(notes)) {
			return CommonUtil.createBuildResponseMessage("Notes not available in Recycle bin", HttpStatus.OK);
		}
		
		return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
	}
}
