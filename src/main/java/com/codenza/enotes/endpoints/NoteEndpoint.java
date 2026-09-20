package com.codenza.enotes.endpoints;

import static com.codenza.enotes.util.Constants.DEFAULT_PAGE_NO;
import static com.codenza.enotes.util.Constants.DEFAULT_PAGE_SIZE;
import static com.codenza.enotes.util.Constants.ROLE_ADMIN;
import static com.codenza.enotes.util.Constants.ROLE_ADMIN_USER;
import static com.codenza.enotes.util.Constants.ROLE_USER;

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

import com.codenza.enotes.dto.NoteRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Note", description = "APIs for creating, managing, searching and organizing user notes")
@RequestMapping("/api/v1/note")
public interface NoteEndpoint {

	@Operation(summary = "Create note", tags = {"Note", "User"} ,description = "Creates a new note for the logged-in user, with an optional file attachment")
	@PostMapping(value = "/create", consumes = "multipart/form-data")
	@PreAuthorize(ROLE_ADMIN_USER)
	public ResponseEntity<?> saveNote(@RequestParam @Parameter(description = "JSON String notes", required = true, content = @Content(schema = @Schema(implementation = NoteRequest.class))) String notes, @RequestParam(required=false) MultipartFile file) throws Exception;
	
	@Operation(summary = "Download note attachment", tags = {"Note", "User"} , description = "Downloads the file attached to a note by its id")
	@GetMapping("/download/{id}")
	@PreAuthorize(ROLE_ADMIN_USER)
	public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception;
	
	@Operation(summary = "Get all notes", description = "Admin: retrieves all notes created by all users")
	@GetMapping("/all-notes")
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> getAllNotes();
	
	@Operation(summary = "Get user notes", tags = {"Note", "User"}, description = "Retrieves the logged-in user's notes with pagination")
	@GetMapping("/user-notes")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getAllNotesByUser(@RequestParam(name="pageNo", defaultValue = DEFAULT_PAGE_NO) Integer pageNo, @RequestParam(name="pageSize", defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize);
	
	@Operation(summary = "Search notes", tags = {"Note", "User"}, description = "Searches the logged-in user's notes by keyword, with pagination")
	@GetMapping("/search")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getNotesSearchByUser(@RequestParam(name="key", defaultValue = "") String key, @RequestParam(name="pageNo", defaultValue = DEFAULT_PAGE_NO) Integer pageNo, @RequestParam(name="pageSize", defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize);
	
	@Operation(summary = "Soft delete note", tags = {"Note", "User"}, description = "Moves a note to the recycle bin without permanently deleting it")
	@DeleteMapping("/soft-delete/{id}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> softDeleteNoteById(@PathVariable Integer id) throws Exception;
	
	@Operation(summary = "Restore note", tags = {"Note", "User"}, description = "Restores a previously soft-deleted note from the recycle bin")
	@PutMapping("/restore/{id}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> restoreNoteById(@PathVariable Integer id) throws Exception;
	
	@Operation(summary = "Get restored notes", tags = {"Note", "User"}, description = "Retrieves notes that were restored from the recycle bin")
	@GetMapping("/restored")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getRestoredNotesByUser() throws Exception;
	
	@Operation(summary = "Permanently delete note", tags = {"Note", "User"}, description = "Permanently deletes a single note by id, bypassing the recycle bin")
	@DeleteMapping("/hard-delete/{id}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> hardDeleteNoteById(@PathVariable Integer id) throws Exception;
	
	@Operation(summary = "Empty recycle bin", tags = {"Note", "User"}, description = "Permanently deletes all soft-deleted notes in the user's recycle bin")
	@DeleteMapping("/delete-recycle-bin")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> deleteAllNotesFromRecycleBin() throws Exception;
	
	@Operation(summary = "Add note to favourites", tags = {"Note", "User"}, description = "Marks a note as favourite for the logged-in user")
	@PostMapping("/fav/{noteId}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> favouriteNote(@PathVariable Integer noteId) throws Exception;
	
	@Operation(summary = "Remove note from favourites", tags = {"Note", "User"}, description = "Removes a note from the logged-in user's favourites")
	@DeleteMapping("/un-fav/{favNoteId}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> unFavouriteNote(@PathVariable Integer favNoteId) throws Exception;
	
	@Operation(summary = "Get favourite notes", tags = {"Note", "User"}, description = "Retrieves all notes marked as favourite by the logged-in user")
	@GetMapping("/fav-notes")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getAllFavNotes() throws Exception;
	
	@Operation(summary = "Copy note", tags = {"Note", "User"}, description = "Creates a duplicate copy of an existing note for the logged-in user")
	@PostMapping("/copy/{noteId}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> copyNote(@PathVariable Integer noteId) throws Exception;
}
