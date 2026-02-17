# Spring Boot E-Commerce Project

## Project Goal

This project aims to create a basic e-commerce platform. Users can view products, place orders, make payments, and track the status of their orders.

## Features

- **Bidirectional Relationships**: For example, relationships between User and Cart, User and Order.
- **RESTful API**: Full CRUD operations for all entities.
- **Order Management**: Complete order lifecycle from cart to delivery.

## Database Design

| Entity  | Description                                          |
| ------- | ---------------------------------------------------- |
| User    | User information (ID, name, email, password)         |
| Product | Product information (ID, name, price, description)   |
| Order   | Order information (ID, user ID, total price, status) |
| Payment | Payment information (ID, order ID, amount, method)   |
| Cart    | User's shopping cart (ID, product list, total price) |

## Technologies & Tools

| Technology  | Purpose                                      |
| ----------- | -------------------------------------------- |
| Spring Boot | Java-based web application framework         |
| JPA         | Database operations and entity relationships |
| MySQL       | Database                                     |
| Postman     | API testing                                  |
| Maven       | Project and dependency management            |

## Project Architecture

```
┌─────────────┐    ┌─────────────┐    ┌─────────────┐    ┌─────────────┐
│  Controller │ -> │   Service   │ -> │ Repository  │ -> │  Database   │
└─────────────┘    └─────────────┘    └─────────────┘    └─────────────┘
```

- **Model**: JPA Entity classes (User, Product, Order, Cart, etc.)
- **Controller**: Spring Boot controllers providing RESTful APIs
- **Service**: Business logic and database operations
- **Repository**: JPA repository interfaces for database access

## API Endpoints

### Product Management

- `GET /products` - List all products
- `POST /products` - Add a new product
- `PUT /products/{id}` - Update a product
- `DELETE /products/{id}` - Delete a product

### Cart Management

- `GET /cart` - View cart items
- `POST /cart` - Add item to cart
- `PUT /cart/{id}` - Update cart item quantity
- `DELETE /cart/{id}` - Remove item from cart

### Order Management

- `POST /orders` - Place an order from cart
- `GET /orders/{id}` - View order details
- `PATCH /orders/{id}/status` - Update order status (pending, shipped, delivered)



## Getting Started

1. Clone the repository
2. Configure MySQL database in `application.properties`
3. Run the application:
   ```bash
   mvn spring-boot:run
   ```
4. Access the API at `http://localhost:8080`

## License

This project is open source and available under the MIT License.
