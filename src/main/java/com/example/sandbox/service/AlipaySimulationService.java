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
 *   - getScenarioForAmount: Returns scenario string for Alipay based on amount.
 *   - maybeDelay: Simulates delayed response if delayMs is provided.
 */
@Service
public class AlipaySimulationService {
    private static final Logger logger = LoggerFactory.getLogger(AlipaySimulationService.class);
    /**
     * Get scenario string for Alipay based on amount.
     * @param request JSON payload with amount field
     * @return Scenario string (10=success, 20=failure, 30=pending, other=success)
     */
    public String getScenarioForAmount(Map<String, Object> request) {
        double amount = 10.0;
        if (request.containsKey("amount")) {
            Object amtObj = request.get("amount");
            try {
                amount = Double.parseDouble(amtObj.toString());
            } catch (Exception e) {
                logger.warn(SandboxConstants.LOG_PREFIX + "Invalid amount, defaulting to 10.0: {}", amtObj);
            }
        }
        String scenario;
        if (amount == 10.0) {
            scenario = "success";
        } else if (amount == 20.0) {
            scenario = "failure";
        } else if (amount == 30.0) {
            scenario = "pending";
        } else {
            scenario = "success";
        }
        logger.info(SandboxConstants.LOG_PREFIX + "Alipay scenario for amount {}: {}", amount, scenario);
        return scenario;
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
