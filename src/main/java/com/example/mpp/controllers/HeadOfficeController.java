package com.example.mpp.controllers;

import com.example.mpp.models.*;
import com.example.mpp.payload.request.CreateBranchRequest;
import com.example.mpp.payload.response.MessageResponse;
import com.example.mpp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/superadmin")
public class HeadOfficeController {

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TransactionRepository transactionRepository;


    @PreAuthorize("hasRole('HEAD_OFFICE')")
    @PostMapping("/create-admin")
    public ResponseEntity<?> branchCreate(@Valid @RequestBody CreateBranchRequest signUpRequest) {
        try {
            if (userRepository.existsByUsername(signUpRequest.getUsername())) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Username already taken"));
            } else if (userRepository.existsByUsername(signUpRequest.getEmail())) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Email already taken"));
            } else {
                List<User> user = new ArrayList<>();

                Set<Role> roles = new HashSet<>();
                Role userRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
                Address address = new Address(signUpRequest.getStreet(), signUpRequest.getCity(),
                        signUpRequest.getPostalCode(), signUpRequest.getZipCode(), signUpRequest.getCountry());
                //
                // admin user address
                Address adminAddress = new Address(signUpRequest.getAdminStreet(), signUpRequest.getAdminCity(),
                        signUpRequest.getAdminPostalCode(), signUpRequest.getAdminZipCode(), signUpRequest.getAdminCountry());

                User user1 = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
                        encoder.encode(signUpRequest.getPassword()), roles);
                user.add(user1);
                Branch branch = new Branch(signUpRequest.getBranchName(), address, user);

                //set admin user address and branch
                branchRepository.save(branch);
                user1.setAddress(adminAddress);
                user1.setBranchName(signUpRequest.getBranchName());

                userRepository.save(user1);

                return new ResponseEntity<>(branch, HttpStatus.OK);
            }
        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PreAuthorize("hasRole('HEAD_OFFICE')")
    @GetMapping("/branches")
    public ResponseEntity<List<Branch>> branches() {
        List<Branch> branches = branchRepository.findAll();
        return new ResponseEntity<>(branches, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('HEAD_OFFICE')")
    @GetMapping("/branch-managers")
    public ResponseEntity<List<User>> admins() {

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);

        List<User> admins = userRepository.findUsersByRoles(roles);


        return new ResponseEntity<>(admins, HttpStatus.OK);
    }


    @PreAuthorize("hasRole('HEAD_OFFICE')")
    @GetMapping("/total-customers")
    public int totalCustomer() {



        List<Customer> tellers = customerRepository.findAll();

        return tellers.size();
    }

    @PreAuthorize("hasRole('HEAD_OFFICE')")
    @GetMapping ("/total-withdrawal")
    public double totalWithdrawal() {



        List<Transaction> transactions = transactionRepository.findAll();

        double withdrawalAmount = 0.0;

        for (Transaction transaction : transactions){
            if (transaction.getType() == TransactionType.WITHDRAWL){
                withdrawalAmount += transaction.getAmount();
            }
        }

        return withdrawalAmount;
    }


    @PreAuthorize("hasRole('HEAD_OFFICE')")
    @GetMapping ("/total-deposit")
    public double totalDeposit() {

        List<Transaction> transactions = transactionRepository.findAll();

        double withdrawalAmount = 0.0;

        for (Transaction transaction : transactions){
            if (transaction.getType() == TransactionType.DEPOSIT){
                withdrawalAmount += transaction.getAmount();
            }
        }

        return withdrawalAmount;
    }



}
