package com.example.sandbox.model;

/**
 * AuthRequest represents the payload for order authorization requests.
 * <p>
 * Fields:
 *   - scenario: Test scenario (success, failure, etc.)
 *   - version: API version (v1, v2)
 *   - merchantId: Merchant identifier
 *   - orderId: Order identifier
 *   - amount: Order amount
 *   - currency: Currency code
 *   - payerId: Payer identifier
 *   - authToken: Authorization token
 *   - ...add more fields as needed for v1/v2
 */
// ...existing code...
import java.util.HashMap;
import java.util.Map;
// ...existing code...

public class AuthRequest {
    // Test scenario (for sandbox)
    public String scenario;
    // API version (v1, v2)
    public String version;

    // Merchant details
    public String merchantId;
    public String merchantName;
    public String merchantCountry;

    // Order details
    public String orderId;
    public double amount;
    public String currency;
    public String orderDescription;
    public String orderCreateTime;

    // Payer details
    public String payerId;
    public String payerEmail;
    public String payerPhone;
    public String payerCountry;

    // Payment method details
    public String paymentMethodType; // e.g. APM, CARD, WALLET
    public String paymentProvider;   // e.g. PayPal, Alipay
    public String paymentInstrumentId;

    // Device info
    public String deviceIp;
    public String deviceType;
    public String deviceUserAgent;

    // Authorization
    public String authToken;
    public String authType; // e.g. OTP, biometric

    // Optional metadata
    public String callbackUrl;
    public String customData;
    // ... add more fields as needed for v1/v2

    /**
     * Utility: Convert AuthRequest to Map for logging (external logger controls field selection)
     */
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("scenario", scenario);
        map.put("version", version);
        map.put("merchantId", merchantId);
        map.put("merchantName", merchantName);
        map.put("merchantCountry", merchantCountry);
        map.put("orderId", orderId);
        map.put("amount", amount);
        map.put("currency", currency);
        map.put("orderDescription", orderDescription);
        map.put("orderCreateTime", orderCreateTime);
        map.put("payerId", payerId);
        map.put("payerEmail", payerEmail);
        map.put("payerPhone", payerPhone);
        map.put("payerCountry", payerCountry);
        map.put("paymentMethodType", paymentMethodType);
        map.put("paymentProvider", paymentProvider);
        map.put("paymentInstrumentId", paymentInstrumentId);
        map.put("deviceIp", deviceIp);
        map.put("deviceType", deviceType);
        map.put("deviceUserAgent", deviceUserAgent);
        map.put("authToken", authToken);
        map.put("authType", authType);
        map.put("callbackUrl", callbackUrl);
        map.put("customData", customData);
        return map;
    }
}
