package com.codenza.enotes.scheduler;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.codenza.enotes.entity.Note;
import com.codenza.enotes.repository.NoteRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class NoteScheduler {
	
	private final NoteRepository noteRepository;
	
	private final Clock clock;

	@Scheduled(cron = "0 0 0 * * ?")
	public void deleteNoteScheduler() {
		System.out.println("working");
		LocalDateTime cutOffDays = LocalDateTime.now(clock).minusDays(7);
		List<Note> deletedNotes= noteRepository.findAllByIsDeletedAndDeletedOnBefore(true, cutOffDays);
		
		noteRepository.deleteAll(deletedNotes);
		
	}
}
