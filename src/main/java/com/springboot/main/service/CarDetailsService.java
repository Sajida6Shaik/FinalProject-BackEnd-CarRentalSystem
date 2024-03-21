package com.springboot.main.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.springboot.main.exception.InvalidIdException;
import com.springboot.main.model.CarDetails;
import com.springboot.main.repository.CarDetailsRepository;

@Service
public class CarDetailsService {

	@Autowired
	private CarDetailsRepository carDetailsRepository;

	public CarDetails insertCar(CarDetails car) {

		return carDetailsRepository.save(car);
	}

	// GET ALL CARS

	public List<CarDetails> getAll(Pageable pageable) {
		return carDetailsRepository.findAll(pageable).getContent();
	}

	// GET CAR BY ID

	public CarDetails getById(int carid) throws InvalidIdException {
		Optional<CarDetails> optional = carDetailsRepository.findById(carid);
		if (!optional.isPresent())
			throw new InvalidIdException("CarID is Invalid");

		return optional.get();
	}

	// TO DELETE A CAR

	public void deleteCar(int carid) {
		carDetailsRepository.deleteById(carid);

	}
 
	
//SEARCH CAR
	public List<CarDetails> getCarDetailsByLocationAndDate(String sourceLocation, String destinationLocation,
			LocalDate fromDateParsed, LocalDate toDateParsed) {
		 
		return  carDetailsRepository.findBySourceLocationAndDestinationLocationAndFromDateAndToDate(sourceLocation, destinationLocation, fromDateParsed,toDateParsed);
	}
}

	 

	 

	 


