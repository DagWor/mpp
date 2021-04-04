package com.example.mpp.services;

import com.example.mpp.models.Role;
import com.example.mpp.models.User;
import com.example.mpp.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class AuthServicesTest {

    @InjectMocks
    private AuthServices authServices;

    @MockBean
    private UserRepository userRepository;



    @Test
    public void getCurrentUserTest(){
        String name="john";
            Set<Role> roles1=new HashSet<>();
            roles1.add(new Role());

        User user=new User("john","john@gmail.com","password",roles1);
        when(userRepository.findUserByUsername(name)).thenReturn(user);
        assertEquals(user,authServices.getCurrentUser());

    }

}