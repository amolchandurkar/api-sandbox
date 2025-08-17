package com.example.sandbox.controller;

import com.example.sandbox.service.OrderSimulationService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderControllerTest {
    @Mock
    OrderSimulationService simulationService;
    @InjectMocks
    OrderController controller;

    public OrderControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test PayPal order creation endpoint.
     * Verifies that the controller delegates to the service and returns the correct provider and amount.
     */
    @Test
    void testCreatePaypalOrder() {
        Map<String, Object> req = new HashMap<>();
        // Simulate service returning amount for PayPal scenario
        when(simulationService.getAmountForScenario(req, "paypal")).thenReturn(100.0);
        // Simulate no delay for this request
        doNothing().when(simulationService).maybeDelay(req);
        // Call controller endpoint
        ResponseEntity<Map<String, Object>> resp = controller.createPaypalOrder(req);
        // Assert provider and amount in response
        assertEquals("paypal", resp.getBody().get("provider"));
        assertEquals(100.0, resp.getBody().get("amount"));
    }

    /**
     * Test Alipay order creation endpoint.
     * Verifies that the controller delegates to the service and returns the correct provider and amount.
     */
    @Test
    void testCreateAlipayOrder() {
        Map<String, Object> req = new HashMap<>();
        // Simulate service returning amount for Alipay scenario
        when(simulationService.getAmountForScenario(req, "alipay")).thenReturn(200.0);
        // Simulate no delay for this request
        doNothing().when(simulationService).maybeDelay(req);
        // Call controller endpoint
        ResponseEntity<Map<String, Object>> resp = controller.createAlipayOrder(req);
        // Assert provider and amount in response
        assertEquals("alipay", resp.getBody().get("provider"));
        assertEquals(200.0, resp.getBody().get("amount"));
    }

    /**
     * Test auth endpoint for notification scenario.
     * Verifies that the controller returns a notification when the service provides one.
     */
    @Test
    void testAuthOrderNotification() {
        Map<String, Object> req = new HashMap<>();
        // Simulate no delay for this request
        doNothing().when(simulationService).maybeDelay(req);
        // Simulate service returning a notification in the response
        Map<String, Object> result = new HashMap<>();
        result.put("notification", Map.of("type", "ORDER_AUTH_NOTIFICATION"));
        when(simulationService.buildAuthResponse(req)).thenReturn(result);
        // Call controller endpoint
        ResponseEntity<Map<String, Object>> resp = controller.authOrder(req);
        // Assert notification is present in response
        assertTrue(resp.getBody().containsKey("notification"));
    }

    /**
     * Test auth endpoint for normal scenario.
     * Verifies that the controller returns an authorized status when the service provides it.
     */
    @Test
    void testAuthOrderNormal() {
        Map<String, Object> req = new HashMap<>();
        // Simulate no delay for this request
        doNothing().when(simulationService).maybeDelay(req);
        // Simulate service returning authorized status
        Map<String, Object> result = new HashMap<>();
        result.put("status", "AUTHORIZED");
        when(simulationService.buildAuthResponse(req)).thenReturn(result);
        // Call controller endpoint
        ResponseEntity<Map<String, Object>> resp = controller.authOrder(req);
        // Assert status is authorized in response
        assertEquals("AUTHORIZED", resp.getBody().get("status"));
    }
}
