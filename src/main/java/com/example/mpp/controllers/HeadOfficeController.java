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
@RequestMapping("/api/superadmin")
public class HeadOfficeController {

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

    @PreAuthorize("hasRole('HEAD_OFFICE')")
    @PostMapping("/create-admin")
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
                Role userRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);

                Authentication auth = SecurityContextHolder.getContext().getAuthentication();

                User _admin = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
                        encoder.encode(signUpRequest.getPassword()));
                User headOffice = userRepository.findUserByUsername(auth.getName());

                _admin.setRoles(roles);
                userRepository.save(_admin);

                Branch branch = new Branch(_admin);
                branchRepository.save(branch);

                List<Branch> branches = headOffice.getBranches();
                branches.add(branch);
                headOffice.setBranches(branches);
                userRepository.save(headOffice);

                return new ResponseEntity<>(_admin, HttpStatus.CREATED);
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