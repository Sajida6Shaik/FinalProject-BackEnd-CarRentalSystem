package com.springboot.main.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.springboot.main.dto.BookingDto;
import com.springboot.main.exception.InvalidIdException;

import com.springboot.main.model.Booking;
import com.springboot.main.model.Host;
import com.springboot.main.repository.BookingRepository;

@Service
public class BookingService {
	@Autowired
	private BookingRepository bookingRepository;

	public Booking insertBooking(Booking booking) {

		return bookingRepository.save(booking);
	}

	// GET ALL Booking
	public List<Booking> getAllBooking(Pageable pageable) {

		return bookingRepository.findAll(pageable).getContent();
	}

	// GET BY ID -BOOKING

	public Booking getById(int bid) throws InvalidIdException {
		Optional<Booking> optional = bookingRepository.findById(bid);
		if (!optional.isPresent())
			throw new InvalidIdException("Booking ID Invalid");

		return optional.get();
	}
	
	 

	// DELETE BOOKING
	public void deleteBooking(int id) {

		bookingRepository.deleteById(id);
	}
	public List<Booking> getByCarId(int cid) {
		return bookingRepository.findBookingByCarId(cid);
	}

}
