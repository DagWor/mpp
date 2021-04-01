package com.example.mpp.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "User Access")
public class UserAccess {

    @Id
    private String id;
    private String username;
    private String password;
    private String customerId;
    private Role role;


}
