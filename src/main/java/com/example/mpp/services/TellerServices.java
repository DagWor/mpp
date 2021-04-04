package com.example.mpp.services;

import com.example.mpp.models.AccountInfo;
import com.example.mpp.models.Transaction;
import com.example.mpp.models.TransactionType;
import com.example.mpp.models.User;
import com.example.mpp.payload.response.MessageResponse;
import com.example.mpp.repository.AccountRepository;
import com.example.mpp.repository.TransactionRepository;
import com.example.mpp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
@Service
public class TellerServices {

    @Autowired
    private  AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction withdrawalService(Transaction transaction){

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
                    transaction1.setBranchId(currentUser.getBranchName());
                    //withdraw money

                    account.get().setBalance(account.get().getBalance() - transaction.getAmount());
                    account.get().setCurrentDate(LocalDate.now());
                    accountRepository.save(account.get());
                    transactionRepository.save(transaction1);


                    // insert into account doc
                    //  accountServices.addTransactionToAccount(transaction.getToAccount(),transaction1);

                   /* Optional<AccountInfo> accountInfo = accountRepository.findAccountInfoByAccountNumber(transaction.getFromAccount());
                    if (accountInfo.isPresent()) {
                        List<Transaction> transactionList = accountInfo.get().getTransaction();
                        transactionList.add(transaction);
                        accountInfo.get().setTransaction(transactionList);
                    }*/
                    addTransactionToAccount(transaction.getToAccount(),transaction1);

                    return transaction;

                }

            } else {
                System.out.println("Account not found");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return transaction;
    }




public Transaction addTransactionToAccount(int accountNumber,Transaction transaction) {

    Optional<AccountInfo> accountInfo = accountRepository.findAccountInfoByAccountNumber(accountNumber);
    if (accountInfo.isPresent()) {
        List<Transaction> transactionList = accountInfo.get().getTransaction();
        transactionList.add(transaction);
        accountInfo.get().setTransaction(transactionList);
    }

    return transaction;
}
}