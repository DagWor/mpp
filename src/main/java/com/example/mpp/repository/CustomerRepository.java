package com.example.mpp.repository;

import com.example.mpp.models.Customer;
import com.example.mpp.models.Teller;
import com.example.mpp.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CustomerRepository extends MongoRepository<Customer,Integer> {
    boolean existsCustomerByUser(User user);
    Customer findCustomerByUser(User user);
    List<Customer> findCustomerByBranchName(String branchName);
    Customer findCustomerById( String customerId);
}
