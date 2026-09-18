package com.codenza.enotes.dto;

import java.util.Date;

import com.codenza.enotes.entity.Todo;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TodoDTO {

	
	private Integer id;
	
	private String title;
	
	private StatusDTO status;
	
	private Integer createdBy;
	
	private Date createdOn;
	
	private Integer updatedBy;
	
	private Date updatedOn;
	
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	@Getter
	@Setter
	public static class StatusDTO{
		private Integer id;
		private String name;
	}
}
