# Voucher Management System

[![Build](https://github.com/simasch/voucher/actions/workflows/build.yml/badge.svg)](https://github.com/simasch/voucher/actions/workflows/build.yml)
[![codecov](https://codecov.io/gh/simasch/voucher/branch/main/graph/badge.svg)](https://codecov.io/gh/simasch/voucher)

A Spring Boot and Vaadin Flow application for managing vouchers.

## Features

- Create and manage vouchers
- Redeem vouchers
- Track voucher usage

## Development

### Prerequisites

- Java 21
- Maven
- Docker (for running tests with TestContainers)

### Running the Application

```bash
./mvnw spring-boot:run
```

The application will be available at `http://localhost:8080`

### Running Tests

```bash
./mvnw test
```

## License

This project is licensed under the MIT License.
