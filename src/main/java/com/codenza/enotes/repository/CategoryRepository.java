package com.codenza.enotes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codenza.enotes.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{

}
