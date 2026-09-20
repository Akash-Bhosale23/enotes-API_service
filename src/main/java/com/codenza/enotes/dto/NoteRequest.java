package com.codenza.enotes.dto;

import com.codenza.enotes.dto.NoteDTO.CategoryDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NoteRequest {

	private String title;
	
	private String description;
	
	private CategoryDTO category;
	
}
