package com.springboot.main.controller;

import java.util.List;
import java.util.Optional;

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

import com.springboot.main.dto.HostDto;
import com.springboot.main.exception.InvalidIdException;
import com.springboot.main.model.Booking;
import com.springboot.main.model.CarDetails;
import com.springboot.main.model.CustomerCar;
import com.springboot.main.model.Host;
import com.springboot.main.service.BookingService;
import com.springboot.main.service.CarDetailsService;
import com.springboot.main.service.CustomerCarService;
import com.springboot.main.service.HostService;
import com.springboot.main.service.UserService;

@CrossOrigin("*")
@RestController
@RequestMapping("/host")
public class HostController {
	@Autowired
	private HostService hostService;

	@Autowired
	private UserService userService;

	@Autowired
	private CarDetailsService carDetailsService;
	@Autowired
	private BookingService bookingService;
	@Autowired
	private CustomerCarService customerCarService;

	@PostMapping("/add")
	public Host insertHost(@RequestBody Host host) {
		System.out.println(host);
		return hostService.insertHost(host);
	}

	// GET ALL HOSTS
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/getallHosts")

	public List<Host> getAllHosts(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(value = "size", required = false, defaultValue = "1000000") Integer size) {

		Pageable pageable = PageRequest.of(page, size);
		return hostService.getAllhosts(pageable);

	}

	// GET HOST BY ID
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/getone/{hid}")
	public ResponseEntity<?> getById(@PathVariable("hid") int hid) {
		try {
			Host host = hostService.getById(hid);

			return ResponseEntity.ok().body(host);
		} catch (InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	// UPDATE HOST DATA

	@PreAuthorize("hasAuthority('HOST') OR hasAuthority('ADMIN')")
	@PutMapping("/update/{hid}")
	public ResponseEntity<?> updateHost(@PathVariable("hid") int hid, @RequestBody HostDto hostDto) {
		try {
			Host host = hostService.getById(hid);
			if (hostDto.getHostEmail() != null)
				host.setHostEmail(hostDto.getHostEmail());
			if (hostDto.getHostName() != null)
				host.setHostName(hostDto.getHostName());
			if (hostDto.getHostContact() != null)
				host.setHostContact(hostDto.getHostContact());
			host = hostService.insertHost(host);
			return ResponseEntity.ok().body(host);
		} catch (InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	// DELETE AN HOST
	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("/delete/{hid}")
	public ResponseEntity<?> deleteHost(@PathVariable("hid") int hid) throws InvalidIdException {

		// validate id
		Host host = hostService.getById(hid);
		int uid = host.getUser().getId();
		userService.deleteUser(uid);
		List<CarDetails> carsByHost = hostService.getCarsManagedByHost(hid);
		for (CarDetails car : carsByHost) {
			int carId = car.getCarId();
			List<Booking> bookings = bookingService.getByCarId(carId);
			for(Booking booking: bookings) {				
				bookingService.deleteBooking(booking.getBookingId());
			}
			List<CustomerCar> cars = customerCarService.getByCarId(carId);
			for(CustomerCar ccar: cars) {				
				customerCarService.deleteCustomerCar(ccar.getId());
			}
			carDetailsService.deleteCar(carId);
		}
		// delete
		hostService.deleteHost(hid);
		return ResponseEntity.ok().body("Host deleted successfully");
	}
	
	
	

	// GET ALL CARS MANAGED BY SPECIFIC HOST
	@PreAuthorize("hasAuthority('HOST') OR hasAuthority('ADMIN')")
	@GetMapping("/{hid}/cars")
	public List<CarDetails> getCarsManagedByHost(@PathVariable int hid) {
		return hostService.getCarsManagedByHost(hid);
	}
	
	
	
	 
	@GetMapping("/hosts")
	public List<Host> getHosts() {
		return hostService.getHosts();
	}
	
	
	 

}
