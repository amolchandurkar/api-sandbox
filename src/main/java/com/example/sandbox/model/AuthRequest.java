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
public class AuthRequest {
    public String scenario;
    public String version;
    public String merchantId;
    public String orderId;
    public double amount;
    public String currency;
    public String payerId;
    public String authToken;
    // ... add more fields as needed for v1/v2
}
