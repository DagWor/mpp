package com.example.mpp.repository;

import com.example.mpp.models.UserAccess;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserAccessRepository extends MongoRepository<UserAccess,String> {

}
