package com.codenza.enotes.controller;

import java.util.List;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codenza.enotes.dto.FavouriteNoteDTO;
import com.codenza.enotes.dto.NoteDTO;
import com.codenza.enotes.dto.NoteResponse;
import com.codenza.enotes.endpoints.NoteEndpoint;
import com.codenza.enotes.entity.FileDetails;
import com.codenza.enotes.service.NoteService;
import com.codenza.enotes.util.CommonUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class NoteController implements NoteEndpoint {

	private final NoteService noteService;
	
	@Override
	public ResponseEntity<?> saveNote(String notes, MultipartFile file) throws Exception{
		
		Boolean savedNote = noteService.saveNote(notes, file);
		
		if(savedNote) {
			return CommonUtil.createBuildResponseMessage("Saved success", HttpStatus.CREATED);
		}else {
			return CommonUtil.createErrorResponseMessage("Not saved", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Override
	public ResponseEntity<?> downloadFile(Integer id) throws Exception{
		
		FileDetails fileDetails= noteService.getFileDetails(id);
		
		byte[] fileData= noteService.downloadFile(fileDetails);
		
		HttpHeaders headers=new HttpHeaders();
		
		String contentType = CommonUtil.getContentType(fileDetails.getOriginalFileName());
		
		headers.setContentType(MediaType.parseMediaType(contentType));
		headers.setContentDisposition(
			    ContentDisposition.attachment()
			        .filename(fileDetails.getOriginalFileName())
			        .build()
			);
		
		return ResponseEntity.ok().headers(headers).body(fileData);
		
		
	}
	
	@Override
	public ResponseEntity<?> getAllNotes(){
		List<NoteDTO> notes = noteService.getAllNotes();
		
		if(CollectionUtils.isEmpty(notes)) {
		return	ResponseEntity.noContent().build();			
		}
		return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> getAllNotesByUser(Integer pageNo, Integer pageSize)
	{
		
		NoteResponse notes = noteService.getAllNotesByUser(pageNo, pageSize);
		
		if(ObjectUtils.isEmpty(notes)) {
		return	ResponseEntity.noContent().build();			
		}
		return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> getNotesSearchByUser(String key, Integer pageNo, Integer pageSize)
	{
		
		NoteResponse notes = noteService.getNotesSearchByUser(pageNo, pageSize,key);
		
		if(ObjectUtils.isEmpty(notes)) {
		return	ResponseEntity.noContent().build();			
		}
		return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
	}
	
	
	@Override
	public ResponseEntity<?> softDeleteNoteById(Integer id) throws Exception{
		
		noteService.softDelete(id);
		
		return CommonUtil.createBuildResponseMessage("Deleted", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> restoreNoteById(Integer id) throws Exception{
		
		noteService.restoreNote(id);
		
		return CommonUtil.createBuildResponseMessage("Restored", HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> getRestoredNotesByUser() throws Exception{
		
		List<NoteDTO> notes= noteService.getNotesFromRecycleBin();
		
		if(CollectionUtils.isEmpty(notes)) {
			return CommonUtil.createBuildResponseMessage("Notes not available in Recycle bin", HttpStatus.OK);
		}
		
		return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> hardDeleteNoteById(Integer id) throws Exception{
		
		noteService.hardDelete(id);
		
		return CommonUtil.createBuildResponseMessage("Deleted", HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> deleteAllNotesFromRecycleBin() throws Exception{
		
		noteService.getNotesFromRecycleBin();
		
		noteService.deleteNotesFromRecycleBin();
		
		return CommonUtil.createBuildResponseMessage("Deleted", HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> favouriteNote(Integer noteId) throws Exception{
		
        noteService.favouriteNotes(noteId);
		
		return CommonUtil.createBuildResponseMessage("Noted added in favourites", HttpStatus.CREATED);
	}
	
	@Override
	public ResponseEntity<?> unFavouriteNote(Integer favNoteId) throws Exception{
		
		noteService.unFavouriteNotes(favNoteId);
						
		return CommonUtil.createBuildResponseMessage("Removed note from favourites", HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> getAllFavNotes() throws Exception{
		
		List<FavouriteNoteDTO> favouriteNotes = noteService.getFavouriteNotes();
		
		if(CollectionUtils.isEmpty(favouriteNotes)) {
			return ResponseEntity.noContent().build();
		}
		
		return CommonUtil.createBuildResponse(favouriteNotes, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> copyNote(Integer noteId) throws Exception{
		
        Boolean copyNote = noteService.copyNote(noteId);
        
        if (copyNote) {
    		return CommonUtil.createBuildResponseMessage("Copied success", HttpStatus.CREATED);

        }
        
		return CommonUtil.createErrorResponseMessage("Not copied ! try again", HttpStatus.INTERNAL_SERVER_ERROR);

		
	}
	
}
