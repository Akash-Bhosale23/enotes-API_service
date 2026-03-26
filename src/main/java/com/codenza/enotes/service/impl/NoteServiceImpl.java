package com.codenza.enotes.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;

import com.codenza.enotes.dto.NoteDTO;
import com.codenza.enotes.dto.NoteDTO.CategoryDTO;
import com.codenza.enotes.entity.Category;
import com.codenza.enotes.entity.FileDetails;
import com.codenza.enotes.entity.Note;
import com.codenza.enotes.exceptions.ResourceNotFoundException;
import com.codenza.enotes.repository.CategoryRepository;
import com.codenza.enotes.repository.FileRepository;
import com.codenza.enotes.repository.NoteRepository;
import com.codenza.enotes.service.NoteService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class NoteServiceImpl implements NoteService {
	
	@Autowired
	private NoteRepository noteRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Value("${file.upload.path}")
	private String uploadPath;
	
	@Autowired
	private FileRepository fileRepository;

	@Override
	public Boolean saveNote(String note, MultipartFile file) throws Exception {
		
		ObjectMapper ob=new ObjectMapper();
		NoteDTO noteDTO = ob.readValue(note, NoteDTO.class);
	
		
		// Category validation method
		checkCategoryExists(noteDTO.getCategory());
		
		
		Note mapNote = mapper.map(noteDTO, Note.class);
		
		FileDetails fileDetails = saveFile(file);

		if (!ObjectUtils.isEmpty(fileDetails)) {
			mapNote.setFile(fileDetails);
		}
		else {
			mapNote.setFile(null);
		}
		
		Note savedNote = noteRepository.save(mapNote);
		
		if(!ObjectUtils.isEmpty(savedNote)) {
			return true;
		}
		
		return false;
	}

	private FileDetails saveFile(MultipartFile file) throws IOException {
	
		if(!ObjectUtils.isEmpty(file) && !file.isEmpty()) {
			
			
			String originalFilename = file.getOriginalFilename();
			String extension = FilenameUtils.getExtension(originalFilename);
			
			List<String> extensionAllow = Arrays.asList("pdf", "xlsx", "jpg", "png", "jpeg");
			
			if(!extensionAllow.contains(extension)) {
			
				throw new IllegalArgumentException("Invalid file format.. updload only pdf, xlxs, jpg, png ,jpeg");
			}
			
			
			String originalFileName=file.getOriginalFilename();
			
			String randomString=UUID.randomUUID().toString();

			String uploadedFileName =randomString+"."+extension;
				
			File saveFile= new File(uploadPath);
			
			if(!saveFile.exists()) {
				saveFile.mkdir();
			}
			
			//path: enotesrestapi/notes/filename.extension
			String storePath=uploadPath.concat(uploadedFileName);
			
			long upload = Files.copy(file.getInputStream(), Paths.get(storePath));
			
			if (upload != 0) {
				FileDetails fileDetails = new FileDetails();
				fileDetails.setOriginalFileName(originalFileName);
				fileDetails.setDisplayFileName(getDisplayFileName(originalFileName));
				fileDetails.setUploadedFileName(uploadedFileName);
				fileDetails.setFileSize(file.getSize());
				fileDetails.setPath(storePath);
				FileDetails savedFile = fileRepository.save(fileDetails);
				return savedFile;
			}
		}
		
		return null;
	}

	private String getDisplayFileName(String originalFileName) {
		String extension = FilenameUtils.getExtension(originalFileName);
		String fileName = FilenameUtils.removeExtension(originalFileName);
		
		if(fileName.length()>8) {
			fileName=fileName.substring(0, 7);
		}
		
		fileName=fileName+"."+extension;
		return fileName;
		
	}

	private void checkCategoryExists(CategoryDTO category) throws Exception {
		categoryRepository.findById(category.getId()).orElseThrow(()-> new ResourceNotFoundException("Category id is invalid"));
		
	}

	@Override
	public List<NoteDTO> getAllNotes() {

		List<NoteDTO> allNotes = noteRepository.findAll().stream().map(note->mapper.map(note, NoteDTO.class)).toList();
		
		return allNotes;
	}

	@Override
	public byte[] downloadFile(FileDetails fileDetails) throws Exception {
//		FileDetails fileDtls = fileRepository.findById(fileDetails.getId()).orElseThrow(()->new ResourceNotFoundException("File is not available"));
		
		InputStream io= new FileInputStream(fileDetails.getPath());
		
		return StreamUtils.copyToByteArray(io);
		
		
	}

	@Override
	public FileDetails getFileDetails(Integer id) throws Exception {
		FileDetails fileDtls = fileRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("File is not available"));
		return fileDtls;
	}

}
