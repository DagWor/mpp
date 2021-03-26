package com.example.mpp.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
	@GetMapping("/all")
	public String allAccess() {
		return "Public Access";
	}
	
	@GetMapping("/user")
	@PreAuthorize("hasRole('USER') or hasRole('TELLER') or hasRole('ADMIN')")
	public String userAccess() {
		return "User access";
	}

	@GetMapping("/teller")
	@PreAuthorize("hasRole('TELLER')")
	public String moderatorAccess() {
		return "Teller access";
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		return "Admin Access";
	}
}
