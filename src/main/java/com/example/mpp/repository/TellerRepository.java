package com.example.mpp.repository;

import com.example.mpp.models.Teller;
import com.example.mpp.models.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TellerRepository extends MongoRepository<Teller,Integer> {


}
