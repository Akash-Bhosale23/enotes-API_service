package com.codenza.enotes.scheduler;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.codenza.enotes.entity.Note;
import com.codenza.enotes.repository.NoteRepository;

@Component
public class NoteScheduler {
	
	@Autowired
	private NoteRepository noteRepository;

	@Scheduled(cron = "0 0 0 * * ?")
	public void deleteNoteScheduler() {
		System.out.println("working");
		LocalDateTime cutOffDays = LocalDateTime.now().minusDays(7);
		List<Note> deletedNotes= noteRepository.findAllByIsDeletedAndDeletedOnBefore(true, cutOffDays);
		
		noteRepository.deleteAll(deletedNotes);
		
	}
}
