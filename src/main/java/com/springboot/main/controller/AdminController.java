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

import com.springboot.main.dto.AdminDto;
import com.springboot.main.exception.InvalidIdException;
import com.springboot.main.model.Admin;
import com.springboot.main.service.AdminService;
@CrossOrigin("*")
@RestController
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private AdminService adminService;
 
	@PostMapping("/add")
	public Admin insertAdmin(@RequestBody Admin admin) {
		return adminService.insertAdmin(admin);
	}

	// GET ALL ADMINS
	
	@PreAuthorize("hasAuthority('ADMIN')" )
	@GetMapping("/getallAdmins")

	public List<Admin> getAllAdmins(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(value = "size", required = false, defaultValue = "1000000") Integer size) {

		Pageable pageable = PageRequest.of(page, size);
		return adminService.getAll(pageable);

	}

	// GET ADMIN BY ID
	@PreAuthorize("hasAuthority('ADMIN')" )
	@GetMapping("/getone/{aid}")

	public ResponseEntity<?> getById(@PathVariable("aid") int aid) {

		try {
			Admin admin = adminService.getById(aid);

			return ResponseEntity.ok().body(admin);

		} catch (InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

			//UPDATE ADMIN
	@PreAuthorize("hasAuthority('ADMIN')" )
	@PutMapping("/update/{aid}")
	public ResponseEntity<?> updateAdmin(@PathVariable("aid") int aid, @RequestBody Admin adminDto)
			throws InvalidIdException {
		// validate id
		Admin admin = adminService.getById(aid);

		if (adminDto.getAdminName() != null)
			admin.setAdminName(adminDto.getAdminName());
		if (adminDto.getEmail() != null)
			adminDto.setEmail(adminDto.getEmail());
		if (adminDto.getPhoneNo() != null)
			adminDto.setPhoneNo(adminDto.getPhoneNo());

		adminDto = adminService.insertAdmin(adminDto);
		return ResponseEntity.ok().body(adminDto);
	}

	//DELETE ADMIN
	@PreAuthorize("hasAuthority('ADMIN')" )
	@DeleteMapping("/delete/{aid}")
	public ResponseEntity<?> deleteAdmin(@PathVariable("aid") int aid) throws InvalidIdException {

		// validate id
		Admin admin = adminService.getById(aid);
		// delete
		adminService.deleteAdmin(admin);
		return ResponseEntity.ok().body("Admin deleted successfully");
	}
}
