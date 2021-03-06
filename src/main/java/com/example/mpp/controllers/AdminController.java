package com.example.mpp.controllers;

import com.example.mpp.models.*;
import com.example.mpp.payload.request.RegistorTellerRequest;
import com.example.mpp.payload.response.MessageResponse;
import com.example.mpp.repository.*;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	BranchRepository branchRepository;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	TransactionRepository transactionRepository;

	@Autowired
	MongoOperations mongoOperations;

	@Autowired
	PasswordEncoder encoder;

	MongoTemplate mongoTemplate;

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Authentication findAuth(){
		return SecurityContextHolder.getContext().getAuthentication();
	}

	Set<Role> roles = new HashSet<>();

	public User findUser(Authentication auth){
		return userRepository.findUserByUsername(auth.getName());
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/create-teller")
	public ResponseEntity<?> registerTeller(@Valid @RequestBody RegistorTellerRequest signUpRequest) {
		try {
			if (userRepository.existsByUsername(signUpRequest.getUsername())) {
				return ResponseEntity
						.badRequest()
						.body(new MessageResponse("Username already taken"));
			}
			else
			if(userRepository.existsByUsername(signUpRequest.getEmail())){
				return ResponseEntity
						.badRequest()
						.body(new MessageResponse("Email already taken"));
			}
			else{
				Set<Role> roles = new HashSet<>();
				Role userRole = roleRepository.findByName(ERole.ROLE_TELLER)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(userRole);

				// Teller address
				Address address = new Address(signUpRequest.getStreet(), signUpRequest.getCity(),
						signUpRequest.getPostalCode(), signUpRequest.getZipCode(), signUpRequest.getCountry());



				User _user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
						encoder.encode(signUpRequest.getPassword()));



               // find current user
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();

                User findUser=userRepository.findUserByUsername(auth.getName());

				Branch branch = branchRepository.findBranchByBranchName(findUser.getBranchName());

				_user.setBranchName(findUser.getBranchName());
				_user.setAddress(address);
				_user.setRoles(roles);
				userRepository.save(_user);
				List<User> userList=branch.getUser();
				userList.add(_user);
				branch.setUser(userList);
				branchRepository.save(branch);
				return new ResponseEntity<>(_user, HttpStatus.CREATED);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@GetMapping ("/total-withdrawal")
	public double totalWithdrawal() {

		User admin = findUser(findAuth());

		Role userRole = roleRepository.findByName(ERole.ROLE_USER)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));

		roles.add(userRole);

		List<Transaction> transactions = transactionRepository.findTransactionByBranchName(admin.getBranchName());

		double withdrawalAmount = 0.0;

		for (Transaction transaction : transactions){
			if (transaction.getType() == TransactionType.WITHDRAWL){
				withdrawalAmount += transaction.getAmount();
			}
		}

		return withdrawalAmount;
	}


	@GetMapping ("/total-deposit")
	public double totalDeposit() {

		User admin = userRepository.findUserByUsername(findAuth().getName());

		Role userRole = roleRepository.findByName(ERole.ROLE_USER)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));

		roles.add(userRole);

		List<Transaction> transactions = transactionRepository.findTransactionByBranchName(admin.getBranchName());

		double withdrawalAmount = 0.0;

		for (Transaction transaction : transactions){
			if (transaction.getType() == TransactionType.DEPOSIT){
				withdrawalAmount += transaction.getAmount();
			}
		}

		return withdrawalAmount;
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/total-customers")
	public int totalCustomer() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User admin = userRepository.findUserByUsername(auth.getName());

		Set<Role> roles = new HashSet<>();
		Role userRole = roleRepository.findByName(ERole.ROLE_USER)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));

		roles.add(userRole);

		List<User> tellers = userRepository.findUserByBranchNameAndRoles(admin.getBranchName(), roles);

		return tellers.size();
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/total-tellers")
	public int totalTeller() {
		User admin = userRepository.findUserByUsername(findAuth().getName());

		Role userRole = roleRepository.findByName(ERole.ROLE_TELLER)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));

		roles.add(userRole);

		List<User> tellers = userRepository.findUserByBranchNameAndRoles(admin.getBranchName(), roles);

		return tellers.size();
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/tellers")
	public ResponseEntity<List<User>> tellers() {
		try {
			User admin = findUser(findAuth());
			Role userRole = roleRepository.findByName(ERole.ROLE_TELLER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));

			roles.add(userRole);

			List<User> tellers = userRepository.findUserByBranchNameAndRoles(admin.getBranchName(), roles);

			if(tellers.size() != 0){
				return new ResponseEntity<>(tellers, HttpStatus.OK);
			}
			else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (NullPointerException e){
			System.out.println(e.getMessage());
		}

		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/branch_details")
	public ResponseEntity<?> details() {
		try{
			int customers = totalCustomer();
			int tellers = totalTeller();
			double withdrawal = totalWithdrawal();
			double deposit = totalDeposit();
			String customer = "customers";
			String teller = "customers";
			String withdraw = "customers";
			String deposits = "customers";

			JSONObject jsonObject = new JSONObject();
			jsonObject.put(customer, customers);
			jsonObject.put(teller, tellers);
			jsonObject.put(withdraw, withdrawal);
			jsonObject.put(deposits, deposit);

			return new ResponseEntity<>(jsonObject, HttpStatus.OK);

		} catch (NullPointerException e){
			System.out.println(e.getMessage());
		}

		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

	}

}
