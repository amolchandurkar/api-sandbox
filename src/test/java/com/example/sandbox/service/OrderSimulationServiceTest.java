package com.example.sandbox.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderSimulationServiceTest {
    @Mock
    PaypalSimulationService paypalService;
    @Mock
    AlipaySimulationService alipayService;
    @InjectMocks
    OrderSimulationService orderService;

    public OrderSimulationServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetScenarioForPaypal() {
        Map<String, Object> req = new HashMap<>();
        when(paypalService.getScenarioForAmount(req)).thenReturn("success");
        assertEquals("success", orderService.getScenarioForProvider(req, "paypal"));
    }

    @Test
    void testGetScenarioForAlipay() {
        Map<String, Object> req = new HashMap<>();
        when(alipayService.getScenarioForAmount(req)).thenReturn("failure");
        assertEquals("failure", orderService.getScenarioForProvider(req, "alipay"));
    }

    @Test
    void testBuildAuthResponseNotification() {
        Map<String, Object> req = new HashMap<>();
        req.put("amount", 1000.0);
        Map<String, Object> result = orderService.buildAuthResponse(req);
        assertTrue(result.containsKey("notification"));
    }

    @Test
    void testBuildAuthResponseNormal() {
        Map<String, Object> req = new HashMap<>();
        req.put("amount", 500.0);
        Map<String, Object> result = orderService.buildAuthResponse(req);
        assertFalse(result.containsKey("notification"));
    }
}
