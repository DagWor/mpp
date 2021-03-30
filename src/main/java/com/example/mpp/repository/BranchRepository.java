package com.example.mpp.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.example.mpp.models.Branch;
import com.example.mpp.models.ERole;
import com.example.mpp.models.Role;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.mpp.models.User;

public interface BranchRepository extends MongoRepository<Branch, String> {
    Branch findBranchByAdmin(User user);
    Branch findBranchByAdmin_Email(String email);
}
