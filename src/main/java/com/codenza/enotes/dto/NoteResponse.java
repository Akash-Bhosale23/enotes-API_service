package com.codenza.enotes.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class NoteResponse {

	private List<NoteDTO> notes;
	
	private Integer pageNo;
	
	private Integer pageSize;
	
	private Long totalElement;
	
	private Integer totalPages;
	
	private Boolean isFirst;
	
	private Boolean isLast;
	
}
