package com.springboot.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.main.model.Booking;

public interface BookingRepository  extends JpaRepository<Booking, Integer>{

	@Query("select b from Booking b where b.carDetails.carId = :cid")
	 List<Booking> findBookingByCarId(int cid);

}
