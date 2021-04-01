package com.example.mpp.repository;

import com.example.mpp.models.Account;
import com.example.mpp.models.AccountInfo;
import com.example.mpp.models.CheckingAccount;
import com.example.mpp.models.SavingAccount;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface AccountRepository extends MongoRepository<AccountInfo,Integer> {
    Optional<AccountInfo> findAccountInfoByAccountNumber(int account);
    boolean existsAccountInfoByAccountNumber(int accountNumber);
}
