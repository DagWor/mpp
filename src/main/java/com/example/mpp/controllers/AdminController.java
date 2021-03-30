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
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


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

				User _user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(), signUpRequest.getPassword());
				User x = userRepository.findUserByUsername(auth.getName());
//
//				updateBranch(branch, x);
				userRepository.save(_user);
				_user.setRoles(roles);


				Branch branch = branchRepository.findBranchByAdmin_Email(x.getEmail());
				List<User> tellers = branch.getBranchTellers();
				tellers.add(_user);
//				branch.setBranchTellers(tellers);

				branch.setBranchTellers(tellers);
				mongoOperations.save(branch, "branch");

//				branchRepository.save(branch);
				return new ResponseEntity<>(_user, HttpStatus.CREATED);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@GetMapping("/total-withdrawal")
	public double totalWithdrawal() {
		
		return 0.0;
	}
	

	
	

}
