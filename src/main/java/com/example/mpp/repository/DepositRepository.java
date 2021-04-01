package com.example.mpp.repository;

import com.example.mpp.models.DepositTransaction;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DepositRepository extends MongoRepository<DepositTransaction,Integer> {


}
