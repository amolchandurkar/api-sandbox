package com.example.sandbox.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @PostMapping("/paypal")
    public ResponseEntity<Map<String, Object>> createPaypalOrder(@RequestBody Map<String, Object> request) {
        double amount = getAmountForScenario(request, "paypal");
        return ResponseEntity.ok(Map.of(
            "provider", "paypal",
            "orderId", "PAYPAL-" + System.currentTimeMillis(),
            "amount", amount,
            "status", "CREATED"
        ));
    }

    @PostMapping("/alipay")
    public ResponseEntity<Map<String, Object>> createAlipayOrder(@RequestBody Map<String, Object> request) {
        double amount = getAmountForScenario(request, "alipay");
        return ResponseEntity.ok(Map.of(
            "provider", "alipay",
            "orderId", "ALIPAY-" + System.currentTimeMillis(),
            "amount", amount,
            "status", "CREATED"
        ));
    }

    private double getAmountForScenario(Map<String, Object> request, String provider) {
        String scenario = (String) request.getOrDefault("scenario", "default");
        // Simulate different amounts for different scenarios
        if (provider.equals("paypal")) {
            switch (scenario) {
                case "success": return 100.0;
                case "failure": return 0.0;
                case "pending": return 50.0;
                default: return 10.0;
            }
        } else if (provider.equals("alipay")) {
            switch (scenario) {
                case "success": return 200.0;
                case "failure": return 0.0;
                case "pending": return 75.0;
                default: return 20.0;
            }
        }
        return 0.0;
    }
}
