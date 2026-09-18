package com.codenza.enotes.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.codenza.enotes.entity.Note;

public interface NoteRepository extends JpaRepository<Note, Integer>{

	Page<Note> findByCreatedBy(Integer userId, Pageable pagable);

	List<Note> findByCreatedByAndIsDeletedTrue(Integer userId);
	
	Page<Note> findByCreatedByAndIsDeletedFalse(Integer userId, Pageable pagable);

	List<Note> findAllByIsDeletedAndDeletedOnBefore(boolean b, LocalDateTime cutOffDays);
	
	@Query("""
		    SELECT n FROM Note n 
		    WHERE (
		        lower(n.title) LIKE lower(concat('%', :keyword, '%'))
		        OR lower(n.description) LIKE lower(concat('%', :keyword, '%'))
		        OR lower(n.category.name) LIKE lower(concat('%', :keyword, '%'))
		    )
		    AND n.isDeleted = false
		    AND n.createdBy = :userId
		    """)
		Page<Note> searchNotes(@Param("keyword") String keyword, 
		                        @Param("userId") Integer userId, 
		                        Pageable pageable);

}
