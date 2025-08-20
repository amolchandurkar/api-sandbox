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

## Extending

Add more scenarios or payment providers by updating the controller logic.

## Documentation

- All endpoints, payloads, and test scenarios are documented in the code and test files.
- Each test case includes comments explaining its purpose and logic.

For further details, see the source code and test files in the repository.

## API Testing & Architecture Validation

See [API-TESTING.md](./API-TESTING.md) for:

- API endpoint details
- How to test using Postman collection
- Unit test case instructions
- ArchUnit architectural test instructions
