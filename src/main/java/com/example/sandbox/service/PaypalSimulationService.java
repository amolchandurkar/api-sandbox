package com.example.sandbox.service;

import com.example.sandbox.util.SandboxConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * PaypalSimulationService simulates PayPal payment scenarios and delayed responses.
 * <p>
 * Usage:
 *   - getAmountForScenario: Returns scenario-based amount for PayPal.
 *   - maybeDelay: Simulates delayed response if delayMs is provided.
 */
@Service
public class PaypalSimulationService {
    private static final Logger logger = LoggerFactory.getLogger(PaypalSimulationService.class);
    /**
     * Get scenario-based amount for PayPal.
     * @param request JSON payload with scenario field
     * @return Simulated amount for the scenario
     */
    public double getAmountForScenario(Map<String, Object> request) {
        String scenario = (String) request.getOrDefault("scenario", "default");
        logger.info(SandboxConstants.LOG_PREFIX + "PayPal scenario: {}", scenario);
        double amount;
        switch (scenario) {
            case "success": amount = 100.0; break;
            case "failure": amount = 0.0; break;
            case "pending": amount = 50.0; break;
            default: amount = 10.0; break;
        }
        logger.info(SandboxConstants.LOG_PREFIX + "PayPal scenario amount: {}", amount);
        return amount;
    }

    /**
     * Simulate delayed response for PayPal edge cases.
     * @param request JSON payload with delayMs field
     */
    public void maybeDelay(Map<String, Object> request) {
        Object delayObj = request.get("delayMs");
        if (delayObj != null) {
            try {
                long delay = Long.parseLong(delayObj.toString());
                logger.info(SandboxConstants.LOG_PREFIX + "Delaying PayPal response by {} ms", delay);
                TimeUnit.MILLISECONDS.sleep(delay);
            } catch (Exception e) {
                logger.warn(SandboxConstants.LOG_PREFIX + "Failed to delay PayPal response: {}", e.getMessage());
            }
        }
    }
}
