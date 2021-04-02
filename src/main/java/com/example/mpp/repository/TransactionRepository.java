package com.example.mpp.repository;

import com.example.mpp.models.Account;
import com.example.mpp.models.AccountInfo;
import com.example.mpp.models.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface TransactionRepository extends MongoRepository<Transaction,Integer> {

    List<Transaction> findTransactionsByAccountInfo(AccountInfo accountNumber);

}
