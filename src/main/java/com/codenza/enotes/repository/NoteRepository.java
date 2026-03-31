package com.codenza.enotes.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.codenza.enotes.entity.Note;

public interface NoteRepository extends JpaRepository<Note, Integer>{

	Page<Note> findByCreatedBy(Integer userId, Pageable pagable);

	List<Note> findByCreatedByAndIsDeletedTrue(Integer userId);
	
	Page<Note> findByCreatedByAndIsDeletedFalse(Integer userId, Pageable pagable);

	List<Note> findAllByIsDeletedAndDeletedOnBefore(boolean b, LocalDateTime cutOffDays);

}
