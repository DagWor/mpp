package com.example.mpp.controllers.apitest;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TellerController {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void withdrawTest_authorized_user_correct_account_and_amount ()throws Exception{

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/teller/withdraw")
                .header("Authorization", "Bearer " + "eyJhbGciOiJIUzUxMiJ9." +
                        "eyJzdWIiOiJ0ZWxsZXJ1c2VyIiwiaWF0IjoxNjE3NjExNDM4LCJleHAiOjE2MTc2OTc4Mzh9." +
                        "RtoqO5Jr2upth7lQqmweIniO0wdwf" +
                        "_iMchtbUcUIUk8xXqH6Ur-7O26xkEsuMSt-4BsRcC8_K_28Ff3RSzUKxg")
                .contentType(MediaType.APPLICATION_JSON).content("{\"fromAccount\": \"10018\",\"amount\": \"1000\"}"))
                .andExpect(status().isOk());

    }

    @Test
    public void withdrawTest_unauthorized_user_account_and_amount ()throws Exception{

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/teller/withdraw")
                .contentType(MediaType.APPLICATION_JSON).content("{\"fromAccount\": \"10018\",\"amount\": \"1000\"}"))
                .andExpect(status().isUnauthorized());

    }
    @Test
    public void withdrawTest_authorized_user_with_incorrect_account_or_amount ()throws Exception{

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/teller/withdraw")
                .header("Authorization", "Bearer " + "eyJhbGciOiJIUzUxMiJ9." +
                        "eyJzdWIiOiJ0ZWxsZXJ1c2VyIiwiaWF0IjoxNjE3NjExNDM4LCJleHAiOjE2MTc2OTc4Mzh9." +
                        "RtoqO5Jr2upth7lQqmweIniO0wdwf" +
                        "_iMchtbUcUIUk8xXqH6Ur-7O26xkEsuMSt-4BsRcC8_K_28Ff3RSzUKxg")
                .contentType(MediaType.APPLICATION_JSON).content("{\"fromAccount\": \"10012\",\"amount\": \"1000\"}"))
                .andExpect(status().isBadRequest());

    }


}
