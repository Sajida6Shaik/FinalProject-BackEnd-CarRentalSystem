package com.springboot.main.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.main.exception.InvalidIdException;
import com.springboot.main.model.CarDetails;
import com.springboot.main.model.Host;
import com.springboot.main.model.User;
import com.springboot.main.repository.CarDetailsRepository;
import com.springboot.main.repository.HostRepository;
import com.springboot.main.repository.UserRepository;

@Service
public class HostService {

	@Autowired
	private HostRepository hostRepository;

	@Autowired
	private CarDetailsRepository carDetailsRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public Host insertHost(Host host) {
		User userbody = host.getUser();
		User user = new User(userbody.getEmailId(), userbody.getUsername(),
				passwordEncoder.encode(userbody.getPassword()), "HOST");
		user = userRepository.save(user);
		host.setUser(user);
		return hostRepository.save(host);

	}

	// GET ALL HOSTS
	public List<Host> getAllhosts(Pageable pageable) {

		return hostRepository.findAll(pageable).getContent();
	}

	// GET HOST BY ID
	public Host getById(int hid) throws InvalidIdException {
		Optional<Host> optional = hostRepository.findById(hid);
		if (!optional.isPresent())
			throw new InvalidIdException("HostID Invalid");
		return optional.get();
	}

	// DELETE AN HOST

	public void deleteHost(int hid) {
		hostRepository.deleteById(hid);

	}
	
	public List<Host> getHosts() {
		return hostRepository.findAll();
	}

	// GET CARS MANAGED BY HOST
	public List<CarDetails> getCarsManagedByHost(int uid) {
		Host host = hostRepository.findByUserId(uid);
		// TODO Auto-generated method stub
		return carDetailsRepository.findByHostId(host.getHostId());
	}

}
