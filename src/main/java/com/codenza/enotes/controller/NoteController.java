package com.codenza.enotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codenza.enotes.dto.NoteDTO;
import com.codenza.enotes.entity.Note;
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
	
	@GetMapping("/")
	public ResponseEntity<?> getAllNotes(){
		List<NoteDTO> notes = noteService.getAllNotes();
		
		if(CollectionUtils.isEmpty(notes)) {
		return	ResponseEntity.noContent().build();			
		}
		return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
	}
}
