package com.example.mpp.controllers;


import com.example.mpp.models.Transaction;
import com.example.mpp.repository.*;
import com.example.mpp.services.TellerServices;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class TellerControllerTest {

    private MockMvc mockMvc;
 @InjectMocks
    TellerController tellerController;
 @InjectMocks
 private TellerServices tellerServices;

 @Mock
    private AccountRepository accountRepository;
 @Mock
 private TransactionRepository transactionRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void withdrawalServiceTest()
    {
     Transaction transaction =new Transaction();
     transaction.setFromAccount(1000);
     transaction.setAmount(505.02);

     //when(tellerServices.withdrawalService(transaction)).thenReturn(transaction);
        //tellerServices.withdrawalService(transaction);
        verify(transactionRepository, times(0)).save(transaction);



    }


/* @Before
    public void setup() throws Exception{
  mockMvc= MockMvcBuilders.standaloneSetup(tellerController).build();
 }
 @Test
    public void withdrawalTest() throws Exception{
   mockMvc.perform(MockMvcRequestBuilders.post("/api/teller/withdraw"))
           .andExpect(MockMvcResultMatchers.status().is(400));
 }*/









}