package com.codenza.enotes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codenza.enotes.entity.FileDetails;

public interface FileRepository extends JpaRepository<FileDetails, Integer>{

}
