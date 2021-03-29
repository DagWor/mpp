package com.example.mpp.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.mpp.models.RoleType;
import com.example.mpp.models.Role;

public interface RoleRepository extends MongoRepository<Role, String> {
  Optional<Role> findByName(RoleType name);
}
