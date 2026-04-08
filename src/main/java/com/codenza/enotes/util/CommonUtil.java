package com.codenza.enotes.util;

import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.codenza.enotes.response.handler.GenericResponse;

public class CommonUtil {

	public static ResponseEntity<?> createBuildResponse(Object data, HttpStatus status){
		GenericResponse response = GenericResponse.builder()
		.responseStatus(status)
		.status("success")
		.message("success")
		.data(data)
		.build();
		
		return response.create();
	}
	
	public static ResponseEntity<?> createBuildResponseMessage(String message, HttpStatus status){
		GenericResponse response = GenericResponse.builder()
		.responseStatus(status)
		.status("success")
		.message(message)
		.build();
		
		return response.create();
	}
	
	public static ResponseEntity<?> createErrorResponse(Object data, HttpStatus status){
		GenericResponse response = GenericResponse.builder()
		.responseStatus(status)
		.status("Failed")
		.message("Failed")
		.data(data)
		.build();
		
		return response.create();
	}
	
	public static ResponseEntity<?> createErrorResponseMessage(String message, HttpStatus status){
		GenericResponse response = GenericResponse.builder()
		.responseStatus(status)
		.status("Failed")
		.message(message)
		.build();
		
		return response.create();
	}

	public static String getContentType(String originalFileName) {
		String extension = FilenameUtils.getExtension(originalFileName);
		
		switch (extension) {
		case "pdf": {
			
			return "application/pdf";
		}
		case "xlsx": {

			return "application/vnd.openxmlformats-officedocument.spreadsheethtml.sheet";
		}
		case "txt": {

			return "txt/plan";
		}
		case "png": {

			return "image/png";
		}
		case "jpeg": {

			return "image/jpeg";
		}
		default:
			return "application/octet-stream";
		}

	}
	
    public static String buildVerificationUrl(Integer userId, String code) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/home/verify")
                .queryParam("uId", userId)
                .queryParam("code", code)
                .build()
                .toUriString();
    }
}
