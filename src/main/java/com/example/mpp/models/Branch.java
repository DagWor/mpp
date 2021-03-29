package com.example.mpp.models;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "branch")
public class Branch {

    private int depositAmount = 98987;
    private List<User> users;

    public void addToBranch(User user){
        users.add(user);
    }

    public List<User> getBranchUsers(){
        return users;
    }

    public int getDepositAmuont() {
        return depositAmount;
    }
}
