package com.codenza.enotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codenza.enotes.dto.FavouriteNoteDTO;
import com.codenza.enotes.dto.NoteDTO;
import com.codenza.enotes.dto.NoteResponse;
import com.codenza.enotes.entity.FileDetails;
import com.codenza.enotes.repository.FavouriteNoteRepository;
import com.codenza.enotes.service.NoteService;
import com.codenza.enotes.util.CommonUtil;

@RestController
@RequestMapping("/api/v1/note")
public class NoteController {

	@Autowired
	private NoteService noteService;
	
	@PostMapping("/create")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> saveNote(@RequestParam String notes, @RequestParam(required=false) MultipartFile file) throws Exception{
		
		Boolean savedNote = noteService.saveNote(notes, file);
		
		if(savedNote) {
			return CommonUtil.createBuildResponseMessage("Saved success", HttpStatus.CREATED);
		}else {
			return CommonUtil.createErrorResponseMessage("Not saved", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/download/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
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
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getAllNotes(){
		List<NoteDTO> notes = noteService.getAllNotes();
		
		if(CollectionUtils.isEmpty(notes)) {
		return	ResponseEntity.noContent().build();			
		}
		return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
	}
	
	@GetMapping("/user-notes")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getAllNotesByUser(@RequestParam(name="pageNo", defaultValue = "0") Integer pageNo, @RequestParam(name="pageSize", defaultValue = "10") Integer pageSize)
	{
		
		NoteResponse notes = noteService.getAllNotesByUser(pageNo, pageSize);
		
		if(ObjectUtils.isEmpty(notes)) {
		return	ResponseEntity.noContent().build();			
		}
		return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
	}
	
	@GetMapping("/search")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getNotesSearchByUser(@RequestParam(name="key", defaultValue = "") String key, @RequestParam(name="pageNo", defaultValue = "0") Integer pageNo, @RequestParam(name="pageSize", defaultValue = "10") Integer pageSize)
	{
		
		NoteResponse notes = noteService.getNotesSearchByUser(pageNo, pageSize,key);
		
		if(ObjectUtils.isEmpty(notes)) {
		return	ResponseEntity.noContent().build();			
		}
		return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
	}
	
	
	@DeleteMapping("/soft-delete/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> softDeleteNoteById(@PathVariable Integer id) throws Exception{
		
		noteService.softDelete(id);
		
		return CommonUtil.createBuildResponseMessage("Deleted", HttpStatus.OK);
	}

	@PutMapping("/restore/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> restoreNoteById(@PathVariable Integer id) throws Exception{
		
		noteService.restoreNote(id);
		
		return CommonUtil.createBuildResponseMessage("Restored", HttpStatus.OK);
	}
	
	@GetMapping("/restored")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getRestoredNotesByUser() throws Exception{
		
		List<NoteDTO> notes= noteService.getNotesFromRecycleBin();
		
		if(CollectionUtils.isEmpty(notes)) {
			return CommonUtil.createBuildResponseMessage("Notes not available in Recycle bin", HttpStatus.OK);
		}
		
		return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
	}
	
	@DeleteMapping("/hard-delete/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> hardDeleteNoteById(@PathVariable Integer id) throws Exception{
		
		noteService.hardDelete(id);
		
		return CommonUtil.createBuildResponseMessage("Deleted", HttpStatus.OK);
	}
	
	@DeleteMapping("/delete-recycle-bin")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> deleteAllNotesFromRecycleBin() throws Exception{
		
		List<NoteDTO> notes= noteService.getNotesFromRecycleBin();
		
		noteService.deleteNotesFromRecycleBin();
		
		return CommonUtil.createBuildResponseMessage("Deleted", HttpStatus.OK);
	}
	
	@PostMapping("/fav/{noteId}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> favouriteNote(@PathVariable Integer noteId) throws Exception{
		
        noteService.favouriteNotes(noteId);
		
		return CommonUtil.createBuildResponseMessage("Noted added in favourites", HttpStatus.CREATED);
	}
	
	@DeleteMapping("/un-fav/{favNoteId}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> unFavouriteNote(@PathVariable Integer favNoteId) throws Exception{
		
		noteService.unFavouriteNotes(favNoteId);
						
		return CommonUtil.createBuildResponseMessage("Removed note from favourites", HttpStatus.OK);
	}
	
	@GetMapping("/fav-notes")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getAllFavNotes() throws Exception{
		
		List<FavouriteNoteDTO> favouriteNotes = noteService.getFavouriteNotes();
		
		if(CollectionUtils.isEmpty(favouriteNotes)) {
			return ResponseEntity.noContent().build();
		}
		
		return CommonUtil.createBuildResponse(favouriteNotes, HttpStatus.OK);
	}
	
	@GetMapping("/copy/{noteId}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> copyNote(@PathVariable Integer noteId) throws Exception{
		
        Boolean copyNote = noteService.copyNote(noteId);
        
        if (copyNote) {
    		return CommonUtil.createBuildResponseMessage("Copied success", HttpStatus.CREATED);

        }
        
		return CommonUtil.createErrorResponseMessage("Not copied ! try again", HttpStatus.INTERNAL_SERVER_ERROR);

		
	}
	
}
