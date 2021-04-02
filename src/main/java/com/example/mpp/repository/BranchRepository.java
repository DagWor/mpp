package com.example.mpp.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.example.mpp.models.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BranchRepository extends MongoRepository<Branch, String> {
    Branch findBranchByAdmin(Admin email);
    Branch findBranchByTellersContains(User user);
}
