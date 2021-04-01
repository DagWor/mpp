package com.example.mpp.controllers;

import com.example.mpp.models.*;
import com.example.mpp.payload.request.LoginRequest;
import com.example.mpp.payload.request.SignupRequest;
import com.example.mpp.payload.response.JwtResponse;
import com.example.mpp.payload.response.MessageResponse;
import com.example.mpp.repository.*;
import com.example.mpp.security.jwt.JwtUtils;
import com.example.mpp.security.services.UserDetailsImpl;
import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/teller")
public class TellerController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    BranchRepository branchRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
   private AccountRepository accountRepository;
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    private UserAccessRepository userAccessRepository;

    @PreAuthorize("hasRole('TELLER')")
    @PostMapping("/create-account")
    public ResponseEntity<?> createAccount(@Valid @RequestBody AccountInfo accountInfo){
        try{
             //   public AccountInfo( int accountNumber, double balance, AccountType type, LocalDate currentDate) {

             if(!accountRepository.existsAccountInfoByAccountNumber(accountInfo.getAccountNumber())){
                  AccountInfo accountInfo1=new AccountInfo(accountInfo.getAccountNumber(), accountInfo.getBalance(),accountInfo.getType()   ,accountInfo.getCurrentDate(),accountInfo.getCustomerId());
                 accountInfo1.setCurrentDate(LocalDate.now());
                 accountRepository.save(accountInfo1);
             }
             else{

             }


                return new ResponseEntity<>(accountInfo, HttpStatus.CREATED);
        }
    catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }
    @PreAuthorize("hasRole('TELLER')")
    @PostMapping("/create-customer")
    public ResponseEntity<?> registerCustomer(@Valid @RequestBody SignupRequest signUpRequest,@RequestBody AccountInfo accountInfo) {



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
                Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                User teller = userRepository.findUserByUsername(auth.getName());
                Branch branch = branchRepository.findBranchByTellersContains(teller);


                User _user = userRepository.save(new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
                        encoder.encode(signUpRequest.getPassword())));
                _user.setRoles(roles);

                userRepository.save(_user);
                ///  create account



                if(!accountRepository.existsAccountInfoByAccountNumber(accountInfo.getAccountNumber())){
                    accountInfo.setAccountNumber(1000);
                    accountRepository.save(accountInfo);
                }

                List<User> customers = new ArrayList<>();
                customers = branch.getCustomers();
                customers.add(_user);
                branch.setCustomers(customers);
                branchRepository.save(branch);
                return new ResponseEntity<>(_user, HttpStatus.CREATED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/customers/{id}")
    @PreAuthorize("hasRole('TELLER') or hasRole('ADMIN') or hasRole('HEAD_OFFICE')")
    public ResponseEntity<List<User>> getAllBranchCustomers(@PathVariable("id") String id) {
        ResponseEntity<List<User>> result;
        try {

            Optional<Branch> branch = branchRepository.findById(id);
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

            Branch branch1 = branch.get();
            List<User> users = branch1.getBranchCustomers();


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

    /*@GetMapping("/customers")
    @PreAuthorize("hasRole('TELLER')")
    public ResponseEntity<List<User>> getAllBranchCustomers(@ReBranch branch){
        try{

            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

            List<User> users = new ArrayList<>(userRepository.findAllByRolesContainsAndAndBranch(userRole, branch));


            if(users.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(users, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getCustomerById(@PathVariable("id") String id) {
        Optional<User> userData = userRepository.findById(id);

        return userData.map(user -> new ResponseEntity<>(user, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/customers/{id}")
    public ResponseEntity<User> updateCustomer(@PathVariable("id") String id, @RequestBody User newData) {
        Optional<User> userData = userRepository.findById(id);

        if (userData.isPresent()) {
            User _user = userData.get();
            _user.setUsername(newData.getUsername());
            _user.setEmail(newData.getEmail());
            _user.setPassword(newData.getPassword());
            _user.setRoles(newData.getRoles());
            return new ResponseEntity<>(userRepository.save(_user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/deposit")
    @PreAuthorize("hasRole('TELLER')")
    public void makeDeposit(double amount){

    }

    @PostMapping("/withdraw")
    @PreAuthorize("hasRole('TELLER')")
    public void makeWithdrawal(@RequestBody Transaction transaction){
        if(accountRepository.existsAccountInfoByAccountNumber(transaction.getFromAccount())){

            AccountInfo account= accountRepository.findAccountInfoByAccountNumber(transaction.getFromAccount());
           if(account.getBalance()>=transaction.getAmount()){
               Transaction transaction1=new Transaction();
               transaction1.setType(TransactionType.WITHDRAWL);
               transaction1.setAmount(transaction.getAmount());
               transaction1.setFromAccount(transaction.getFromAccount());
               transaction1.setTransactionDate(LocalDate.now());


               // find current teller
               Authentication auth = SecurityContextHolder.getContext().getAuthentication();
               User teller = userRepository.findUserByUsername(auth.getName());
               Branch branch = branchRepository.findBranchByTellersContains(teller);


               //branch.getBranchId()  check this line
               transaction1.setBranchId(123);

               account.setBalance(account.getBalance()- transaction.getAmount());
               account.setCurrentDate(LocalDate.now());
                accountRepository.save(account);
               transactionRepository.save(transaction1);

           }

        } else{
            System.out.println("Account not found");
        }


    }

    @PostMapping("/transfer")
    @PreAuthorize("hasRole('TELLER')")
    public double totalDeposit(){
//        Branch b = new Branch();
//        return b.getDepositAmuont();
        return 0.0;
    }

    @GetMapping("/balance")
    @PreAuthorize("hasRole('TELLER')")
    public double viewBalance(){
        return 0.0;
    }

    @PostMapping("/activate-customer")
    @PreAuthorize("hasRole('TELLER')")
    public void createCustomer(){

    }
}
