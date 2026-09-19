package com.codenza.enotes.endpoints;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import static com.codenza.enotes.util.Constants.ROLE_ADMIN;
import static com.codenza.enotes.util.Constants.ROLE_ADMIN_USER;
import static com.codenza.enotes.util.Constants.ROLE_USER;
import static com.codenza.enotes.util.Constants.DEFAULT_PAGE_NO;
import static com.codenza.enotes.util.Constants.DEFAULT_PAGE_SIZE;

@RequestMapping("/api/v1/note")
public interface NoteEndpoint {

	
	@PostMapping("/create")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> saveNote(@RequestParam String notes, @RequestParam(required=false) MultipartFile file) throws Exception;
	
	@GetMapping("/download/{id}")
	@PreAuthorize(ROLE_ADMIN_USER)
	public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception;
	
	@GetMapping("/")
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> getAllNotes();
	
	@GetMapping("/user-notes")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getAllNotesByUser(@RequestParam(name="pageNo", defaultValue = DEFAULT_PAGE_NO) Integer pageNo, @RequestParam(name="pageSize", defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize);
	
	@GetMapping("/search")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getNotesSearchByUser(@RequestParam(name="key", defaultValue = "") String key, @RequestParam(name="pageNo", defaultValue = DEFAULT_PAGE_NO) Integer pageNo, @RequestParam(name="pageSize", defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize);
	
	@DeleteMapping("/soft-delete/{id}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> softDeleteNoteById(@PathVariable Integer id) throws Exception;
	
	@PutMapping("/restore/{id}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> restoreNoteById(@PathVariable Integer id) throws Exception;
	
	@GetMapping("/restored")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getRestoredNotesByUser() throws Exception;
	
	@DeleteMapping("/hard-delete/{id}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> hardDeleteNoteById(@PathVariable Integer id) throws Exception;
	
	@DeleteMapping("/delete-recycle-bin")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> deleteAllNotesFromRecycleBin() throws Exception;
	
	@PostMapping("/fav/{noteId}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> favouriteNote(@PathVariable Integer noteId) throws Exception;
	
	@DeleteMapping("/un-fav/{favNoteId}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> unFavouriteNote(@PathVariable Integer favNoteId) throws Exception;
	
	@GetMapping("/fav-notes")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getAllFavNotes() throws Exception;
	
	@GetMapping("/copy/{noteId}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> copyNote(@PathVariable Integer noteId) throws Exception;
}
