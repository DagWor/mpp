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
import com.example.mpp.services.AccountServices;
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
    private CustomerRepository customerRepositor;
    @Autowired
    private AccountServices accountServices;

    @PreAuthorize("hasRole('TELLER')")
    @PostMapping("/create-account")
    public ResponseEntity<?> createAccount(@Valid @RequestBody CreateAccountRequest accountInfo) {
        try {
            //   public AccountInfo( int accountNumber, double balance, AccountType type, LocalDate currentDate) {

            Optional<User> customerUser = userRepository.findByUsername(accountInfo.getUsername());
            if (customerUser.isPresent()) {
                if (customerRepositor.existsCustomerByUser(customerUser.get())) {
                    Customer customer = customerRepositor.findCustomerByUser(customerUser.get());
                    Optional<CurrentAccountNumber> currentAccountNumber = currentAccountNumberResource.findById("6064ccb32259ef7531409d04");
                    int accountNumber = currentAccountNumber.get().getCurrentAccountNumber() + 1;
                    if (!accountRepository.existsAccountInfoByAccountNumber(accountNumber)) {
                        AccountInfo accountInfo1 = new AccountInfo(accountNumber, accountInfo.getBalance(), accountInfo.getType()
                                , accountInfo.getCurrentDate());
                        accountInfo1.setCurrentDate(LocalDate.now());
                        accountRepository.save(accountInfo1);
                        List<AccountInfo> accountList = customer.getAccount();

                        if (accountInfo.getType().equals("SAVING")) {
                            //super(accountNumber, balance,type, currentDate, customerId);
                            SavingAccount account = new SavingAccount(currentAccountNumber.get().getCurrentAccountNumber() + 1,
                                    accountInfo.getBalance(), "SAVING", LocalDate.now(), customer);
                            accountList.add(account);
                            customer.setAccount(accountList);
                            account.setCustomer(customer);

                        } else {
                            CheckingAccount account = new CheckingAccount(currentAccountNumber.get().getCurrentAccountNumber() + 1,
                                    accountInfo.getBalance(), "CHECKING", LocalDate.now(), customer);
                            accountList.add(account);
                            customer.setAccount(accountList);
                            account.setCustomer(customer);

                        }

                        accountRepository.save(accountInfo1);
                        customerRepositor.save(customer);

                    }


                }

            } else {

            }


            return new ResponseEntity<>(accountInfo, HttpStatus.CREATED);
        } catch (Exception e) {
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
            } else if (userRepository.existsByEmail(customerSignupRequest.getEmail())) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Email already taken"));
            } else {
                // find list of roles
                Set<Role> roles = new HashSet<>();

                Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

                roles.add(userRole);


                //Address(String street, String city, String postalCode, int zipCode, String country) {

                // create customer address
                Address address = new Address(customerSignupRequest.getStreet(), customerSignupRequest.getCity(),
                        customerSignupRequest.getPostalCode(), customerSignupRequest.getZipCode(), customerSignupRequest.getCountry());
                //
                // create customer account number currentAccountNumberResource
                // Customer( Address address, User user)
                User user = new User(customerSignupRequest.getUsername(), customerSignupRequest.getEmail()
                        , encoder.encode(customerSignupRequest.getPassword()), roles);
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();

                User currentUser = userRepository.findUserByUsername(auth.getName());
                user.setBranchName(currentUser.getBranchName());
                user.setAddress(address);
                userRepository.save(user);
                Customer customer = new Customer(user);
                customer.setBranchName(currentUser.getBranchName());
                List<AccountInfo> accountList = new ArrayList<>();


                //CurrentAccountNumber currentAccountNumber1=new CurrentAccountNumber(10000);
                // currentAccountNumberResource.save(currentAccountNumber1);

                Optional<CurrentAccountNumber> currentAccountNumber = currentAccountNumberResource.
                        findById("6067db4b7805514fd5a5f196");


                //currentAccountNumber.get().getCurrentAccountNumber()
                List<Transaction> transactions=null;
                int accountNumber = currentAccountNumber.get().getCurrentAccountNumber() + 1;
                AccountInfo accountInfo1 = new AccountInfo(accountNumber, customerSignupRequest.getIntialAmount(),
                        customerSignupRequest.getAccountTYpe()
                        , LocalDate.now());
                accountInfo1.setCurrentDate(LocalDate.now());
                accountInfo1.setTransaction(transactions);
                accountInfo1.setBranchName(currentUser.getBranchName());
                accountRepository.save(accountInfo1);
                accountList.add(accountInfo1);
                customer.setAccount(accountList);
                customerRepositor.save(customer);
                accountInfo1.setCustomer(customer);

                //save to customer
                userWithIDRepository.save(new UserWithID(customer.getId(), user.getUsername()));


                // update current account number
                currentAccountNumber.get().setCurrentAccountNumber(currentAccountNumber.get().getCurrentAccountNumber() + 1);
                currentAccountNumberResource.save(currentAccountNumber.get());

                return new ResponseEntity<>(customer, HttpStatus.OK);

            }

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


