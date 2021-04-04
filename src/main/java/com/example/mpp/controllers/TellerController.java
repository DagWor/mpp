package com.example.mpp.controllers;

import com.example.mpp.models.*;

import com.example.mpp.payload.request.*;

import com.example.mpp.payload.request.SignupRequest;
import com.example.mpp.payload.request.WithdrawalRequest;
import com.example.mpp.payload.response.MessageResponse;
import com.example.mpp.repository.*;
import com.example.mpp.repository.resources.CurrentAccountNumberResource;
import com.example.mpp.repository.resources.UserWithIDRepository;
import com.example.mpp.resources.CurrentAccountNumber;
import com.example.mpp.resources.UserWithID;
import com.example.mpp.security.jwt.JwtUtils;
import com.example.mpp.services.AccountServices;
import com.example.mpp.services.TellerServices;
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
    @Autowired
    private TellerServices tellerServices;


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
                                , accountInfo.getCurrentDate(),customer.getId());
                        accountInfo1.setCurrentDate(LocalDate.now());
                        accountRepository.save(accountInfo1);
                        List<AccountInfo> accountList = customer.getAccount();

                        if (accountInfo.getType().equals("SAVING")) {
                            //super(accountNumber, balance,type, currentDate, customerId);
                            SavingAccount account = new SavingAccount(currentAccountNumber.get().getCurrentAccountNumber() + 1,
                                    accountInfo.getBalance(), "SAVING", LocalDate.now(),customer.getId());
                            accountList.add(account);
                            customer.setAccount(accountList);


                        } else {
                            CheckingAccount account = new CheckingAccount(currentAccountNumber.get().getCurrentAccountNumber() + 1,
                                    accountInfo.getBalance(), "CHECKING", LocalDate.now(), customer.getId());
                            accountList.add(account);
                            customer.setAccount(accountList);


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
                int accountNumber = currentAccountNumber.get().getCurrentAccountNumber() + 1;

                //currentAccountNumber.get().getCurrentAccountNumber()
                List<Transaction> transactions = new ArrayList<>();
                Transaction transactions1 = new Transaction();
                transactions1.setType(TransactionType.DEPOSIT);
                transactions1.setAmount(customerSignupRequest.getIntialAmount());
                transactions1.setFromAccount(accountNumber);
                transactions1.setTransactionDate(LocalDate.now());
                transactions.add(transactions1);
                transactionRepository.save(transactions1);

                AccountInfo accountInfo1 = new AccountInfo(accountNumber, customerSignupRequest.getIntialAmount(),
                        customerSignupRequest.getAccountTYpe()
                        , LocalDate.now(),customer.getId());
                accountInfo1.setCurrentDate(LocalDate.now());
                accountInfo1.setTransaction(transactions);
                accountInfo1.setBranchName(currentUser.getBranchName());
                accountRepository.save(accountInfo1);
                accountList.add(accountInfo1);
            //    customer.setAccount(accountList);
//                customerRepositor.save(customer);
//                accountInfo1.setCustomer(customer);

                //save to customer
                userWithIDRepository.save(new UserWithID(customer.getId(), user.getUsername()));


                // update current account number
                currentAccountNumber.get().setCurrentAccountNumber(currentAccountNumber.get().getCurrentAccountNumber() + 1);
                currentAccountNumberResource.save(currentAccountNumber.get());

                return new ResponseEntity<>("customer saved", HttpStatus.OK);

            }

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/deposit")
    @PreAuthorize("hasRole('TELLER')")
    public ResponseEntity<?> makeDeposit(@RequestBody Transaction transactionRequest) {
        if(tellerServices.depositService(transactionRequest)==null)
            return new ResponseEntity<>("deposit request is failed",HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(transactionRequest,HttpStatus.OK);

    }


    @PostMapping("/withdraw")
    @PreAuthorize("hasRole('TELLER')")
    public ResponseEntity<?> requestResponseEntity(@RequestBody Transaction transaction) {

      if(tellerServices.withdrawalService(transaction)==null)
           return new ResponseEntity<>("withdrawal request is failed",HttpStatus.BAD_REQUEST);

      return new ResponseEntity<>(transaction,HttpStatus.OK);

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
        List<Customer> customerList = customerRepositor.findCustomerByBranchName(currentUser.getBranchName());
        return new ResponseEntity<>(customerList, HttpStatus.OK);
    }

    @PostMapping("/listoftransaction")
    @PreAuthorize("hasRole('TELLER')")
    public ResponseEntity<List<Transaction>> listOfTransaction() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.findUserByUsername(auth.getName());
        List<Transaction> transactionList = transactionRepository.findTransactionByBranchName(currentUser.getBranchName());
        return new ResponseEntity<>(transactionList, HttpStatus.OK);
    }



    @PostMapping("/listofaccount")
    @PreAuthorize("hasRole('TELLER')")
    public ResponseEntity<List<AccountInfo>> listOfAccount() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.findUserByUsername(auth.getName());
        List<AccountInfo> accountInfoList = accountRepository.findAccountInfoByBranchName(currentUser.getBranchName());
        return new ResponseEntity<>(accountInfoList, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('TELLER')")
    @PostMapping("/transfer")
    public ResponseEntity<?>  transfer(@Valid @RequestBody Transaction transaction) {

        try {
            Optional<AccountInfo> toAccount = accountRepository.findAccountInfoByAccountNumber(transaction.getToAccount());
            Optional<AccountInfo> fromAccount = accountRepository.findAccountInfoByAccountNumber(transaction.getFromAccount());

            if (!accountRepository.existsAccountInfoByAccountNumber(transaction.getToAccount())) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("To account number is not found"));
            } else if (!accountRepository.existsAccountInfoByAccountNumber(transaction.getFromAccount())) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("From account number is not found"));
            } else if ((fromAccount.get().getAccountNumber()==toAccount.get().getAccountNumber())) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("check your account number"));
            } else if (!fromAccount.isPresent() && fromAccount.get().getBalance() <= transaction.getAmount()) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("not enough balance"));
            } else {
                AccountInfo toAccount1 = toAccount.get();
                AccountInfo fromAccount1 = fromAccount.get();


                fromAccount1.setBalance(fromAccount1.getBalance() - transaction.getAmount());
                fromAccount1.setCurrentDate(LocalDate.now());

                toAccount1.setBalance(toAccount1.getBalance() + transaction.getAmount());
                toAccount1.setCurrentDate(LocalDate.now());


                Transaction transaction1 = new Transaction();
                transaction1.setType(TransactionType.TRANSFER);
                transaction1.setAmount(transaction.getAmount());
                transaction1.setFromAccount(transaction.getFromAccount());
                transaction1.setToAccount(transaction.getToAccount());
                transaction1.setTransactionDate(LocalDate.now());
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                User currentUser = userRepository.findUserByUsername(auth.getName());
                transaction1.setBranchId(currentUser.getBranchName());

                transactionRepository.save(transaction1);

                //Optional<AccountInfo> accountInfo=accountRepository.findAccountInfoByAccountNumber(transaction.getToAccount());
                // if (accountInfo.isPresent()){

                List<Transaction> transactionList = toAccount.get().getTransaction();

                    transactionList.add(transaction1);
                    toAccount.get().setTransaction(transactionList);




                List<Transaction> transactionList1 = fromAccount.get().getTransaction();

                    transactionList1.add(transaction1);
                    fromAccount.get().setTransaction(transactionList1);


                accountRepository.save(fromAccount1);
                accountRepository.save(toAccount1);

                return new ResponseEntity<>(transaction, HttpStatus.OK);


            }


        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }



    }
}