package com.example.mpp.services;

import com.example.mpp.controllers.TellerController;
import com.example.mpp.models.ERole;
import com.example.mpp.models.Role;
import com.example.mpp.models.User;
import com.example.mpp.repository.AccountRepository;
import com.example.mpp.repository.TransactionRepository;
import com.example.mpp.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

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

    private MockMvc mockMvc;
    @InjectMocks
    TellerController tellerController;
    @Autowired
    private TellerServices tellerServices;

    @MockBean
    private AuthServices authServices;
//@Autowired
//Transaction transaction;

    @MockBean
    private AccountRepository accountRepository;
    @MockBean
    private TransactionRepository transactionRepository;
    @MockBean
    private UserRepository userRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getCurrentUserTest(){
        String name="john";
            Set<Role> roles1=new HashSet<>();
            roles1.add(new Role());

        Set<Role> roleSet=new HashSet<>();
        Role role=new Role();
        role.setId("adjakdjnald");
        role.setName(ERole.ROLE_USER);
        roleSet.add(role);
       // User user=new User("john","john@gmail.com","password",roles1);
        User user1=new User("teller","teller@gmail.com","123456789",roleSet);
        user1.setBranchName("midwest");
        when(authServices.getCurrentUser()).thenReturn(user1);
        when(userRepository.findUserByUsername(user1.getUsername())).thenReturn(user1);
        assertEquals(user1,authServices.getCurrentUser());

    }



}