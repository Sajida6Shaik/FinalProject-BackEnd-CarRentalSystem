package com.springboot.main.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.main.exception.InvalidIdException;
import com.springboot.main.model.Booking;
import com.springboot.main.model.CarDetails;
import com.springboot.main.model.Customer;
import com.springboot.main.model.User;
import com.springboot.main.repository.BookingRepository;
import com.springboot.main.repository.CarDetailsRepository;
import com.springboot.main.repository.CustomerRepository;
import com.springboot.main.repository.UserRepository;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private CarDetailsRepository carDetailsRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public Customer insertCustomer(Customer customer) {
		User userbody = customer.getUser();
		User user = new User(userbody.getEmailId(), userbody.getUsername(),
				passwordEncoder.encode(userbody.getPassword()), "CUSTOMER");
		user = userRepository.save(user);
		customer.setUser(user);
		return customerRepository.save(customer);
	}

	// GET ALL CUSTOMER
	public List<Customer> getAll(Pageable pageable) {

		return customerRepository.findAll(pageable).getContent();
	}

	// GET CUSTOMER BY ID
	public Customer getById(int cid) throws InvalidIdException {
		Optional<Customer> optional = customerRepository.findById(cid);
		if (!optional.isPresent())
			throw new InvalidIdException("CustomerID Invalid");

		return optional.get();
	}

	// DELETE
	public void deleteCustomer(int cid) {
		customerRepository.deleteById(cid);

	}

	// BookingsMadeByCustomer

	public Optional<Booking> getBookingsMadeByCustomer(int cid) {
		// TODO Auto-generated method stub
		return bookingRepository.findById(cid);
	}

	// getCarsRentedByCustomer

	public Optional<CarDetails> getCarsRentedByCustomer(int cid) {
		// TODO Auto-generated method stub
		return carDetailsRepository.findById(cid);

	}

}
