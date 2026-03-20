package com.codenza.enotes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codenza.enotes.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{

	List<Category> findByIsActiveTrue();

}
