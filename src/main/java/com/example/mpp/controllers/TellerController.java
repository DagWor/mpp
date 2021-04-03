package com.example.mpp.controllers;

import com.example.mpp.models.*;

import com.example.mpp.payload.request.*;

import com.example.mpp.payload.response.MessageResponse;
import com.example.mpp.repository.*;
import com.example.mpp.repository.resources.CurrentAccountNumberResource;
import com.example.mpp.repository.resources.UserWithIDRepository;
import com.example.mpp.resources.CurrentAccountNumber;
import com.example.mpp.resources.UserWithID;
import com.example.mpp.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;

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

    @Autowired
    DepositRepository depositRepository;
    @Autowired
    private UserWithIDRepository userWithIDRepository;
    @Autowired
    private CurrentAccountNumberResource currentAccountNumberResource;
    @Autowired
    private CustomerRepositor customerRepositor;

    @PreAuthorize("hasRole('TELLER')")
    @PostMapping("/create-account")
    public ResponseEntity<?> createAccount(@Valid @RequestBody CreateAccountRequest accountInfo){
        try{
             //   public AccountInfo( int accountNumber, double balance, AccountType type, LocalDate currentDate) {

          Optional<User> customerUser=userRepository.findByUsername(accountInfo.getUsername());
            if(customerUser.isPresent()){
                if(customerRepositor.existsCustomerByUser(customerUser.get())){
                    Customer customer=customerRepositor.findCustomerByUser(customerUser.get());
                    Optional<CurrentAccountNumber> currentAccountNumber=currentAccountNumberResource.findById("6064ccb32259ef7531409d04");
                    int accountNumber=currentAccountNumber.get().getCurrentAccountNumber()+1;
                    if(!accountRepository.existsAccountInfoByAccountNumber(accountNumber)){
                        AccountInfo accountInfo1=new AccountInfo(accountNumber, accountInfo.getBalance(),accountInfo.getType()
                                ,accountInfo.getCurrentDate());
                        accountInfo1.setCurrentDate(LocalDate.now());
                        accountRepository.save(accountInfo1);
                        List<Account> accountList=customer.getAccount();

                       if(accountInfo.getType().equals("SAVING")){
                            //super(accountNumber, balance,type, currentDate, customerId);
                            SavingAccount account=new SavingAccount(currentAccountNumber.get().getCurrentAccountNumber()+1,
                                    accountInfo.getBalance(),"SAVING",LocalDate.now(),customer);
                            accountList.add(account);
                            customer.setAccount(accountList);
                            account.setCustomer(customer);

                        } else {
                            CheckingAccount account=new CheckingAccount(currentAccountNumber.get().getCurrentAccountNumber()+1,
                                    accountInfo.getBalance(),"CHECKING",LocalDate.now(),customer);
                            accountList.add(account);
                            customer.setAccount(accountList);
                            account.setCustomer(customer);

                        }

                        accountRepository.save(accountInfo1);
                       customerRepositor.save(customer);

                    }


                }

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
    public ResponseEntity<?> registerCustomers(@Valid @RequestBody CustomerSignupRequest customerSignupRequest) {
        try {
            if (userRepository.existsByUsername(customerSignupRequest.getUsername())) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Username already taken"));
            }
            else
            if(userRepository.existsByEmail(customerSignupRequest.getEmail())){
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Email already taken"));
            }
            else {
                // find list of roles
                Set<Role> roles = new HashSet<>();

                Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

                roles.add(userRole);


                //Address(String street, String city, String postalCode, int zipCode, String country) {

                // create customer address
                Address address= new Address(customerSignupRequest.getStreet(),customerSignupRequest.getCity(),
                        customerSignupRequest.getPostalCode(),customerSignupRequest.getZipCode(),customerSignupRequest.getCountry() );
                //
                // create customer account number currentAccountNumberResource
                // Customer( Address address, User user)
                User user=new User(customerSignupRequest.getUsername(),customerSignupRequest.getEmail()
                        ,encoder.encode(customerSignupRequest.getPassword()),roles);

            userRepository.save(user);
                Customer customer=new Customer(user);
                List<Account> accountList=new ArrayList<>();


                    //CurrentAccountNumber currentAccountNumber1=new CurrentAccountNumber(10000);
               // currentAccountNumberResource.save(currentAccountNumber1);

                    Optional<CurrentAccountNumber> currentAccountNumber=currentAccountNumberResource.
                            findById("6067db4b7805514fd5a5f196");

                customerRepositor.save(customer);
                int accountNumber=currentAccountNumber.get().getCurrentAccountNumber()+1;

                //currentAccountNumber.get().getCurrentAccountNumber()
                if(customerSignupRequest.getAccountTYpe().equals("SAVING")){
                    //super(accountNumber, balance,type, currentDate, customerId);
                    AccountInfo accountInfo1=new AccountInfo(accountNumber, customerSignupRequest.getIntialAmount(),
                            customerSignupRequest.getAccountTYpe()
                            ,LocalDate.now());
                    accountInfo1.setCurrentDate(LocalDate.now());
                    accountInfo1.setCustomer(customer);
                    accountRepository.save(accountInfo1);
                    SavingAccount account=new SavingAccount(currentAccountNumber.get().getCurrentAccountNumber()+1,
                            customerSignupRequest.getIntialAmount(),"SAVING",LocalDate.now(),customer);
                    accountRepository.save(account);
                    accountList.add(account);
                    customer.setAccount(accountList);
                    account.setCustomer(customer);

                } else {

                    CheckingAccount account=new CheckingAccount(currentAccountNumber.get().getCurrentAccountNumber()+1,
                            customerSignupRequest.getIntialAmount(),"CHECKING",LocalDate.now(),customer);
                    accountRepository.save(account);
                    accountList.add(account);
                    customer.setAccount(accountList);
                    account.setCustomer(customer);

                }
                //   set branch for customer


                Authentication auth = SecurityContextHolder.getContext().getAuthentication();

                User currentUser=userRepository.findUserByUsername(auth.getName());
                user.setBranchName(currentUser.getBranchName());
                user.setAddress(address);


                userRepository.save(user);
                customer.setUser(user);
                customerRepositor.save(customer);
               //

                // update current account number
                currentAccountNumber.get().setCurrentAccountNumber(currentAccountNumber.get().getCurrentAccountNumber()+1);
                currentAccountNumberResource.save(currentAccountNumber.get());

                // save new account

                //  Optional<CurrentAccountNumber> currentAccountNumbers=currentAccountNumberResource.findById("6064ccb32259ef7531409d04");

                //customerRepositor.save(customer);
                return new ResponseEntity<>(customer,HttpStatus.OK);

            }

            } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }



/*







        @PreAuthorize("hasRole('TELLER')")
    @PostMapping("/create-customer")
    public ResponseEntity<?> registerCustomer(@Valid @RequestBody SignupRequest signUpRequest) {



        try {
            if (userRepository.existsByUsername(signUpRequest.getUsername())) {
                return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Username already taken"));
            }
            else
            if(userRepository.existsByEmail(signUpRequest.getEmail())){
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Email already taken"));
            }
            else {
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
                UserWithID userWithID=new UserWithID(_user.getId(), _user.getUsername());
                userWithIDRepository.save(userWithID);
//
//                if(!accountRepository.existsAccountInfoByAccountNumber(accountInfo.getAccountNumber())){
//                    accountInfo.setAccountNumber(1000);
//                    accountRepository.save(accountInfo);
//                }

                List<User> customers = branch.getCustomers();
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
*/

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

  /*  @GetMapping("/users/{id}")
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

*/
   /*

    @PostMapping("/deposit")
    @PreAuthorize("hasRole('TELLER')")
    public ResponseEntity<AccountInfo> makeDeposit(@RequestBody DepositRequest transactionRequest){
        try {
            Optional<AccountInfo> crAccount =accountRepository.findAccountInfoByAccountNumber(transactionRequest.getAccountNumber());
            if (crAccount.isPresent()) {
                AccountInfo accountInfo = crAccount.get();
                Account account=null;
                if(accountInfo.getType().equals("CHECKIN")) {
                    account = new CheckingAccount(transactionRequest.getAccountNumber(),
                            accountInfo.getBalance(),"CHECKIN",LocalDate.now(),accountInfo.getCustomerId());

                }
                else if(accountInfo.getType().equals("SAVING")){
                    account = new SavingAccount(transactionRequest.getAccountNumber(),accountInfo.getBalance(),"SAVING",LocalDate.now(),
                            accountInfo.getCustomerId());

                }
//
                if(account!=null){
                    account.getInterst();
                    accountInfo.setBalance(account.getBalance()+transactionRequest.getAmount());
                    accountInfo.setCurrentDate(LocalDate.now());
                    accountRepository.save(accountInfo);

                    DepositTransaction transactions = new DepositTransaction(LocalDate.now(),transactionRequest.getAmount(),transactionRequest.getAccountNumber(),123);
                    depositRepository.save(transactions);

                }

    //public DepositTransaction( LocalDate transactionDate, @NotBlank double amount, int toAccount, int branchId, TransactionType type) {


                //transation to be saved

                return new ResponseEntity<>(accountInfo,HttpStatus.OK);
            }
            else{ return  new ResponseEntity<>(HttpStatus.NO_CONTENT);}
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/withdraw")
    @PreAuthorize("hasRole('TELLER')")
    public void makeWithdrawal(@RequestBody WithdrawalRequest transaction){

        if(accountRepository.existsAccountInfoByAccountNumber(transaction.getAccountNumber())){

            Optional<AccountInfo> account= accountRepository.findAccountInfoByAccountNumber(transaction.getAccountNumber());
           if( account.isPresent() && account.get().getBalance()>=transaction.getAmount()){
               Transaction transaction1=new Transaction();
               transaction1.setType(TransactionType.WITHDRAWL);
               transaction1.setAmount(transaction.getAmount());
               transaction1.setFromAccount(transaction.getAccountNumber());
               transaction1.setTransactionDate(LocalDate.now());


               // find current teller
               Authentication auth = SecurityContextHolder.getContext().getAuthentication();
               User teller = userRepository.findUserByUsername(auth.getName());
               Branch branch = branchRepository.findBranchByTellersContains(teller);


               //branch.getBranchId()  check this line
               transaction1.setBranchId(123);

               account.get().setBalance(account.get().getBalance()- transaction.getAmount());
               account.get().setCurrentDate(LocalDate.now());
                accountRepository.save(account.get());
               transactionRepository.save(transaction1);

           }

        } else{
            System.out.println("Account not found");
        }


    }
*/
    @PostMapping("/transfer")
    @PreAuthorize("hasRole('TELLER')")
    public double totalDeposit(){
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
