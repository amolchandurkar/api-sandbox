package com.example.sandbox;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.beans.factory.annotation.Autowired;
// ...existing code...
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc
public class PaymentApiSandboxApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testPaypalOrderSuccess() throws Exception {
    mockMvc.perform(
        org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/api/order/paypal")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"scenario\":\"success\"}")
    )
        .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk())
        .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.provider").value("paypal"))
        .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.amount").value(100.0));
    }

    @Test
    void testAlipayOrderPending() throws Exception {
    mockMvc.perform(
        org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/api/order/alipay")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"scenario\":\"pending\"}")
    )
        .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk())
        .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.provider").value("alipay"))
        .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.amount").value(75.0));
    }
}
