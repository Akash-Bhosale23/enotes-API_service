package com.codenza.enotes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PswdResetRequest {

	@JsonProperty("uId")
	private Integer uId;
	
	private String newPassword;
	
}
