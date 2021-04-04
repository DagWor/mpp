package com.example.mpp.repository;

import com.example.mpp.models.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends MongoRepository<Transaction,Integer> {
          Transaction findTransactionsByTransactionId(String  id);
          Transaction findTransactionsByTransactionDate(LocalDate date);
          Transaction findTransactionsByBranchId(String id);
          Transaction findTransactionsByFromAccount(int account);
          Transaction findTransactionsByToAccount(int account);
          List<Transaction> findTransactionByBranchName(String branchName);

}
