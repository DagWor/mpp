package com.example.mpp.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin")
public class AdminController {
	
	
	@PostMapping("/create-teller-acount")
	@PreAuthorize("ADMIN")
	public void createTellerAccount() {
		
	}
	
	@GetMapping("/total-withdrawal")
	public double totalWithdrawal() {
		
		return 0.0;
	}
	

	
	

}
