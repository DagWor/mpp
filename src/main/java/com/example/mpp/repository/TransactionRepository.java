package com.example.mpp.repository;

import com.example.mpp.models.Customer;
import com.example.mpp.models.AccountInfo;
import com.example.mpp.models.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

import java.time.LocalDate;

public interface TransactionRepository extends MongoRepository<Transaction,Integer> {
          Transaction findTransactionsByTransactionId(String  id);
          Transaction findTransactionsByTransactionDate(LocalDate date);
          Transaction findTransactionsByBranchId(String id);
          Transaction findTransactionsByFromAccount(int account);
          Transaction findTransactionsByToAccount(int account);

}
