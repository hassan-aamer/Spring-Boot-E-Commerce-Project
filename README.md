# Enterprise Spring Boot E-Commerce REST API

Production-ready, scalable, and secure E-Commerce RESTful Web Application built with **Spring Boot 3**, **Spring Security**, **JWT Authentication**, **JPA / Hibernate**, **MySQL**, and **Docker**.

---

## 🌟 Key Features

- **Security & JWT Authentication**: User registration, password encryption with BCrypt, stateless JWT authentication (`/api/auth/login`, `/api/auth/register`), and Role-Based Access Control (`ROLE_USER`, `ROLE_ADMIN`).
- **Product & Category Management**: Full CRUD operations for products and categories with automatic validation and inventory tracking.
- **Cart & Cart Item Lifecycle**: Manage user shopping carts, add/update/remove items dynamically.
- **Order Processing & Inventory Stock Control**: Order placement from cart items with automatic stock deduction, price total calculation, and status workflow (`PENDING` ➡️ `PROCESSING` ➡️ `PAID` ➡️ `SHIPPED` ➡️ `DELIVERED` / `CANCELLED`).
- **Payment Processing Module**: Seamless payment processing linked to orders with payment status tracking (`COMPLETED`, `PENDING`, `FAILED`, `REFUNDED`).
- **Interactive OpenAPI / Swagger UI**: Built-in API documentation and testing interface at `/swagger-ui.html` with Bearer JWT token support.
- **Automated Testing Suite**: Unit and integration test coverage powered by JUnit 5, Mockito, and H2 In-Memory Database (`application-test.properties`).
- **Docker & Containerization**: Multi-stage `Dockerfile` and `docker-compose.yml` for effortless environment orchestration.

---

## 🛠️ Technology Stack

| Component | Technology / Framework |
| :--- | :--- |
| **Backend Framework** | Spring Boot 3.5.0, Java 21 |
| **Security & Auth** | Spring Security 6, JJWT (v0.12.6), BCrypt |
| **Persistence Layer** | Spring Data JPA, Hibernate, MySQL 8.0 |
| **Testing** | JUnit 5, Mockito, H2 Database |
| **Documentation** | Springdoc OpenAPI v2.8.5 (Swagger UI) |
| **Containerization** | Docker, Docker Compose |
| **Build & Tooling** | Apache Maven, ModelMapper |

---

## 📐 Database Design

| Entity | Description | Key Relationships |
| :--- | :--- | :--- |
| **User** | User profile, credentials, and authority roles | 1:1 Cart, 1:N Orders |
| **Role** | Authority enum (`ROLE_USER`, `ROLE_ADMIN`) | Assigned to User |
| **Category** | Product categorization | 1:N Products |
| **Product** | Product details, pricing (`BigDecimal`), and stock inventory | N:1 Category |
| **Cart** | Shopping cart owned by a user | 1:1 User, 1:N CartItems |
| **CartItem** | Individual product item in cart with quantity | N:1 Cart, N:1 Product |
| **Order** | Customer order record with total amount & status | N:1 User, 1:N OrderItems, 1:1 Payment |
| **OrderItem** | Snapshot of ordered item, quantity, and historical price | N:1 Order, N:1 Product |
| **Payment** | Transaction record, amount, method, and payment status | 1:1 Order |

---

## 🚀 Getting Started

### Prerequisites

- Java 21 JDK
- Maven 3.8+
- Docker & Docker Compose (optional for containerized setup)
- MySQL Server 8.0+

### Option 1: Run with Docker Compose (Recommended)

Run the entire application and database with a single command:

```bash
docker-compose up --build
```

Access the API at `http://localhost:8085`, Swagger UI at `http://localhost:8085/swagger-ui.html`, and phpMyAdmin at `http://localhost:8086`.

### Option 2: Run Locally

1. Create a MySQL database named `e_commerce_db`:
   ```sql
   CREATE DATABASE e_commerce_db;
   ```
2. Update database credentials in `src/main/resources/application.properties` if needed.
3. Build and run the application:
   ```bash
   mvn clean spring-boot:run
   ```

### Option 3: Run Automated Tests

To execute unit and integration tests using the isolated H2 test profile:

```bash
mvn test
```

---

## 🔌 API Endpoints Summary

### Authentication (`/api/auth`)

- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - Authenticate user and receive JWT token

### Products (`/api/products`)

- `GET /api/products` - List all products *(Public)*
- `GET /api/products/{id}` - Get product details *(Public)*
- `POST /api/products` - Create new product *(Protected)*
- `PUT /api/products/{id}` - Update product *(Protected)*
- `DELETE /api/products/{id}` - Delete product *(Protected)*

### Carts & Cart Items (`/api/carts`, `/api/cart-items`)

- `POST /api/carts` - Create user cart
- `GET /api/carts/user/{userId}` - Fetch user's cart
- `POST /api/carts/{cartId}/items` - Add item to cart
- `PUT /api/cart-items/{id}` - Update cart item quantity
- `DELETE /api/cart-items/{id}` - Remove item from cart

### Orders (`/api/orders`)

- `POST /api/orders` - Place order from cart items (deducts stock & calculates total)
- `GET /api/orders/{id}` - Fetch order details
- `GET /api/orders/user/{userId}` - List orders for a user
- `PUT /api/orders/{id}/status` - Update order status (`PENDING`, `SHIPPED`, `DELIVERED`, `CANCELLED`)
- `DELETE /api/orders/{id}` - Cancel order (restores stock)

### Payments (`/api/payments`)

- `POST /api/payments` - Process order payment
- `GET /api/payments/{paymentId}` - Get payment by ID
- `GET /api/payments/order/{orderId}` - Get payment by Order ID

---

## 📮 Postman Collection

Import `postman_collection.json` into Postman to test all endpoints. The collection automatically saves the JWT token upon executing `/api/auth/login` and includes it in subsequent protected requests.

---

## 📄 License

This project is licensed under the MIT License.
