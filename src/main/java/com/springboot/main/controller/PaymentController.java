
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

import com.springboot.main.dto.PaymentDto;
import com.springboot.main.exception.InvalidIdException;
import com.springboot.main.model.Customer;
import com.springboot.main.model.Payment;
import com.springboot.main.service.CustomerService;
import com.springboot.main.service.PaymentService;
@CrossOrigin("*")
@RestController
@RequestMapping("/payment")
public class PaymentController {
	@Autowired
	private PaymentService paymentService;
	@Autowired
	private CustomerService customerService;
//	@PreAuthorize("hasAuthority('CUSTOMER')" )
	@PostMapping("/add/{cid}")
	public ResponseEntity<?> insertPayment(@PathVariable("cid") int cid, @RequestBody Payment payment) {
		try {

			Customer customer = customerService.getById(cid);

			payment.setCustomer(customer);

			payment = paymentService.insertPayment(payment);
			return ResponseEntity.ok().body(payment);
		} catch (InvalidIdException e) {

			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	// GET ALL PAYMENTS
	@PreAuthorize("hasAuthority('ADMIN')" )
	@GetMapping("/getallpayments")
	public List<Payment> getAll(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(value = "size", required = false, defaultValue = "10000000") Integer size) {
		Pageable pageable = PageRequest.of(page, size);
		return paymentService.getAll(pageable);
	}

	// GET ONE PAYMENT
	@PreAuthorize("hasAuthority('ADMIN')" )
	@GetMapping("/getone/{pid}")
	public ResponseEntity<?> getone(@PathVariable("pid") int pid) throws InvalidIdException {
		try {
			Payment payment = paymentService.getByPaymentId(pid);
			return ResponseEntity.ok().body(payment);
		} catch (InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());

		}
	}

	// UPDATE PAYMENT
	@PreAuthorize("hasAuthority('ADMIN')" )
	@PutMapping("/update/{pid}")
	public ResponseEntity<?> updatePayment(@PathVariable("pid") int pid, @RequestBody PaymentDto paymentDto)
			throws InvalidIdException {
		// validate id
		Payment payment = paymentService.getByPaymentId(pid);

		if (paymentDto.getPaymentPrice()!= 0)
			payment.setPaymentPrice(paymentDto.getPaymentPrice());

		if (paymentDto.getPaymentType()!= null)
			payment.setPaymentType(paymentDto.getPaymentType());

		payment = paymentService.insertPayment(payment);
		return ResponseEntity.ok().body(payment);
	}

	// DELETE AN PAYMENT
	@PreAuthorize("hasAuthority('ADMIN')" )
	@DeleteMapping("/delete/{pid}")
	public ResponseEntity<?> deletePayment(@PathVariable("pid") int pid) throws InvalidIdException {

		// validate id
		Payment payment = paymentService.getByPaymentId(pid);
		// delete
		paymentService.deletePayment(payment);
		return ResponseEntity.ok().body("Payment deleted successfully");
	}

}
