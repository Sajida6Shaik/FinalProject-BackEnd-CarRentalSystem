package com.springboot.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.main.model.CustomerCar;

public interface CustomerCarRepository extends JpaRepository<CustomerCar, Integer> {

	@Query("select cc from CustomerCar cc where cc.car.carId=:cid")
	List<CustomerCar> findByCarId(int cid);
}