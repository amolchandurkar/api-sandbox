package com.example.sandbox.service;

import com.example.sandbox.util.SandboxConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(OrderSimulationService.class);
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
        logger.info(SandboxConstants.LOG_PREFIX + "Getting amount for provider: {} with request: {}", provider, request);
        if (provider.equals(SandboxConstants.PROVIDER_PAYPAL)) {
            double amt = paypalService.getAmountForScenario(request);
            logger.info(SandboxConstants.LOG_PREFIX + "PayPal scenario amount: {}", amt);
            return amt;
        } else if (provider.equals(SandboxConstants.PROVIDER_ALIPAY)) {
            double amt = alipayService.getAmountForScenario(request);
            logger.info(SandboxConstants.LOG_PREFIX + "Alipay scenario amount: {}", amt);
            return amt;
        }
        logger.warn(SandboxConstants.LOG_PREFIX + "Unknown provider: {}", provider);
        return 0.0;
    }

    /**
     * Simulate delayed response for edge cases.
     * Delegates to processor-specific service.
     * @param request JSON payload with delayMs field
     */
    public void maybeDelay(Map<String, Object> request) {
        String provider = request.getOrDefault("provider", SandboxConstants.PROVIDER_PAYPAL).toString();
        logger.info(SandboxConstants.LOG_PREFIX + "Checking for delay for provider: {} with request: {}", provider, request);
        if (provider.equals(SandboxConstants.PROVIDER_PAYPAL)) {
            paypalService.maybeDelay(request);
            logger.info(SandboxConstants.LOG_PREFIX + "Delay handled by PayPalSimulationService");
        } else if (provider.equals(SandboxConstants.PROVIDER_ALIPAY)) {
            alipayService.maybeDelay(request);
            logger.info(SandboxConstants.LOG_PREFIX + "Delay handled by AlipaySimulationService");
        } else {
            logger.warn(SandboxConstants.LOG_PREFIX + "Unknown provider for delay: {}", provider);
        }
    }

    /**
     * Build authorization response, including notification for amount 1000.
     * @param request JSON payload with order and auth details
     * @return Map with authorization response and optional notification
     */
    public Map<String, Object> buildAuthResponse(Map<String, Object> request) {
        logger.info(SandboxConstants.LOG_PREFIX + "Building auth response for request: {}", request);
        double amount = request.get("amount") != null ? Double.parseDouble(request.get("amount").toString()) : 0.0;
        Map<String, Object> response = Map.of(
            "provider", request.getOrDefault("provider", "unknown"),
            "orderId", request.getOrDefault("orderId", "AUTH-" + System.currentTimeMillis()),
            "amount", amount,
            "status", SandboxConstants.STATUS_AUTHORIZED
        );
        if (amount == 1000.0) {
            logger.info(SandboxConstants.LOG_PREFIX + "Order amount is 1000, adding notification to response.");
            return Map.of(
                "authResponse", response,
                "notification", Map.of(
                    "type", SandboxConstants.NOTIFICATION_TYPE,
                    "message", "Order of 1000 authorized, notification sent"
                )
            );
        }
        logger.info(SandboxConstants.LOG_PREFIX + "Returning normal auth response: {}", response);
        return response;
    }
}
