# 📚 E-Library API

> A comprehensive RESTful API for managing a digital library system with user authentication, book inventory management, and advanced search capabilities.

![Java](https://img.shields.io/badge/Java-17+-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue.svg)
![Redis](https://img.shields.io/badge/Redis-Cache-red.svg)
![License](https://img.shields.io/badge/License-MIT-yellow.svg)

> ⚠️ **Important:** The final version of this project is in the `rebuild` branch. Make sure to checkout that branch for the latest stable code.

---

## ✨ Features

### 🔐 User Authentication & Authorization
- JWT-based authentication with Redis token management
- Email verification with OTP for registration
- Password reset functionality with OTP verification
- Secure password encryption
- Token blacklisting for logout functionality

### 📖 Book Management
- Browse and search books with pagination
- Advanced search with keyword filtering
- Genre-based filtering
- Random book recommendations
- Detailed book information including ratings and descriptions

### 📚 User Inventory
- Personal book collection management
- Add books to personal library
- Remove books from collection
- View all owned books

### 👤 User Profile Management
- Update user information
- Change password with validation
- Profile picture support

---

## 🛠️ Technology Stack

| Technology | Purpose |
|------------|---------|
| **Spring Boot 3.x** | Application Framework |
| **Java 17+** | Programming Language |
| **PostgreSQL** | Primary Database |
| **Redis Cloud** | Cache & Session Store |
| **Spring Security** | Security Framework |
| **JWT** | Token-based Authentication |
| **Spring Mail** | Email Service |
| **Hibernate/JPA** | ORM |
| **Maven** | Build Tool |

---

## 📋 Prerequisites

Before running this application, ensure you have:

- ☕ Java 17 or higher
- 📦 Maven or Gradle
- 🐘 PostgreSQL database
- 🔴 Redis instance
- 📧 Gmail account for email functionality (or another SMTP provider)

---

## 🚀 Installation

### 1️⃣ Clone the repository

```bash
git clone <repository-url>
cd e-library
git checkout rebuild
```

> 💡 **Note:** Make sure to checkout the `rebuild` branch as it contains the final version of the project.

### 2️⃣ Configure application properties

Create an `application.properties` file in `src/main/resources/`:

```properties
spring.application.name=e-library

# Database Configuration
spring.datasource.url=jdbc:postgresql://<your-host>:<port>/<database-name>
spring.datasource.username=<your-username>
spring.datasource.password=<your-password>
spring.datasource.driver-class-name=org.postgresql.Driver

# Hibernate
spring.jpa.show-sql=true
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect

# JWT Secret (Generate a secure random string)
JWT_SECRET=<your-secret-key>

# Email Configuration
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=<your-email>
spring.mail.password=<your-app-password>
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

# Redis Configuration
spring.data.redis.host=<redis-host>
spring.data.redis.port=<redis-port>
spring.data.redis.password=<redis-password>
```

### 3️⃣ Set up the database

Create the necessary tables in your PostgreSQL database:

```sql
-- Users table
CREATE TABLE users (
    user_id VARCHAR(7) PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(100),
    surname VARCHAR(100),
    birth_date DATE,
    gender VARCHAR(20),
    picture_url VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Books table
CREATE TABLE books (
    book_id SERIAL PRIMARY KEY,
    title VARCHAR(500),
    author TEXT,
    description TEXT,
    coverimg VARCHAR(500),
    likedpercent VARCHAR(10),
    rating DECIMAL(3,2),
    numratings INTEGER,
    pages INTEGER
);

-- Genres table
CREATE TABLE genres (
    book_id INTEGER REFERENCES books(book_id),
    genre VARCHAR(100)
);

-- User inventory table
CREATE TABLE user_inventory (
    user_id VARCHAR(7) REFERENCES users(user_id),
    book_id INTEGER REFERENCES books(book_id),
    PRIMARY KEY (user_id, book_id)
);
```

### 4️⃣ Build the project

```bash
mvn clean install
```

### 5️⃣ Run the application

```bash
mvn spring-boot:run
```

🎉 The API will start on `http://localhost:8080`

---

## 📡 API Endpoints

### 🔐 Authentication

#### Register User (Initiate)
```http
POST /api/auth/register/initiate
Content-Type: application/json
```

**Request Body:**
```json
{
  "email": "user@example.com",
  "password": "SecurePass123"
}
```

**Validation Rules:**
- Email must be valid format
- Password must be 8-50 characters
- Password must contain at least one uppercase letter, one lowercase letter, and one digit

#### Register User (Verify)
```http
POST /api/auth/register/verify
Content-Type: application/json
```

**Request Body:**
```json
{
  "email": "user@example.com",
  "otp": "123456"
}
```

**Validation Rules:**
- Email must be valid format
- OTP code is required (6-digit code sent to email)

#### Login
```http
POST /api/auth/login
Content-Type: application/json
```

**Request Body:**
```json
{
  "email": "user@example.com",
  "password": "SecurePass123"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "userId": "ABC1234",
  "email": "user@example.com",
  "name": "John",
  "surname": "Doe",
  "birthday": "1990-01-01",
  "gender": "MALE",
  "pictureUrl": "https://..."
}
```

#### Logout
```http
POST /api/auth/logout
Authorization: Bearer <token>
```

---

### 🔑 Password Reset

#### Initiate Password Reset
```http
POST /api/password/reset/initiate
Content-Type: application/json
```

**Request Body:**
```json
{
  "email": "user@example.com"
}
```

#### Verify Password Reset
```http
POST /api/password/reset/verify
Content-Type: application/json
```

**Request Body:**
```json
{
  "email": "user@example.com",
  "otpCode": "123456",
  "newPassword": "NewSecurePass123"
}
```

**Validation Rules:**
- Email must be valid format
- OTP code is required
- New password must be 8-50 characters
- New password must contain at least one uppercase letter, one lowercase letter, and one digit

---

### 📖 Books (Public Endpoints)

#### Get Random Books
```http
GET /api/books
```

Returns 10 random books from the library.

#### Get Book by ID
```http
GET /api/books/get?id=1
```

#### Search Books
```http
GET /api/books/search?keyword=harry&page=0
```

#### Advanced Search
```http
GET /api/books/advancedsearch?keyword=potter&page=0
```

#### Filter by Genres
```http
POST /api/books/filter?page=0
Content-Type: application/json
```

**Request Body:**
```json
["Fantasy", "Adventure"]
```

---

### 👤 User Management (Protected Endpoints)

> 🔒 All endpoints require `Authorization: Bearer <token>` header

#### Update User Profile
```http
PUT /api/user/update
Authorization: Bearer <token>
Content-Type: application/json
```

**Request Body:**
```json
{
  "name": "John",
  "surname": "Doe",
  "birthdate": "1990-01-01",
  "gender": "MALE"
}
```

**Validation Rules:**
- Name and surname must contain only letters
- Name and surname must be 2-50 characters
- Birthdate must be in the past
- Gender must be valid enum value (MALE, FEMALE, OTHER)

#### Change Password
```http
PUT /api/user/change-password
Authorization: Bearer <token>
Content-Type: application/json
```

**Request Body:**
```json
{
  "oldPassword": "OldPass123",
  "newPassword": "NewPass123",
  "confirmPassword": "NewPass123"
}
```

**Validation Rules:**
- All fields are required
- New password must be 8-50 characters
- New password must contain at least one uppercase letter, one lowercase letter, and one digit
- New password and confirm password must match

---

### 📚 Inventory Management (Protected Endpoints)

> 🔒 All endpoints require `Authorization: Bearer <token>` header

#### Get User's Books
```http
GET /api/inventory
Authorization: Bearer <token>
```

#### Add Book to Inventory
```http
POST /api/inventory/add?bookId=1
Authorization: Bearer <token>
```

#### Remove Book from Inventory
```http
DELETE /api/inventory/remove?bookId=1
Authorization: Bearer <token>
```

---

## 🔒 Security

| Feature | Implementation |
|---------|----------------|
| **JWT Tokens** | 1-hour expiration time |
| **Password Encryption** | BCrypt with Spring Security's DelegatingPasswordEncoder |
| **Token Blacklisting** | Revoked tokens stored in Redis |
| **OTP Verification** | 6-digit codes for email verification and password reset |
| **CORS** | Configured for `http://localhost:3000` (frontend) |

---

## ⚠️ Error Handling

The API uses consistent error responses:

**Standard Error Response:**
```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "Human-readable error message",
  "timestamp": "2025-11-11 10:30:00"
}
```

**Validation Error Response:**
```json
{
  "status": 400,
  "error": "Bad Request",
  "fieldErrors": {
    "email": "Email must be valid",
    "password": "Password must be between 8 and 50 characters"
  },
  "message": "Validation failed",
  "timestamp": "2025-11-11 10:30:00"
}
```

### Common Error Codes

| Code | Description |
|------|-------------|
| `USER_NOT_FOUND` | User does not exist |
| `INVALID_CREDENTIALS` | Wrong email or password |
| `DUPLICATE_RESOURCE` | Email already registered |
| `INVALID_OTP` | Wrong OTP code |
| `BLACK_LISTED_TOKEN` | Token has been revoked |
| `BOOK_NOT_FOUND` | Book does not exist |
| `MISSING_PARAMETER` | Required parameter missing |
| `INVALID_PARAMETER_TYPE` | Wrong parameter type |
| `MISMATCHED_PASSWORDS` | Password and confirm password don't match |
| `SAME_PASSWORD_EXCEPTION` | New password is same as old password |
| `REGISTRATION_NOT_FOUND` | Registration session not found or expired |
| `PW_RESET_SESSION_NOT_FOUND` | Password reset session not found or expired |
| `INVALID_PAYLOAD` | Request body contains invalid data |

---

## 🔐 Password Requirements

All passwords in the system must meet these criteria:

- ✅ Minimum 8 characters
- ✅ Maximum 50 characters  
- ✅ At least one uppercase letter (A-Z)
- ✅ At least one lowercase letter (a-z)
- ✅ At least one digit (0-9)

**Example valid passwords:** `SecurePass123`, `MyP@ssw0rd`, `HelloWorld1`

**Example invalid passwords:** `password` (no uppercase or digit), `PASSWORD123` (no lowercase), `ShortP1` (too short)

---

## 🏗️ Project Structure

```
src/main/java/com/project/e_library/
├── controller/          # REST controllers
├── service/            # Business logic
├── model/              # JPA entities
├── dto/                # Data transfer objects
├── security/           # Security configuration & JWT
├── exception/          # Custom exceptions & handlers
└── id/                 # ID generation utilities
```

---

## 🧪 Development

### Running Tests
```bash
mvn test
```

### Building for Production
```bash
mvn clean package
java -jar target/e-library-0.0.1-SNAPSHOT.jar
```

---

## 🌍 Environment Variables (Production)

For production deployment, use environment variables:

```bash
export DB_URL=jdbc:postgresql://your-host:5432/dbname
export DB_USERNAME=your_username
export DB_PASSWORD=your_password
export JWT_SECRET=your_jwt_secret
export REDIS_HOST=your_redis_host
export REDIS_PORT=6379
export REDIS_PASSWORD=your_redis_password
export MAIL_USERNAME=your_email
export MAIL_PASSWORD=your_app_password
```

---

## 🤝 Contributing

We welcome contributions! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

---

## 💬 Support

For issues and questions, please open an issue in the GitHub repository.

---

## 📝 Note

**This is a backend API.** You'll need a separate frontend application to interact with these endpoints. The CORS configuration currently allows requests from `http://localhost:3000`.
