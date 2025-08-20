package com.example.sandbox.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderRequestTest {
    @Test
    void testOrderRequestFields() {
        OrderRequest req = new OrderRequest();
        req.scenario = "success";
        req.version = "v2";
        req.currency = "USD";
        req.amount = 123.45;
        req.merchantId = "M123";
        req.orderId = "O456";
        req.authToken = "token-abc";
        // Assert all fields are set correctly
        assertEquals("success", req.scenario);
        assertEquals("v2", req.version);
        assertEquals("USD", req.currency);
        assertEquals(123.45, req.amount);
        assertEquals("M123", req.merchantId);
        assertEquals("O456", req.orderId);
        assertEquals("token-abc", req.authToken);
    }

    @Test
    void testOrderRequestDefaultValues() {
        OrderRequest req = new OrderRequest();
        // Assert default values (null/0)
        assertNull(req.scenario);
        assertNull(req.version);
        assertNull(req.currency);
        assertEquals(0.0, req.amount);
        assertNull(req.merchantId);
        assertNull(req.orderId);
        assertNull(req.authToken);
    }
}
