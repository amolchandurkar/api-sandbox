package com.example.sandbox.service;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaypalSimulationServiceTest {
    private final PaypalSimulationService service = new PaypalSimulationService();

    @Test
    void testGetAmountForScenarioSuccess() {
        Map<String, Object> req = new HashMap<>();
        req.put("scenario", "success");
        assertEquals(100.0, service.getAmountForScenario(req));
    }

    @Test
    void testGetAmountForScenarioFailure() {
        Map<String, Object> req = new HashMap<>();
        req.put("scenario", "failure");
        assertEquals(0.0, service.getAmountForScenario(req));
    }

    @Test
    void testGetAmountForScenarioPending() {
        Map<String, Object> req = new HashMap<>();
        req.put("scenario", "pending");
        assertEquals(50.0, service.getAmountForScenario(req));
    }

    @Test
    void testGetAmountForScenarioDefault() {
        Map<String, Object> req = new HashMap<>();
        req.put("scenario", "other");
        assertEquals(10.0, service.getAmountForScenario(req));
    }
}
