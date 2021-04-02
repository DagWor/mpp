package com.example.mpp.repository;

import com.example.mpp.models.Customer;
import com.example.mpp.models.Teller;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<Customer,Integer> {
    Customer findCustomerByUsername(String  username);
    Customer findCustomersByBranches(int branchId);
    Customer findCustomerById( String customerId);
}
