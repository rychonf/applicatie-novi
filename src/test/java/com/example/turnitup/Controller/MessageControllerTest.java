package com.example.turnitup.Controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MessageControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser(roles = "ORGANISATION")
    void getAllMessagesWithRoleOrganisation() throws Exception {

        // Do the call with the organisation role
        mockMvc
                .perform(get("/message/list")
                        .with(user("tim")
                                .roles("ORGANISATION")))
                .andExpect(status().isForbidden());

        // Do the call with the dj role
        mockMvc
                .perform(get("/message/list")
                        .with(user("henk")
                                .roles("DJ")))
                .andExpect(status().isForbidden());

        // Do the call with the admin role
        mockMvc
                .perform(get("/message/list")
                        .with(user("rych")
                                .roles("ADMIN")))
                .andExpect(status().isOk());
    }

}