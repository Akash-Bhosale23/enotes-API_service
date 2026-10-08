package com.codenza.enotes.service;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import com.codenza.enotes.dto.CategoryDTO;
import com.codenza.enotes.entity.Category;
import com.codenza.enotes.exceptions.ExistDataException;
import com.codenza.enotes.repository.CategoryRepository;
import com.codenza.enotes.service.impl.CatetoryServiceImpl;
import com.codenza.enotes.util.Validation;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

	@Mock
	private CategoryRepository categoryRepo;
	
	@InjectMocks
	private CatetoryServiceImpl categoryService;
	
	@Mock
	private ModelMapper mapper;
	
	@Mock
	private Validation validation;
	
	private CategoryDTO categoryDto=null;
	
	private Category category=null;
	
	@BeforeEach
	public void initialize() {
		categoryDto= CategoryDTO.builder()
				.name("Java Programming")
				.description("This is java programming notes")
				.isActive(true)
				.build();
		
		category = Category.builder()
				.name("Java Programming")
				.description("This is java programming notes")
				.isActive(true)
				.build();
	}
	
	@Test
	public void testSaveCategory() {
		
		//arrange
		when(categoryRepo.existsByName(categoryDto.getName())).thenReturn(false);
		when(mapper.map(categoryDto, Category.class)).thenReturn(category);
		when(categoryRepo.save(category)).thenReturn(category);
 	
		//act
		Boolean saveCategory = categoryService.saveCategory(categoryDto);
		
		//assert
		assertTrue(saveCategory);
		
		//verify
		verify(validation).categoryValidation(categoryDto);
		verify(categoryRepo).existsByName(categoryDto.getName());
		verify(categoryRepo).save(category);
		
	}
	
	@Test
	public void testCategoryExists() {
		when(categoryRepo.existsByName(categoryDto.getName())).thenReturn(true);
		
		ExistDataException exception= assertThrows(ExistDataException.class, ()->{
			categoryService.saveCategory(categoryDto);
		});
		
		assertEquals("Category is already exist", exception.getMessage());
		
		verify(validation).categoryValidation(categoryDto);
		verify(categoryRepo).existsByName(categoryDto.getName());
		verify(categoryRepo,never()).save(category);
		

	}
	
	@Test
	public void testUpdateCategory() {
		
		categoryDto.setId(1);
		category.setId(1);
		
		//arrange
		when(categoryRepo.existsByName(categoryDto.getName())).thenReturn(false);
		when(mapper.map(categoryDto, Category.class)).thenReturn(category);
		when(categoryRepo.save(category)).thenReturn(category);
 		
		//act
		Boolean saveCategory = categoryService.saveCategory(categoryDto);
		
		//assert
		assertTrue(saveCategory);
		
		//verify
		verify(validation).categoryValidation(categoryDto);
		verify(categoryRepo).existsByName(categoryDto.getName());
		verify(categoryRepo).save(category);
		
	}
	
	@Test
	public void testGetAllCategory() {

	    //arrange
	    List<Category> categoryList = List.of(category);
	    when(categoryRepo.findByIsDeletedFalse()).thenReturn(categoryList);
	    when(mapper.map(category, CategoryDTO.class)).thenReturn(categoryDto);

	    //act
	    List<CategoryDTO> result = categoryService.getAllCategory();

	    //assert
	    assertNotNull(result);
	    assertEquals(1, result.size());
	    assertEquals("Java Programming", result.get(0).getName());

	    //verify
	    verify(categoryRepo).findByIsDeletedFalse();
	    verify(mapper).map(category, CategoryDTO.class);
	}

	@Test
	public void testGetAllCategory_whenNoCategoriesExist() {

	    //arrange
	    when(categoryRepo.findByIsDeletedFalse()).thenReturn(Collections.emptyList());

	    //act
	    List<CategoryDTO> result = categoryService.getAllCategory();

	    //assert
	    assertNotNull(result);
	    assertTrue(result.isEmpty());

	    //verify
	    verify(categoryRepo).findByIsDeletedFalse();
	    verify(mapper, never()).map(any(), eq(CategoryDTO.class));
	}
	
}
