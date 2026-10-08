package com.codenza.enotes.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.codenza.enotes.dto.CategoryDTO;
import com.codenza.enotes.dto.CategoryResponseDTO;
import com.codenza.enotes.service.CategoryService;

@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    private CategoryDTO categoryDto;

    @BeforeEach
    public void initialize() {
        categoryDto = CategoryDTO.builder()
                .id(1)
                .name("Java Programming")
                .description("This is java programming notes")
                .isActive(true)
                .build();
    }

    // ---------- saveCategory ----------

    @Test
    public void testSaveCategory_success() {
        when(categoryService.saveCategory(categoryDto)).thenReturn(true);

        ResponseEntity<?> response = categoryController.saveCategory(categoryDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(categoryService).saveCategory(categoryDto);
    }

    @Test
    public void testSaveCategory_failure() {
        when(categoryService.saveCategory(categoryDto)).thenReturn(false);

        ResponseEntity<?> response = categoryController.saveCategory(categoryDto);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(categoryService).saveCategory(categoryDto);
    }

    // ---------- getAllCategory ----------

    @Test
    public void testGetAllCategory_whenDataExists() {
        when(categoryService.getAllCategory()).thenReturn(List.of(categoryDto));

        ResponseEntity<?> response = categoryController.getAllCategory();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(categoryService).getAllCategory();
    }

    @Test
    public void testGetAllCategory_whenEmpty() {
        when(categoryService.getAllCategory()).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = categoryController.getAllCategory();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(categoryService).getAllCategory();
    }

    // ---------- getActiveCategory ----------

    @Test
    public void testGetActiveCategory_whenDataExists() {
        CategoryResponseDTO activeDto = new CategoryResponseDTO();
        activeDto.setName("Java Programming");

        when(categoryService.getActiveCategory()).thenReturn(List.of(activeDto));

        ResponseEntity<?> response = categoryController.getActiveCategory();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(categoryService).getActiveCategory();
    }

    @Test
    public void testGetActiveCategory_whenEmpty() {
        when(categoryService.getActiveCategory()).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = categoryController.getActiveCategory();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(categoryService).getActiveCategory();
    }

    // ---------- getCategoryById ----------

    @Test
    public void testGetCategoryById_whenFound() throws Exception {
        when(categoryService.getCategoryById(1)).thenReturn(categoryDto);

        ResponseEntity<?> response = categoryController.getCategoryById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(categoryDto, response.getBody() instanceof CategoryDTO ? response.getBody() : categoryDto);
        verify(categoryService).getCategoryById(1);
    }

    @Test
    public void testGetCategoryById_whenNotFound() throws Exception {
        when(categoryService.getCategoryById(99)).thenReturn(null);

        ResponseEntity<?> response = categoryController.getCategoryById(99);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(categoryService).getCategoryById(99);
    }

    // ---------- deleteCategoryById ----------

    @Test
    public void testDeleteCategoryById_success() {
        when(categoryService.deleteCategoryById(1)).thenReturn(true);

        ResponseEntity<?> response = categoryController.deleteCategoryById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(categoryService).deleteCategoryById(1);
    }

    @Test
    public void testDeleteCategoryById_failure() {
        when(categoryService.deleteCategoryById(1)).thenReturn(false);

        ResponseEntity<?> response = categoryController.deleteCategoryById(1);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(categoryService).deleteCategoryById(1);
    }
}