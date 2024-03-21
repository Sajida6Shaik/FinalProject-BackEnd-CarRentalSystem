package com.springboot.main.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.springboot.main.exception.InvalidIdException;
import com.springboot.main.model.Routes;
import com.springboot.main.repository.RoutesRepository;

@Service
public class RoutesService {

	@Autowired
	private RoutesRepository routesRepository;

	public Routes insertRoutes(Routes routes) {

		return routesRepository.save(routes);
	}

	// GET ALL  ROUTES
	public List<Routes> getAll(Pageable pageable) {
		return routesRepository.findAll(pageable).getContent();

	}

	// GET BY ID ROUTES

	public Routes getById(int rid) throws InvalidIdException {
		Optional<Routes> optional = routesRepository.findById(rid);
		if (!optional.isPresent())
			throw new InvalidIdException("SearchID is Invalid");

		return optional.get();
	}

	// DELETE ROUTES

	public void deleteRoutes(Routes routes) {
		routesRepository.delete(routes);

	}

}
