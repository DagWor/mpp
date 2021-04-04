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
    @Autowired
    private AuthServices authServices;
    @Autowired
    private TellerServices tellerServices;


    public Transaction depositService(Transaction transactionRequest){

      if (accountRepository.existsAccountInfoByAccountNumber(transactionRequest.getToAccount())) {
            Optional<AccountInfo> crAccount = accountRepository.
                    findAccountInfoByAccountNumber(transactionRequest.getToAccount());
            AccountInfo accountInfo = crAccount.get();

            accountInfo.setBalance(accountInfo.getBalance() + transactionRequest.getAmount());
            accountInfo.setCurrentDate(LocalDate.now());
            accountRepository.save(accountInfo);

         Transaction transactions1= saveTransaction(transactionRequest,TransactionType.DEPOSIT);
         addTransactionToAccount(transactionRequest.getToAccount(), transactions1);

            return transactions1;

        }
      return null;

}








    public Transaction withdrawalService(Transaction transaction) {


        if (accountRepository.existsAccountInfoByAccountNumber(transaction.getFromAccount())) {

            Optional<AccountInfo> account = accountRepository.findAccountInfoByAccountNumber(transaction.getFromAccount());
            if (account.isPresent() && account.get().getBalance() >= transaction.getAmount()) {
                Transaction transaction1 = new Transaction();
                Transaction transactions1= saveTransaction(transaction,TransactionType.WITHDRAWL);
                //withdraw money

                account.get().setBalance(account.get().getBalance() - transaction.getAmount());
                account.get().setCurrentDate(LocalDate.now());
                accountRepository.save(account.get());
                addTransactionToAccount(transaction.getFromAccount(), transaction1);
                return transaction;

            }


        }

        return null;
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

public Transaction saveTransaction(Transaction transaction,TransactionType transactionType) {
        if(transaction!=null && transactionType!=null){
    Transaction transactions1 = new Transaction();
    transactions1.setType(transactionType);
    transactions1.setAmount(transaction.getAmount());
    if(transactionType==TransactionType.DEPOSIT){
        transactions1.setToAccount(transaction.getToAccount());
    }else if(transactionType==TransactionType.WITHDRAWL){
        transactions1.setFromAccount(transaction.getFromAccount());
    }
    transactions1.setTransactionDate(LocalDate.now());
    String branchName = authServices.getCurrentUser().getBranchName();
    transactions1.setBranchId(branchName);
    transactionRepository.save(transactions1);
    return transactions1;
}
        return null;
}

}