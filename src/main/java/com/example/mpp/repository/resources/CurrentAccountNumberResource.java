package com.example.mpp.repository.resources;

import com.example.mpp.resources.CurrentAccountNumber;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CurrentAccountNumberResource extends MongoRepository<CurrentAccountNumber,String> {
}
