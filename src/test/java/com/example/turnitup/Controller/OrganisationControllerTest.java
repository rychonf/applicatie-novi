package com.example.turnitup.Controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrganisationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void getOrganisationWithRightId_ExpectOk() throws Exception{
        mockMvc
                .perform(get("/organisation/1002")
                        .with(user("rych")
                                .roles("ADMIN")))
                .andExpect(status().isOk());
    }

//    @Test
//    public void getOrganisationWithWrongId_ExpectNotFound() throws Exception{
//        mockMvc
//                .perform(get("/organisation/1004")
//                        .with(user("rych")
//                                .roles("ADMIN")))
//                .andExpect(status().isNotFound());
//    }
}