package com.springboot.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.main.model.Routes;

public interface RoutesRepository  extends JpaRepository<Routes, Integer>{

	 

}