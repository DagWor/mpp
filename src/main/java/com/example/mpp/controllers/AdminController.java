package com.example.mpp.controllers;

import com.example.mpp.models.Branch;
import com.example.mpp.models.ERole;
import com.example.mpp.models.Role;
import com.example.mpp.models.User;
import com.example.mpp.payload.request.SignupRequest;
import com.example.mpp.payload.response.MessageResponse;
import com.example.mpp.repository.BranchRepository;
import com.example.mpp.repository.RoleRepository;
import com.example.mpp.repository.UserRepository;
import com.mongodb.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
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
	MongoOperations mongoOperations;

	@Autowired
	PasswordEncoder encoder;

	MongoTemplate mongoTemplate;

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/create-teller")
	public ResponseEntity<?> registerTeller(@Valid @RequestBody SignupRequest signUpRequest) {
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

				Authentication auth = SecurityContextHolder.getContext().getAuthentication();

				User _user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
						encoder.encode(signUpRequest.getPassword()));
				User x = userRepository.findUserByUsername(auth.getName());

				_user.setRoles(roles);
				userRepository.save(_user);


				Branch branch = branchRepository.findBranchByAdmin_Email(x.getEmail());
				List<User> tellers = new ArrayList<>();
				tellers = branch.getBranchTellers();
				tellers.add(_user);

				branch.setBranchTellers(tellers);
				branchRepository.save(branch);

				return new ResponseEntity<>(_user, HttpStatus.CREATED);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/tellers/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('HEAD_OFFICE')")
	public ResponseEntity<List<User>> getAllBranchTellers(@PathVariable("id") String id) {
		ResponseEntity<List<User>> result;
		try {

			Optional<Branch> branch = branchRepository.findById(id);
			Role userRole = roleRepository.findByName(ERole.ROLE_TELLER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));

			Branch branch1 = branch.get();
			List<User> users = branch1.getBranchTellers();


			if (users.isEmpty()) {
				result = new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				result = new ResponseEntity<>(users, HttpStatus.OK);
			}

		} catch (Exception e) {
			result = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return result;
	}

	
	@PostMapping("/total-withdrawal")
	public double totalWithdrawal() {
		return 0.0;
	}

}
