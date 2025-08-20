package com.example.sandbox.service;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AlipaySimulationServiceTest {
    private final AlipaySimulationService service = new AlipaySimulationService();

    @Test
    void testGetScenarioForAmountSuccess10() {
        Map<String, Object> req = new HashMap<>();
        req.put("amount", 10.0);
        assertEquals("success", service.getScenarioForAmount(req));
    }

    @Test
    void testGetScenarioForAmountFailure20() {
        Map<String, Object> req = new HashMap<>();
        req.put("amount", 20.0);
        assertEquals("failure", service.getScenarioForAmount(req));
    }

    @Test
    void testGetScenarioForAmountPending30() {
        Map<String, Object> req = new HashMap<>();
        req.put("amount", 30.0);
        assertEquals("pending", service.getScenarioForAmount(req));
    }

    @Test
    void testGetScenarioForAmountOtherAmount() {
        Map<String, Object> req = new HashMap<>();
        req.put("amount", 99.0);
        assertEquals("success", service.getScenarioForAmount(req));
    }

    @Test
    void testGetScenarioForAmountDefaultAmount() {
        Map<String, Object> req = new HashMap<>();
        assertEquals("success", service.getScenarioForAmount(req));
    }
}
