package com.example.mpp;


import com.example.mpp.security.services.UserDetailsServiceImpl;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.Assert.assertEquals;

@DataMongoTest
@ExtendWith(SpringExtension.class)
public class MppApplicationTests {

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
