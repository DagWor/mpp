package com.example.mpp.repository.resources;

import com.example.mpp.resources.UserWithID;
import org.apache.catalina.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserWithIDRepository extends MongoRepository<UserWithID,String> {
    UserWithID findUserWithIDByUsername(String usernam);
}
