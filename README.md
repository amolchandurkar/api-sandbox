# Payment API Sandbox

This example project simulates merchant payment order APIs for PayPal and Alipay using Java 21, Spring Boot, and WireMock. It is designed for integration testing in a sandbox environment, allowing you to test payment flows without connecting to real payment providers.

## Features

- Simulated endpoints for PayPal and Alipay order creation
- Different order amounts based on scenario (success, failure, pending, etc.)
- Easily extendable for other payment providers

## Usage

1. Build and run the Spring Boot application.
2. Use `/api/order/paypal` and `/api/order/alipay` endpoints for integration testing.
3. Pass a JSON body with a `scenario` field to simulate different responses.

Example request:

```
POST /api/order/paypal
{
  "scenario": "success"
}
```

## Tech Stack

- Java 21
- Spring Boot
- WireMock
- Maven

## Running Tests

Run `mvn test` to execute all unit and integration tests.

### Testing Details

- **Unit tests** cover all service classes and controller logic using JUnit 5 and Mockito. Each scenario (success, failure, notification, delayed response) is validated.
- **Test files** are located in `src/test/java/com/example/sandbox/service/` and `src/test/java/com/example/sandbox/controller/`.
- Example test classes:
  - `PaypalSimulationServiceTest.java`: Validates PayPal business logic for all scenarios.
  - `AlipaySimulationServiceTest.java`: Validates Alipay business logic for all scenarios.
  - `OrderSimulationServiceTest.java`: Validates coordinator logic, notification, and delegation.
  - `OrderControllerTest.java`: Validates controller endpoints and response structure.

### How to Run Tests

```
mvn clean test
```

If you are using Java 21, ensure you have the latest Mockito and Byte Buddy dependencies. If you encounter mocking errors, add the JVM property:

```
mvn clean test -Dnet.bytebuddy.experimental=true
```

### Test Coverage

All business logic branches and controller endpoints are covered by unit tests. Each test case is documented for clarity.

## Extending

Add more scenarios or payment providers by updating the controller logic.

## Documentation

- All endpoints, payloads, and test scenarios are documented in the code and test files.
- Each test case includes comments explaining its purpose and logic.

For further details, see the source code and test files in the repository.
