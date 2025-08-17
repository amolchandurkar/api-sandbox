package com.example.sandbox;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;


@SpringBootTest
@AutoConfigureMockMvc
public class PaymentApiSandboxApplicationTests {
    private WebTestClient webTestClient;

    @Autowired
    ApplicationContext context;

    @Autowired

    @Test
    void testPaypalOrderSuccess() {
        if (webTestClient == null) {
            webTestClient = WebTestClient.bindToApplicationContext(context).build();
        }
        webTestClient.post().uri("/api/order/paypal")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"scenario\":\"success\"}")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.provider").isEqualTo("paypal")
                .jsonPath("$.amount").isEqualTo(100.0);
    }

    @Test
    void testAlipayOrderPending() {
        if (webTestClient == null) {
            webTestClient = WebTestClient.bindToApplicationContext(context).build();
        }
        webTestClient.post().uri("/api/order/alipay")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"scenario\":\"pending\"}")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.provider").isEqualTo("alipay")
                .jsonPath("$.amount").isEqualTo(75.0);
    }
}
