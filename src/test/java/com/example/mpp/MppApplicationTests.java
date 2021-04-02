package com.example.mpp;


import com.example.mpp.controllers.TellerController;
import com.example.mpp.models.DepositTransaction;
import com.example.mpp.payload.request.DepositRequest;
import com.example.mpp.repository.DepositRepository;
import com.example.mpp.security.services.UserDetailsServiceImpl;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.STREAM;
import static org.mockito.Mockito.when;
//import static org.junit.Assert.assertEquals;

@DataMongoTest
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MppApplicationTests {


    @Autowired
    private TellerController tellerController;

    @MockBean
    private DepositRepository depositRepository;
  @Test
    public void depositTest(){
      //  when(depositRepository.findAll()).thenReturn(Stream.of(new DepositRequest()).collect()
    }








    //    @DisplayName("given object to save"
    //            + " when save object using MongoDB template"
    //            + " then object is saved")
    //    @Test
//    public void createUser(@Autowired MongoTemplate mongoTemplate) {
//        // given
//        DBObject objectToSave = BasicDBObjectBuilder.start()
//                .add("username", "test")
//                .add("email", "test@gmail.com")
//                .add("password", "123456789")
//                .get();
//
//        // when
//        mongoTemplate.save(objectToSave, "users");
//
//        // then
//        assertThat(mongoTemplate.findAll(DBObject.class, "users")).extracting("username")
//                .containsOnly("test");
//    }
}
