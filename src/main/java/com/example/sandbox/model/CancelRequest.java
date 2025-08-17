package com.example.sandbox.model;

/**
 * CancelRequest represents the payload for order cancellation requests.
 * <p>
 * Fields:
 *   - scenario: Test scenario (success, failure, etc.)
 *   - version: API version (v1, v2)
 *   - merchantId: Merchant identifier
 *   - orderId: Order identifier
 *   - cancelReason: Reason for cancellation
 *   - authToken: Authorization token
 *   - ...add more fields as needed for v1/v2
 */
public class CancelRequest {
    public String scenario;
    public String version;
    public String merchantId;
    public String orderId;
    public String cancelReason;
    public String authToken;
    // ... add more fields as needed for v1/v2
}
