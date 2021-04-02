package com.example.mpp.repository;

import com.example.mpp.models.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepositor extends MongoRepository<Customer,String> {


}
