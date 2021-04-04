package com.example.mpp.repository;

import java.util.Optional;
import java.util.Set;
import java.util.List;

import com.example.mpp.models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.mpp.models.User;

public interface UserRepository extends MongoRepository<User, String> {
  Optional<User> findByUsername(String username);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);

  User findUserByUsername(String username);

  List<User> findUserByBranchNameAndRoles(String branchName, Set<Role> roles);
}
