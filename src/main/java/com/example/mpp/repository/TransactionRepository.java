package com.example.mpp.repository;

import com.example.mpp.models.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransactionRepository extends MongoRepository<Transaction,Integer> {


}
