# ATMFx

ATMFx is a desktop-based ATM simulator application built using **JavaFX** and **JDBC**, featuring a modern UI design. It was developed to demonstrate the integration of Java with SQL databases while simulating core banking functions in an ATM interface.

> **Note:** This was one of my first projects using Java and SQL. While functional, it contains architectural limitations and known vulnerabilities.

## Features

- Transfer Money
- Generate Mini Statement
- Change PIN
- View Balance
- Custom Withdrawal

## SQL Implementation

For building the database layer of the application, I utilized a variety of **SQL techniques** including **stored procedures**, **triggers**, and **joins** (both self joins and table joins). These SQL features were crucial in handling core functionalities such as balance updates, transaction history retrieval, and user authentication. The integration of these advanced SQL concepts helped streamline the operations and make the application more dynamic and responsive.

## Architecture

The application follows a **monolithic architecture**, where both the **database layer** and **application logic** are tightly coupled. This approach simplifies development but introduces challenges in scalability and security.

## Tech Stack

- **Java**
- **JavaFX**
- **JDBC**
- **SQL (MySQL or any JDBC-compatible DB)**

## How to Run

1. Clone the repository:
   ```bash
   git clone https://github.com/CodeAvecAsnit/ATMfx.git
2.  Open the project in your Java IDE.
3.  Run the `ATM.java` file.

### This version includes an explanation of how the application operates and handles the core features through SQL queries. Let me know if you'd like to make further adjustments!

