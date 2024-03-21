package com.springboot.main.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.main.exception.InvalidIdException;
import com.springboot.main.model.CarDetails;
import com.springboot.main.model.Host;
import com.springboot.main.repository.CarDetailsRepository;
import com.springboot.main.service.CarDetailsService;
import com.springboot.main.service.HostService;

@CrossOrigin("*")
@RestController
@RequestMapping("/car")

public class CarDetailsController {

	@Autowired
	private CarDetailsService carDetailsService;
	@Autowired
	private HostService hostService;

	@Autowired
	private CarDetailsRepository carDetailsRepository;

	/* Insert Car by Hostid */
	@PreAuthorize("hasAuthority('HOST') OR hasAuthority('ADMIN')")
	@PostMapping("/add/{hid}")

	public ResponseEntity<?> insertCar(@PathVariable("hid") int hid, @RequestBody CarDetails car) {
		try {
			/* fetch host from db by id */
			Host host = hostService.getById(hid);
			/* attach host to car */
			car.setHost(host);
			/* save the savedCar in db */
			CarDetails savedCar = carDetailsService.insertCar(car);
			return ResponseEntity.ok().body(savedCar);
		} catch (InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	// GET ALL CARS MANAGED BY HOSTS
	@PreAuthorize("hasAuthority('CUSTOMER') OR hasAuthority('ADMIN') OR hasAuthority('HOST')")
	@GetMapping("/getallcars")

	public List<CarDetails> getAllCars(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(value = "size", required = false, defaultValue = "1000000") Integer size) {

		Pageable pageable = PageRequest.of(page, size);
		return carDetailsService.getAll(pageable);

	}

	//GET CAR BY ID
	@PreAuthorize("hasAuthority('ADMIN') OR hasAuthority('HOST') OR hasAuthority('CUSTOMER')")
	@GetMapping("/getone/{carid}")

	public ResponseEntity<?> getById(@PathVariable("carid") int carid) {

		try {
			CarDetails car = carDetailsService.getById(carid);

			return ResponseEntity.ok().body(car);

		} catch (InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	// UPDATE CAR
	@PreAuthorize("hasAuthority('HOST') OR hasAuthority('ADMIN')")
	@PutMapping("/update/{carid}")

	public ResponseEntity<?> UpdateCar(@PathVariable("carid") int carid, @RequestBody CarDetails newCar) {
		
		
		System.out.println(newCar);

		try {
			CarDetails car = carDetailsService.getById(carid);

			// car model
			if (newCar.getCarModel() != null)
				car.setCarModel(newCar.getCarModel());
			// price

			if (newCar.getPrice() != 0)
				car.setPrice(newCar.getPrice());
			// Mileage
			if (newCar.getMileage() != 0)
				car.setMileage(newCar.getMileage());
			// FuelType

			if (newCar.getFuelType() != null)
				car.setFuelType(newCar.getFuelType());
			// Seating

			if (newCar.getSeating() != 0)
				car.setSeating(newCar.getSeating());

			// color

			if (newCar.getColor() != null)
				car.setColor(newCar.getColor());

			// location
			if (newCar.getLocation() != null)
				car.setLocation(newCar.getLocation());

			// Insurance

			if (newCar.getInsurance() != null)
				car.setInsurance(newCar.getInsurance());

			// SourceLocation
			if (newCar.getSourceLocation() != null)
				car.setSourceLocation(newCar.getSourceLocation());
			// DestinationLocation
			if (newCar.getDestinationLocation() != null)
				car.setDestinationLocation(newCar.getDestinationLocation());

			// From Date
			if (newCar.getFromDate() != null)
				car.setFromDate(newCar.getFromDate());

			// To Date
			if (newCar.getToDate() != null)
				car.setToDate(newCar.getToDate());

			// CarType

			 if(newCar.getCarType()!=null)
				 car.setCarType(newCar.getCarType());

			car = carDetailsService.insertCar(car);

			return ResponseEntity.ok().body(car);

		} catch (InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	// DELETE A CAR
	@PreAuthorize("hasAuthority('ADMIN') OR hasAuthority('HOST')")
	@DeleteMapping("/delete/{carid}")

	public ResponseEntity<?> deleteCar(@PathVariable("carid") int carid) {
		try {
			CarDetails car = carDetailsService.getById(carid);
			carDetailsService.deleteCar(carid);
			return ResponseEntity.ok().body("Car is Deleted");
		} catch (InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	//SEARCH CAR
	@GetMapping("/cardetails")
	public List<CarDetails> getCarDetailsByLocationAndDate(@RequestParam String sourceLocation,
			@RequestParam String destinationLocation, @RequestParam String fromDate, @RequestParam String toDate) {
		LocalDate fromDateParsed = LocalDate.parse(fromDate);
		LocalDate toDateParsed = LocalDate.parse(toDate);

		return carDetailsService.getCarDetailsByLocationAndDate(sourceLocation, destinationLocation, fromDateParsed,
				toDateParsed);
	}

 
	
	


}
