package com.example.mpp.services;

import com.example.mpp.controllers.TellerController;
import com.example.mpp.models.*;
import com.example.mpp.repository.*;
import com.example.mpp.services.AuthServices;
import com.example.mpp.services.TellerServices;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class TellerServiceTest {
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
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void depositServiceTest(){
        Transaction transaction =new Transaction();
        // transaction.setFromAccount(10018);
        transaction.setToAccount(10018);
        transaction.setAmount(505.02);

        Set<Role> roleSet=new HashSet<>();

        Role role=new Role();
        role.setId("adjakdjnald");
        role.setName(ERole.ROLE_USER);
        roleSet.add(role);
        User user=new User("john","john@gmail.com","123456789",roleSet);
        User user1=new User("teller","teller@gmail.com","123456789",roleSet);
        user1.setBranchName("midwest");

        AccountInfo accountInfo=new AccountInfo(transaction.getToAccount(),transaction.getAmount(),
                "WITHDRAWAL", LocalDate.now(),"adkmals");
        when(accountRepository.existsAccountInfoByAccountNumber(transaction.getToAccount())).
                thenReturn(true);
        when(accountRepository.findAccountInfoByAccountNumber(transaction.getToAccount())).
                thenReturn(java.util.Optional.of(accountInfo));
        when(authServices.getCurrentUser()).thenReturn(user1);
        assertEquals(transaction,tellerServices.depositService(transaction));
    }


    @Test
    public void withdrawalServiceTest()
    {
        Transaction transaction =new Transaction();
        transaction.setFromAccount(10018);
//        transaction.setToAccount(10018);
        transaction.setAmount(505.02);

        Set<Role> roleSet=new HashSet<>();

        Role role=new Role();
        role.setId("adjakdjnald");
        role.setName(ERole.ROLE_USER);
        roleSet.add(role);
        User user=new User("john","john@gmail.com","123456789",roleSet);
        User user1=new User("teller","teller@gmail.com","123456789",roleSet);
        user1.setBranchName("midwest");

        AccountInfo accountInfo=new AccountInfo(transaction.getFromAccount(),transaction.getAmount(),
                "WITHDRAWAL", LocalDate.now(),"adkmals");
        when(accountRepository.existsAccountInfoByAccountNumber(transaction.getFromAccount())).
                thenReturn(true);
        when(accountRepository.findAccountInfoByAccountNumber(transaction.getFromAccount())).
                thenReturn(java.util.Optional.of(accountInfo));
        when(authServices.getCurrentUser()).thenReturn(user1);
        assertEquals(transaction,tellerServices.withdrawalService(transaction));
    }
    @Test
    public void saveTransactionTest(){
        Transaction transaction =new Transaction();
        transaction.setFromAccount(10018);
        transaction.setToAccount(10018);
        transaction.setAmount(505.02);


        Set<Role> roleSet=new HashSet<>();

        Role role=new Role();
        role.setId("adjakdjnald");
        role.setName(ERole.ROLE_TELLER);
        roleSet.add(role);
        User user1=new User("teller","teller@gmail.com","123456789",roleSet);
        user1.setBranchName("midwest");
        when(transactionRepository.save(transaction)).thenReturn(transaction);
        when(authServices.getCurrentUser()).thenReturn(user1);

        assertEquals(transaction,tellerServices.saveTransaction(transaction,TransactionType.DEPOSIT));

    }



}
