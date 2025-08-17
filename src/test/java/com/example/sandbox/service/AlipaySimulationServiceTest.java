package com.example.sandbox.service;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AlipaySimulationServiceTest {
    private final AlipaySimulationService service = new AlipaySimulationService();

    @Test
    void testGetAmountForScenarioSuccess() {
        Map<String, Object> req = new HashMap<>();
        req.put("scenario", "success");
        assertEquals(200.0, service.getAmountForScenario(req));
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
        assertEquals(75.0, service.getAmountForScenario(req));
    }

    @Test
    void testGetAmountForScenarioDefault() {
        Map<String, Object> req = new HashMap<>();
        req.put("scenario", "other");
        assertEquals(20.0, service.getAmountForScenario(req));
    }
}
