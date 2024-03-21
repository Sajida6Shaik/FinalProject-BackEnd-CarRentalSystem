
package com.springboot.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.main.exception.InvalidIdException;

import com.springboot.main.model.Booking;
import com.springboot.main.model.CarDetails;
import com.springboot.main.model.Customer;

import com.springboot.main.model.Host;
import com.springboot.main.service.BookingService;
import com.springboot.main.service.CarDetailsService;
import com.springboot.main.service.CustomerService;
import com.springboot.main.service.HostService;

@CrossOrigin("*")
@RestController
@RequestMapping("/booking")
public class BookingController {

	@Autowired
	private BookingService bookingService;

	@Autowired
	private CustomerService customerService;
	@Autowired
	private CarDetailsService carDetailsService;

	@PostMapping("/add/{cid}/{carid}")
	public ResponseEntity<?> insertBooking(@PathVariable("cid") int cid, @PathVariable("carid") int carid,
			@RequestBody Booking booking) {
		try {
			Customer customer = customerService.getById(cid);
			CarDetails car = carDetailsService.getById(carid);

			booking.setCarDetails(car);
			booking.setCustomer(customer);

			booking = bookingService.insertBooking(booking);
			return ResponseEntity.ok().body(booking);
		} catch (InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	// GET ALL BOOKINGS
	@PreAuthorize("hasAuthority('HOST') OR hasAuthority('ADMIN')" )
	@GetMapping("/getallBookings")

	public List<Booking> getAllBookings(
			@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(value = "size", required = false, defaultValue = "1000000") Integer size) {

		Pageable pageable = PageRequest.of(page, size);
		return bookingService.getAllBooking(pageable);

	}

	// GET ONE BOOKINGS
	@PreAuthorize("hasAuthority('HOST') OR hasAuthority('ADMIN')" )
	@GetMapping("/getone/{bid}")
	public ResponseEntity<?> getone(@PathVariable("bid") int bid) throws InvalidIdException {
		try {
			Booking booking = bookingService.getById(bid);
			return ResponseEntity.ok().body(booking);
		} catch (InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());

		}
	}

 
	// UPDATE BOOKING
	@PreAuthorize(" hasAuthority('ADMIN')" )
	@PutMapping("/update/{bid}")
	public ResponseEntity<?> updateBooking(@PathVariable("bid") int bid, @RequestBody Booking newBooking)
			throws InvalidIdException {
		// validate id
		Booking booking = bookingService.getById(bid);

		if (newBooking.getCarDetails() != null)
			booking.setCarDetails(newBooking.getCarDetails());

		if (newBooking.getCustomer() != null)
			booking.setCustomer(newBooking.getCustomer());

		if (newBooking.getBookingStatus() != null)
			booking.setBookingStatus(newBooking.getBookingStatus());

		if (newBooking.getDropOfLocation() != null)
			booking.setDropOfLocation(newBooking.getDropOfLocation());

		if (newBooking.getPickupLocation() != null)
			booking.setPickupLocation(newBooking.getPickupLocation());

		if (newBooking.getFromDate() != null)
			booking.setFromDate(newBooking.getFromDate());

		if (newBooking.getToDate() != null)
			booking.setToDate(newBooking.getToDate());

		if (newBooking.getPrice() != 0)
			booking.setPrice(newBooking.getPrice());

		booking = bookingService.insertBooking(booking);
		return ResponseEntity.ok().body(booking);
	}

	// DELETE BOOKING
	@PreAuthorize(" hasAuthority('ADMIN')" )

	@DeleteMapping("/delete/{bid}")
	public ResponseEntity<?> deleteBooking(@PathVariable("bid") int bid) throws InvalidIdException {

		// validate id
		Booking booking = bookingService.getById(bid);
		// delete
		bookingService.deleteBooking(booking.getBookingId());
		return ResponseEntity.ok().body("Booking deleted successfully");
	}
}
