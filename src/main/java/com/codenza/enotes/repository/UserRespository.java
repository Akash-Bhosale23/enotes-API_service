package com.codenza.enotes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codenza.enotes.entity.User;

public interface UserRespository extends JpaRepository<User, Integer> {

	Boolean existsByEmail(String email);

}
