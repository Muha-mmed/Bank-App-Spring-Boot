# Bank-App-Spring-Boot

A Spring Boot application for simple bank account management (create accounts, deposit, withdraw, and transfer funds).

---

# Requirements

· Java 17+
· Maven 3.8+
· MySQL (or any configured database)

---

# Setup & Installation

1. ## Clone the repository:

```bash
git clone https://github.com/Muha-mmed/Bank-App-Spring-Boot.git
cd Bank-App-Spring-Boot
```

1. ## Copy the example config and update values:

```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
```

1. ## Run the application:

```bash
mvn spring-boot:run
```

or

```bash
./mvnw spring-boot:run
```

1. Access the app at:

```
http://localhost:8080
```

## API Endpoints

| Method | URL                          | Description              |
|--------|------------------------------|--------------------------|
| GET    | `account`              | List all accounts        |
| POST   | `account`              | Create new account       |
| GET    | `account/{accountNumber}`         | Get account by ID        |
| PUT   | `account/deposit/{AccountNumber}/{amount}` | Deposit into account     |
| PUT   | `account/withdraw/{AccountNumber/{amount}`| Withdraw from account    |
| PUT   | `account/transfer/{senderAccNumber}/{receiverAccNumber}/{amount}`     | Transfer between accounts|

---

## Running Tests

```bash
mvn test
```

---

## Deployment

Build a JAR file and run:

```bash
mvn clean package java -jar target/Bank-App-Spring-Boot-0.0.1-SNAPSHOT.jar
```
