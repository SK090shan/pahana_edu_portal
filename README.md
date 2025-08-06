# Pahana Education - Enterprise Bookstore Billing System

Pahana Education is a complete re-architecture of a web-based billing and inventory management system for the "Pahana Edu" bookstore. This version was built with a "theory-first" mindset, focusing on the explicit implementation of enterprise-grade design patterns, a robust 3-tier architecture, and advanced security features as outlined in the course lectures.

---

## Table of Contents
1. [Project Overview](#project-overview)
2. [Key Features](#key-features)
3. [Architectural Design & Implemented Patterns](#architectural-design--implemented-patterns)
4. [Security Features](#security-features)
5. [Technology Stack](#technology-stack)
6. [Database Schema](#database-schema)
7. [Setup and Installation Guide](#setup-and-installation-guide)
8. [References](#references)

---

## Project Overview

This project fulfills the need for a computerized, online system to manage the billing and inventory for Pahana Edu bookstore. It provides a secure, multi-user environment where different roles (Staff, Admin) have distinct permissions. The system handles the entire lifecycle of bookstore operations, from user registration and inventory management to interactive invoicing, receipt generation (PDF with QR code), and administrative reporting with data export capabilities.

---

## Key Features

-   **Secure User Authentication:** A robust login/logout system with securely hashed passwords using **jBCrypt**.
-   **Role-Based Access Control (RBAC):**
    -   Two distinct roles: **Staff** and **Admin**.
    -   Admins have full privileges, including deleting records, viewing reports, and managing staff accounts.
    -   Staff have operational access but are restricted from sensitive areas and destructive actions.
-   **Full CRUD Functionality:**
    -   **Staff Management (Admin Only):** Admins can view all users, activate new 'PENDING' registrations, and delete staff accounts.
    -   **Customer & Item Management:** Full Create, Read, and Update capabilities for all users. Delete functionality is restricted to Admins.
-   **Interactive Invoicing System:**
    -   A dynamic UI allows for building a bill by adding multiple items.
    -   Line totals are calculated in real-time on the client-side using JavaScript.
    -   A two-stage process: "Finalize Bill" saves the transaction to the database, then redirects to a confirmation page.
-   **Advanced Receipt Generation:**
    -   After a bill is finalized, users can print a professional PDF receipt.
    -   Receipts are generated dynamically using the **iTextPDF** library.
    -   Each receipt includes a **QR Code**, generated using the **ZXing** library, which contains key bill information.
-   **Comprehensive Admin Reporting:**
    -   An Admin-only reports dashboard displays tables of all staff, customers, and inventory.
    -   Calculates and displays the **Total Income** from all historical bills.
    -   Allows for data to be exported to **CSV files** for offline analysis.

---

## Architectural Design & Implemented Patterns

This application was architected to be maintainable, scalable, and secure, directly applying principles from the course lectures.

### 1. 3-Tier Architecture
The system is built on a classic 3-Tier Architecture, ensuring a strong separation of concerns:
-   **Presentation Tier (View):** The JSP files (`webapp/`) which render the HTML user interface.
-   **Application Tier (Controller/Logic):**
    -   The `com.pahana.controller` package contains all Jakarta Servlets, which act as the system's controllers.
    -   The `com.pahana.service` package contains business logic services like `CsvExportService` and `PdfGenerationService`.
-   **Data Tier (Model/Data Access):**
    -   The `com.pahana.model` packages contain the POJOs.
    -   The `com.pahana.dao` packages contain the data access logic.

### 2. Core Design Patterns

-   **Model-View-Controller (MVC):** This is the primary architectural pattern.
    -   **Model:** The POJOs in `com.pahana.model.*`.
    -   **View:** The JSP files in `webapp/`.
    -   **Controller:** The Servlets in `com.pahana.controller`.

-   **Singleton Pattern (Creational - Lecture 3):**
    The `DatabaseManager` class is implemented as a Singleton to guarantee that only one instance exists. This provides a single, thread-safe point of access to the database connection pool, which is critical in a multi-threaded web environment.
    ```java
    // In DatabaseManager.java
    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }
    ```

-   **Factory Pattern (Creational - Lecture 3):**
    The `DAOFactory` provides a centralized method for creating Data Access Objects. This decouples the controllers from the concrete DAO implementations (e.g., `UserDAOImpl`). If we were to switch to a different database technology, we would only need to change the implementation in the factory, not in every servlet.
    ```java
    // In a Servlet
    userDAO = DAOFactory.getUserDAO(); // The servlet doesn't know or care about UserDAOImpl
    ```

-   **Data Access Object (DAO) Pattern:**
    This pattern is used to abstract the data persistence logic. The `com.pahana.dao` package contains interfaces (`UserDAO`, `ItemDAO`), and the `com.pahana.dao.impl` package contains the concrete JDBC implementations. This separation is key to a maintainable system (Sommerville, 2016).

---

## Security Features

-   **Authentication:** Handled by `LoginServlet`, which checks credentials and creates an `HttpSession`.
-   **Authorization (RBAC):** Enforced at three levels:
    1.  **UI Level:** JSP scriptlets (`<% ... %>`) are used to conditionally render links and buttons (e.g., "Delete", "Reports") based on the user's role.
    2.  **Controller Level:** Servlets like `ItemServlet` and `CustomerServlet` contain server-side `if` statements to block unauthorized actions (e.g., delete) even if a user bypasses the UI.
    3.  **Filter Level:** The `AuthenticationFilter` acts as a central gatekeeper, protecting all application URLs (`/*`) and redirecting any non-authenticated users to the login page.
-   **Data Security:**
    -   **Password Hashing:** All user passwords are securely hashed using the industry-standard **jBCrypt** algorithm before being stored.
    -   **SQL Injection Prevention:** All database queries are executed using **Prepared Statements**, which prevents SQL injection attacks.

---

## Technology Stack

-   **Backend:** Java 17, Jakarta Servlets 5.0
-   **Database:** Microsoft SQL Server
-   **Web Server:** Apache Tomcat 11.0
-   **Build Tool:** Apache Maven
-   **Frontend:** JSP, HTML5, CSS3, JavaScript, Bootstrap 5
-   **Key Libraries:**
    -   `mssql-jdbc`: For database connectivity.
    -   `jbcrypt`: For secure password hashing.
    -   `itext7-core`: For dynamic PDF generation.
    -   `zxing-core/javase`: For QR code generation.
    -   `Jakarta JSTL`: For tag library support in JSPs.

---

## Database Schema

The database `PahanaEduDB` consists of five tables:

```sql
CREATE TABLE Users (
    userId INT PRIMARY KEY IDENTITY(1,1),
    fullName VARCHAR(100), username VARCHAR(50) UNIQUE, email VARCHAR(100) UNIQUE,
    password_hash VARCHAR(256), role VARCHAR(20), status VARCHAR(20)
);

CREATE TABLE Customers (
    customerId INT PRIMARY KEY IDENTITY(1,1),
    accountNumber VARCHAR(50) UNIQUE, fullName VARCHAR(100), address VARCHAR(255), telephone VARCHAR(20)
);

CREATE TABLE Items (
    itemId INT PRIMARY KEY IDENTITY(1,1),
    itemName VARCHAR(150), price DECIMAL(10, 2), stock INT
);

CREATE TABLE Bills (
    billId INT PRIMARY KEY IDENTITY(1,1),
    customerId INT, staffId INT, billDate DATETIME, totalAmount DECIMAL(10, 2),
    FOREIGN KEY (customerId) REFERENCES Customers(customerId),
    FOREIGN KEY (staffId) REFERENCES Users(userId)
);

CREATE TABLE BillItems (
    billItemId INT PRIMARY KEY IDENTITY(1,1),
    billId INT, itemId INT, quantity INT, pricePerUnit DECIMAL(10, 2),
    FOREIGN KEY (billId) REFERENCES Bills(billId),
    FOREIGN KEY (itemId) REFERENCES Items(itemId)
);
```

---

## Setup and Installation Guide

1.  **Prerequisites:**
    -   JDK 17
    -   Apache Maven
    -   Microsoft SQL Server
    -   Apache Tomcat 11.0
    -   Eclipse IDE for Enterprise Java Developers

2.  **Database Setup:**
    -   Create a new database named `PahanaEduDB`.
    -   Execute the SQL scripts from the "Database Schema" section above to create all tables.
    -   **Manually insert the first Admin user:**
        ```sql
        INSERT INTO Users (fullName, username, email, password_hash, role, status)
        VALUES ('Admin User', 'admin', 'admin@pahana.edu', '$2a$12$YOUR_BCRYPT_HASH_HERE', 'Admin', 'ACTIVE');
        ```
        *(Note: You will need to generate a Bcrypt hash for a password like 'admin123' to insert here, as the login checks for a hash, not plain text.)*

3.  **Configuration:**
    -   Clone the repository.
    -   Import the project into Eclipse as an "Existing Maven Project".
    -   Navigate to `src/main/java/com/pahana/util/DatabaseManager.java` and update the database connection `URL`, `USER`, and `PASSWORD` to match your local SQL Server instance.

4.  **Running the Application:**
    -   In Eclipse, right-click the project -> **Maven -> Update Project**.
    -   Configure an Apache Tomcat 11.0 server in Eclipse.
    -   Right-click the project -> **Run As -> Run on Server**.
    -   The application will be accessible at `http://localhost:8080/PahanaEducation/`.


