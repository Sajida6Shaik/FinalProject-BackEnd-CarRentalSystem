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

import com.springboot.main.dto.RoutesDto;
import com.springboot.main.exception.InvalidIdException;
import com.springboot.main.model.Booking;
import com.springboot.main.model.Routes;
import com.springboot.main.service.BookingService;
import com.springboot.main.service.RoutesService;

@CrossOrigin("*")
@RestController
@RequestMapping("/routes")
public class RoutesController {
	@Autowired
	private RoutesService routesService;

	@Autowired
	private BookingService bookingService;

	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("/add/{bid}")
	public ResponseEntity<?> insertRoutes(@PathVariable("bid") int bid, @RequestBody Routes routes) {
		try {

			Booking booking = bookingService.getById(bid);

			routes.setBooking(booking);

			routes = routesService.insertRoutes(routes);
			return ResponseEntity.ok().body(routes);
		} catch (InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	// GET ALL SEARCH ROUTES
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/getallRoutes")
	public List<Routes> getAll(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(value = "size", required = false, defaultValue = "10000000") Integer size) {
		Pageable pageable = PageRequest.of(page, size);
		return routesService.getAll(pageable);
	}

	// GET ONE SEARCH
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/getone/{rid}")
	public ResponseEntity<?> getone(@PathVariable("rid") int rid) throws InvalidIdException {
		try {
			Routes routes = routesService.getById(rid);
			return ResponseEntity.ok().body(routes);
		} catch (InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());

		}
	}

	// UPDATE ROUTES
	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping("/update/{rid}")
	public ResponseEntity<?> updateRoutes(@PathVariable("rid") int rid, @RequestBody RoutesDto routesDto)
			throws InvalidIdException {
		try {
			// validate id
			Routes routes = routesService.getById(rid);

			if (routesDto.getSourceLocation() != null)
				routes.setSourceLocation(routesDto.getSourceLocation());

			if (routesDto.getDestinationLocation() != null)
				routes.setDestinationLocation(routesDto.getDestinationLocation());

			if (routesDto.getStartDate() != null)
				routes.setStartDate(routesDto.getStartDate());

			if (routesDto.getEndDate() != null)
				routes.setEndDate(routesDto.getEndDate());

			routes = routesService.insertRoutes(routes);
			return ResponseEntity.ok().body(routes);
		} catch (InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	// DELETE AN SEARCH ROUTES
	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("/delete/{rid}")
	public ResponseEntity<?> deleteRoutes(@PathVariable("rid") int rid) throws InvalidIdException {

		// validate id
		Routes routes = routesService.getById(rid);
		// delete
		routesService.deleteRoutes(routes);
		return ResponseEntity.ok().body("Routes deleted successfully");
	}

}
