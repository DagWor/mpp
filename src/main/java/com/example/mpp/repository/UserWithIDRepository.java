package com.example.mpp.repository;

import com.example.mpp.resources.UserWithID;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserWithIDRepository extends MongoRepository<UserWithID,String> {

}