//    @PreAuthorize("hasRole('TELLER')")
//    @PostMapping("/find-all-customer")
//    public ResponseEntity<?> findAllCustomer() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        User currentUser = userRepository.findUserByUsername(auth.getName());
//
//    }





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


    @PostMapping("/deposit")
    @PreAuthorize("hasRole('TELLER')")
    public ResponseEntity<?> makeDeposit(@RequestBody Transaction transactionRequest) {
        try {
            Optional<AccountInfo> crAccount = accountRepository.
                    findAccountInfoByAccountNumber(transactionRequest.getToAccount());
            Random r = new Random();
            int low = 1000;
            int high = 1000000;
            int result = r.nextInt(high-low) + low;

            if (crAccount.isPresent()) {
                AccountInfo accountInfo = crAccount.get();

                    accountInfo.setBalance(accountInfo.getBalance() + transactionRequest.getAmount());
                    accountInfo.setCurrentDate(LocalDate.now());
                    accountRepository.save(accountInfo);


                    Transaction transactions1 = new Transaction();
                    transactions1.setType(TransactionType.DEPOSIT);
                    transactions1.setAmount(transactionRequest.getAmount());
                    transactions1.setFromAccount(transactionRequest.getToAccount());
                    transactions1.setTransactionDate(LocalDate.now());


                    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                    User currentUser = userRepository.findUserByUsername(auth.getName());

                    // find current teller
                    //branch.getBranchId()  check this line
                    transactions1.setBranchId(currentUser.getBranchName());
                    transactionRepository.save(transactions1);

                    // insert into account doc
                   // accountServices.addTransactionToAccount(transactionRequest.getToAccount(),transactions1);
                Optional<AccountInfo> accountInfo1=accountRepository.findAccountInfoByAccountNumber(transactionRequest.getFromAccount());
                if (accountInfo1.isPresent()){
                    List<Transaction> transactionList=accountInfo1.get().getTransaction();
                    transactionList.add(transactionRequest);
                    accountInfo1.get().setTransaction(transactionList);
                }

                }

                return new ResponseEntity<>(transactionRequest, HttpStatus.OK);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/withdraw")
    @PreAuthorize("hasRole('TELLER')")
    public ResponseEntity<Transaction> requestResponseEntity(@RequestBody Transaction transaction) {
        try {
            if (accountRepository.existsAccountInfoByAccountNumber(transaction.getFromAccount())) {

                Optional<AccountInfo> account = accountRepository.findAccountInfoByAccountNumber(transaction.getFromAccount());
                if (account.isPresent() && account.get().getBalance() >= transaction.getAmount()) {
                    Transaction transaction1 = new Transaction();
                    transaction1.setType(TransactionType.WITHDRAWL);
                    transaction1.setAmount(transaction.getAmount());
                    transaction1.setFromAccount(transaction.getFromAccount());
                    transaction1.setTransactionDate(LocalDate.now());
                    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

                    User currentUser = userRepository.findUserByUsername(auth.getName());

                    // find current teller
                    //branch.getBranchId()  check this line
                    transaction1.setBranchId(currentUser.getBranchName());
                    //withdraw money

                    account.get().setBalance(account.get().getBalance() - transaction.getAmount());
                    account.get().setCurrentDate(LocalDate.now());
                    accountRepository.save(account.get());
                    transactionRepository.save(transaction1);


                    // insert into account doc
                    //  accountServices.addTransactionToAccount(transaction.getToAccount(),transaction1);

                    Optional<AccountInfo> accountInfo=accountRepository.findAccountInfoByAccountNumber(transaction.getFromAccount());
                    if (accountInfo.isPresent()){
                        List<Transaction> transactionList=accountInfo.get().getTransaction();
                        transactionList.add(transaction);
                        accountInfo.get().setTransaction(transactionList);
                    }


                    return new ResponseEntity<Transaction>(transaction, HttpStatus.OK);

                }

            } else {
                System.out.println("Account not found");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<Transaction>(HttpStatus.INTERNAL_SERVER_ERROR);

    }

//    @PostMapping("/findallcustomer")
//    @PreAuthorize("hasRole('TELLER')")
//    public ResponseEntity<Transaction> requestResponseEntity(){
//
//    }


    @PostMapping("/transfer")
    @PreAuthorize("hasRole('TELLER')")
    public double totalDeposit() {
        return 0.0;
    }

    @GetMapping("/balance")
    @PreAuthorize("hasRole('TELLER')")
    public double viewBalance() {
        return 0.0;
    }

    @PostMapping("/listOfCustomer")
    @PreAuthorize("hasRole('TELLER')")
    public ResponseEntity<List<Customer>> listOfCustomer() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.findUserByUsername(auth.getName());
        List<Customer> customerList=customerRepositor.findCustomerByBranchName(currentUser.getBranchName());
      return new ResponseEntity<>(customerList,HttpStatus.OK);
    }

    @PostMapping("/listoftransaction")
    @PreAuthorize("hasRole('TELLER')")
    public ResponseEntity<List<Transaction>> listOfTransaction() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.findUserByUsername(auth.getName());
        List<Transaction> transactionList=transactionRepository.findTransactionByBranchName(currentUser.getBranchName());
        return new ResponseEntity<>(transactionList,HttpStatus.OK);
    }

    @PostMapping("/listofaccount")
    @PreAuthorize("hasRole('TELLER')")
    public ResponseEntity<List<AccountInfo>> listOfAccount() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.findUserByUsername(auth.getName());
        List<AccountInfo> accountInfoList=accountRepository.findAccountInfoByBranchName(currentUser.getBranchName());
        return new ResponseEntity<>(accountInfoList,HttpStatus.OK);
    }




}
