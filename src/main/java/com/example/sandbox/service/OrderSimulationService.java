package com.example.sandbox.service;

import org.springframework.stereotype.Service;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * OrderSimulationService coordinates payment simulation logic for all providers.
 * <p>
 * Delegates scenario and delay handling to processor-specific services (PayPal, Alipay).
 * Handles authorization response and notification logic for special cases.
 * <p>
 * Usage:
 *   - getAmountForScenario: Returns scenario-based amount for a provider.
 *   - maybeDelay: Simulates delayed responses for edge cases.
 *   - buildAuthResponse: Returns authorization response, with notification for amount 1000.
 */
@Service
public class OrderSimulationService {
    @Autowired
    private PaypalSimulationService paypalService;
    @Autowired
    private AlipaySimulationService alipayService;

    /**
     * Get scenario-based amount for a payment provider.
     * @param request JSON payload with scenario and details
     * @param provider Payment provider (paypal, alipay)
     * @return Simulated amount for the scenario
     */
    public double getAmountForScenario(Map<String, Object> request, String provider) {
        if (provider.equals("paypal")) {
            return paypalService.getAmountForScenario(request);
        } else if (provider.equals("alipay")) {
            return alipayService.getAmountForScenario(request);
        }
        return 0.0;
    }

    /**
     * Simulate delayed response for edge cases.
     * Delegates to processor-specific service.
     * @param request JSON payload with delayMs field
     */
    public void maybeDelay(Map<String, Object> request) {
        if (request.getOrDefault("provider", "paypal").equals("paypal")) {
            paypalService.maybeDelay(request);
        } else if (request.getOrDefault("provider", "alipay").equals("alipay")) {
            alipayService.maybeDelay(request);
        }
    }

    /**
     * Build authorization response, including notification for amount 1000.
     * @param request JSON payload with order and auth details
     * @return Map with authorization response and optional notification
     */
    public Map<String, Object> buildAuthResponse(Map<String, Object> request) {
        double amount = request.get("amount") != null ? Double.parseDouble(request.get("amount").toString()) : 0.0;
        Map<String, Object> response = Map.of(
            "provider", request.getOrDefault("provider", "unknown"),
            "orderId", request.getOrDefault("orderId", "AUTH-" + System.currentTimeMillis()),
            "amount", amount,
            "status", "AUTHORIZED"
        );
        if (amount == 1000.0) {
            return Map.of(
                "authResponse", response,
                "notification", Map.of(
                    "type", "ORDER_AUTH_NOTIFICATION",
                    "message", "Order of 1000 authorized, notification sent"
                )
            );
        }
        return response;
    }
}
