package com.codenza.enotes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codenza.enotes.entity.Note;

public interface NoteRepository extends JpaRepository<Note, Integer>{

}
