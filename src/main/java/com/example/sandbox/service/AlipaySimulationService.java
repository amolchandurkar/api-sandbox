package com.example.sandbox.service;

import com.example.sandbox.util.SandboxConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * AlipaySimulationService simulates Alipay payment scenarios and delayed responses.
 * <p>
 * Usage:
 *   - getAmountForScenario: Returns scenario-based amount for Alipay.
 *   - maybeDelay: Simulates delayed response if delayMs is provided.
 */
@Service
public class AlipaySimulationService {
    private static final Logger logger = LoggerFactory.getLogger(AlipaySimulationService.class);
    /**
     * Get scenario-based amount for Alipay.
     * @param request JSON payload with scenario field
     * @return Simulated amount for the scenario
     */
    public double getAmountForScenario(Map<String, Object> request) {
        String scenario = (String) request.getOrDefault("scenario", "default");
        logger.info(SandboxConstants.LOG_PREFIX + "Alipay scenario: {}", scenario);
        double amount;
        switch (scenario) {
            case "success": amount = 200.0; break;
            case "failure": amount = 0.0; break;
            case "pending": amount = 75.0; break;
            default: amount = 20.0; break;
        }
        logger.info(SandboxConstants.LOG_PREFIX + "Alipay scenario amount: {}", amount);
        return amount;
    }

    /**
     * Simulate delayed response for Alipay edge cases.
     * @param request JSON payload with delayMs field
     */
    public void maybeDelay(Map<String, Object> request) {
        Object delayObj = request.get("delayMs");
        if (delayObj != null) {
            try {
                long delay = Long.parseLong(delayObj.toString());
                logger.info(SandboxConstants.LOG_PREFIX + "Delaying Alipay response by {} ms", delay);
                TimeUnit.MILLISECONDS.sleep(delay);
            } catch (Exception e) {
                logger.warn(SandboxConstants.LOG_PREFIX + "Failed to delay Alipay response: {}", e.getMessage());
            }
        }
    }
}
