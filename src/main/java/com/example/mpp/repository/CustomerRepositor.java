package com.example.mpp.repository;

import com.example.mpp.models.Customer;
import com.example.mpp.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepositor extends MongoRepository<Customer,String> {

    boolean existsCustomerByUser(User user);
    Customer findCustomerByUser(User user);

}
