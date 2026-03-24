package com.codenza.enotes.dto;

import java.util.Date;

import com.codenza.enotes.entity.Category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NoteDTO {

	private Integer id;
	
	private String title;
	
	private String description;
	
	private CategoryDTO category;
	
	private Integer createdBy;
	
	private Date createdOn;
	
	private Integer updatedBy;
	
	private Date updatedOn;
	
	
	@AllArgsConstructor
	@NoArgsConstructor
	@Getter
	@Setter
	public static class CategoryDTO{
		
		private Integer id;
		
		private String name;
		
	}
}
