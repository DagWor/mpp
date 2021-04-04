package com.example.mpp.controllers;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
public class TellerControllerTest {

    private MockMvc mockMvc;
 @InjectMocks
    TellerController tellerController;
 @Before
    public void setup() throws Exception{
  mockMvc= MockMvcBuilders.standaloneSetup(tellerController).build();
 }
 @Test
    public void withdrawalTest() throws Exception{
   mockMvc.perform(MockMvcRequestBuilders.post("/api/teller/withdraw"))
           .andExpect(MockMvcResultMatchers.status().is(400));
 }

 

}