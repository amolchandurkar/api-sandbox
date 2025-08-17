
package com.example.sandbox.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import java.util.Map;
import com.example.sandbox.service.OrderSimulationService;

/**
 * OrderController exposes REST endpoints for simulating merchant payment order flows.
 *
 * Endpoints:
 *   - /api/order/paypal: Simulates PayPal order creation with scenario-based responses.
 *   - /api/order/alipay: Simulates Alipay order creation with scenario-based responses.
 *   - /api/order/auth: Simulates order authorization, including notification logic for special cases.
 *
 * This controller delegates business logic to OrderSimulationService, which further delegates to processor-specific services.
 *
 * Usage:
 *   Send a POST request with a JSON body containing scenario, amount, and other fields as needed.
 *
 * Example request:
 * {
 *   "scenario": "success",
 *   "amount": 100.0,
 *   "provider": "paypal"
 * }
 */
@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderSimulationService simulationService;

    /**
     * Simulate PayPal order creation.
     * @param request JSON payload with scenario and order details
     * @return ResponseEntity with simulated PayPal order response
     */
    @PostMapping("/paypal")
    public ResponseEntity<Map<String, Object>> createPaypalOrder(@RequestBody Map<String, Object> request) {
        double amount = simulationService.getAmountForScenario(request, "paypal");
        simulationService.maybeDelay(request);
        return ResponseEntity.ok(Map.of(
            "provider", "paypal",
            "orderId", "PAYPAL-" + System.currentTimeMillis(),
            "amount", amount,
            "status", "CREATED"
        ));
    }

    /**
     * Simulate Alipay order creation.
     * @param request JSON payload with scenario and order details
     * @return ResponseEntity with simulated Alipay order response
     */
    @PostMapping("/alipay")
    public ResponseEntity<Map<String, Object>> createAlipayOrder(@RequestBody Map<String, Object> request) {
        double amount = simulationService.getAmountForScenario(request, "alipay");
        simulationService.maybeDelay(request);
        return ResponseEntity.ok(Map.of(
            "provider", "alipay",
            "orderId", "ALIPAY-" + System.currentTimeMillis(),
            "amount", amount,
            "status", "CREATED"
        ));
    }

    /**
     * Simulate order authorization.
     * Includes notification logic for orders of amount 1000.
     * @param request JSON payload with order and auth details
     * @return ResponseEntity with simulated authorization response and optional notification
     */
    @PostMapping("/auth")
    public ResponseEntity<Map<String, Object>> authOrder(@RequestBody Map<String, Object> request) {
        simulationService.maybeDelay(request);
        return ResponseEntity.ok(simulationService.buildAuthResponse(request));
    }

    // Business logic moved to OrderSimulationService
}
