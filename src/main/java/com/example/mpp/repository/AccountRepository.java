package com.example.mpp.repository;

import com.example.mpp.models.*;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;


public interface AccountRepository extends MongoRepository<AccountInfo,Integer> {
    Optional<AccountInfo> findAccountInfoByAccountNumber(int account);
    boolean existsAccountInfoByAccountNumber(int accountNumber);
   AccountInfo findAccountInfoByCustomerId(String customerId);
    Optional <AccountInfo> findAccountInfoByCustomerId(int customerId);
    List<AccountInfo> findAllByCustomerId(int customerId);
    List<AccountInfo> findAccountInfoByBranchName(String branchName);
    List<AccountInfo> findAllByCustomerId(String customerId);

}
