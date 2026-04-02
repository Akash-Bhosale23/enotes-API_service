package com.codenza.enotes.dto;

import com.codenza.enotes.entity.Note;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class FavouriteNoteDTO {

	private Integer id;
	
	private Note note;
	
	private Integer userId;
}
