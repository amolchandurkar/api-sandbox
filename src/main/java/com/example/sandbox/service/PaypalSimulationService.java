package com.example.sandbox.service;

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
    /**
     * Get scenario-based amount for PayPal.
     * @param request JSON payload with scenario field
     * @return Simulated amount for the scenario
     */
    public double getAmountForScenario(Map<String, Object> request) {
        String scenario = (String) request.getOrDefault("scenario", "default");
        switch (scenario) {
            case "success": return 100.0;
            case "failure": return 0.0;
            case "pending": return 50.0;
            default: return 10.0;
        }
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
                TimeUnit.MILLISECONDS.sleep(delay);
            } catch (Exception ignored) {}
        }
    }
}
