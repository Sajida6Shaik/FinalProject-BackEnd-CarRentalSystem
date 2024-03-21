
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
import com.springboot.main.model.CarDetails;
import com.springboot.main.model.Customer;
import com.springboot.main.model.CustomerCar;
import com.springboot.main.service.CarDetailsService;
 
import com.springboot.main.service.CustomerCarService;
import com.springboot.main.service.CustomerService;
@CrossOrigin("*")
@RestController
@RequestMapping("/customercar")
public class CustomerCarController {

	@Autowired
	private CustomerCarService customercarService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private CarDetailsService carService;
	@PreAuthorize("hasAuthority('ADMIN')" )
	@PostMapping("/add/{cid}/{carid}")
	public ResponseEntity<?> insertCustomerCar(@PathVariable("cid") int cid, @PathVariable("carid") int carid,
			@RequestBody CustomerCar customerCar) {
		try {
			Customer customer = customerService.getById(cid);
			CarDetails car = carService.getById(carid);
			customerCar.setCar(car);
			customerCar.setCustomer(customer);
			customerCar = customercarService.insert(customerCar);
			return ResponseEntity.ok().body(customerCar);
		} catch (InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	// GET ALL CUSTOMERCARS
	@PreAuthorize(" hasAuthority('ADMIN')" )
	@GetMapping("/getallcustomercar")
	public List<CustomerCar> getAll(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(value = "size", required = false, defaultValue = "10000000") Integer size) {
		Pageable pageable = PageRequest.of(page, size);
		return customercarService.getAll(pageable);
	}
	
	
	
	// GET MAPPING FOR ONE CUSTOMER CAR
	@PreAuthorize(" hasAuthority('ADMIN')" )
	@GetMapping("/getone/{custid}")
	public ResponseEntity<?> getone(@PathVariable("custid") int custid) throws InvalidIdException {
		try {
			CustomerCar customercar = customercarService.getById(custid);
			return ResponseEntity.ok().body(customercar);
		} catch (InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());

		}
	}

	// PUT MAPPING
	@PreAuthorize("hasAuthority('ADMIN')" )
	@PutMapping("/update/{custid}")
	public ResponseEntity<?> updateCustomerCar(@PathVariable("custid") int custid,
			@RequestBody CustomerCar newCustomerCar) throws InvalidIdException {

		// VALIDATE ID
		CustomerCar oldcustomercar = customercarService.getById(custid);

		if (newCustomerCar.getId() != 0)
			oldcustomercar.setId(newCustomerCar.getId());

		if (newCustomerCar.getDate() != null)
			oldcustomercar.setDate(newCustomerCar.getDate());

		oldcustomercar = customercarService.insert(oldcustomercar);
		return ResponseEntity.ok().body(oldcustomercar);
	}

	// DELETE A CUSTOMERCAR
	@PreAuthorize("hasAuthority('ADMIN')" )
	@DeleteMapping("/delete/{custid}")
	public ResponseEntity<?> deleteCustomerCar(@PathVariable("custid") int custid) {
		try {
			CustomerCar customerCar = customercarService.getById(custid);
			customercarService.deleteCustomerCar(custid);
			return ResponseEntity.ok().body("CustomerCar is Deleted");
		} catch (InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
