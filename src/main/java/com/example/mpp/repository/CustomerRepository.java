package com.example.mpp.repository;

import com.example.mpp.models.Customer;
import com.example.mpp.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<Customer, String> {
    Boolean existsCustomerByUser(User user);
    Customer findCustomerByUser(User user);
    Customer findCustomerByUserUsername(String username);
}
