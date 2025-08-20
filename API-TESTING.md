# API Testing & Architecture Validation Guide

## API Details

This sandbox simulates PayPal and Alipay order APIs for integration testing. It supports scenarios for different order amounts, delayed responses, and notifications.

### Endpoints

- `/api/order/paypal` — Simulate PayPal order
- `/api/order/alipay` — Simulate Alipay order
- `/api/order/auth` — Simulate order authorization
- `/api/order/capture` — Simulate order capture
- `/api/order/cancel` — Simulate order cancellation

Refer to the Postman collection for request/response examples.

## How to Test Using Postman

1. Import `Payment-API-Sandbox.postman_collection.json` into Postman.
2. Set the base URL to `http://localhost:8080` (or your configured port).
3. Use the provided requests to test PayPal, Alipay, and other endpoints.
4. Review responses for scenario simulation, delays, and notifications.

## Unit & Integration Testing

- **Unit tests** cover all service classes and controller logic using JUnit 5 and Mockito. Each scenario (success, failure, notification, delayed response) is validated.
- **Test files** are located in `src/test/java/com/example/sandbox/service/` and `src/test/java/com/example/sandbox/controller/`.
- Example test classes:
  - `PaypalSimulationServiceTest.java`: Validates PayPal business logic for all scenarios.
  - `AlipaySimulationServiceTest.java`: Validates Alipay business logic for all scenarios.
  - `OrderSimulationServiceTest.java`: Validates coordinator logic, notification, and delegation.
  - `OrderControllerTest.java`: Validates controller endpoints and response structure.

### How to Run Tests

  ```shell
  mvn clean test
  ```

If you are using Java 21, ensure you have the latest Mockito and Byte Buddy dependencies. If you encounter mocking errors, add the JVM property:

  ```shell
  mvn clean test -Dnet.bytebuddy.experimental=true
  ```

### Test Coverage

All business logic branches and controller endpoints are covered by unit tests. Each test case is documented for clarity.

## ArchUnit Test Cases

- Architecture rules are defined in `src/test/java/com/example/sandbox/architecture/ArchitectureTest.java`.
- Configuration is externalized in `src/test/resources/archunit.properties`.
- These tests ensure controllers, services, models, and loggers are in their correct packages.
- Run with:
  ```shell
  mvn test
  ```

## Additional Notes

- All configuration for architecture rules can be changed in `archunit.properties`.
- For more details, see the main README and source code documentation.
