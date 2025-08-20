package com.example.sandbox.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import java.util.Map;
import com.example.sandbox.service.OrderSimulationService;
import com.example.sandbox.util.SandboxConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderSimulationService simulationService;

    /**
     * Simulate PayPal order creation.
     * @param request JSON payload with scenario and order details
     * @return ResponseEntity with simulated PayPal order response
     */
    @PostMapping("/paypal")
    public ResponseEntity<Map<String, Object>> createPaypalOrder(@RequestBody Map<String, Object> request) {
        logger.info(SandboxConstants.LOG_PREFIX + "Received PayPal order request: {}", request);
        String scenario = simulationService.getScenarioForProvider(request, SandboxConstants.PROVIDER_PAYPAL);
        logger.info(SandboxConstants.LOG_PREFIX + "Calculated PayPal scenario: {}", scenario);
        simulationService.maybeDelay(request);
        String orderId = "PAYPAL-" + System.currentTimeMillis();
        logger.info(SandboxConstants.LOG_PREFIX + "Generated PayPal orderId: {}", orderId);
        Map<String, Object> response = Map.of(
            "provider", SandboxConstants.PROVIDER_PAYPAL,
            "orderId", orderId,
            "scenario", scenario,
            "status", SandboxConstants.STATUS_CREATED
        );
        logger.info(SandboxConstants.LOG_PREFIX + "Responding to PayPal order request: {}", response);
        return ResponseEntity.ok(response);
    }

    /**
     * Simulate Alipay order creation.
     * @param request JSON payload with scenario and order details
     * @return ResponseEntity with simulated Alipay order response
     */
    @PostMapping("/alipay")
    public ResponseEntity<Map<String, Object>> createAlipayOrder(@RequestBody Map<String, Object> request) {
        logger.info(SandboxConstants.LOG_PREFIX + "Received Alipay order request: {}", request);
        String scenario = simulationService.getScenarioForProvider(request, SandboxConstants.PROVIDER_ALIPAY);
        logger.info(SandboxConstants.LOG_PREFIX + "Calculated Alipay scenario: {}", scenario);
        simulationService.maybeDelay(request);
        String orderId = "ALIPAY-" + System.currentTimeMillis();
        logger.info(SandboxConstants.LOG_PREFIX + "Generated Alipay orderId: {}", orderId);
        Map<String, Object> response = Map.of(
            "provider", SandboxConstants.PROVIDER_ALIPAY,
            "orderId", orderId,
            "scenario", scenario,
            "status", SandboxConstants.STATUS_CREATED
        );
        logger.info(SandboxConstants.LOG_PREFIX + "Responding to Alipay order request: {}", response);
        return ResponseEntity.ok(response);
    }

    /**
     * Simulate order authorization.
     * Includes notification logic for orders of amount 1000.
     * @param request JSON payload with order and auth details
     * @return ResponseEntity with simulated authorization response and optional notification
     */
    @PostMapping("/auth")
    public ResponseEntity<Map<String, Object>> authOrder(@RequestBody Map<String, Object> request) {
        logger.info(SandboxConstants.LOG_PREFIX + "Received auth order request: {}", request);
        simulationService.maybeDelay(request);
        Map<String, Object> response = simulationService.buildAuthResponse(request);
        logger.info(SandboxConstants.LOG_PREFIX + "Responding to auth order request: {}", response);
        return ResponseEntity.ok(response);
    }

    // Business logic moved to OrderSimulationService
}
