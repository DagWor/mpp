package com.example.mpp.repository;

import com.example.mpp.models.Admin;
import com.example.mpp.models.Teller;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdminRepository extends MongoRepository<Admin,Integer> {
    Admin findAdminByUsername(String username);
    Admin findAdminByBranch(int branchId);
}
