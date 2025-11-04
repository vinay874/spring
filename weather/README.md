# Spring Boot Weather Application

A comprehensive Spring Boot application that provides secure weather-related REST APIs with role-based access control (RBAC). The system supports public user registration, JWT authentication, admin-level user management, and secure access to both general and advanced weather endpoints.

## Features

- **User Management**: Public registration and admin user creation
- **JWT Authentication**: Secure token-based authentication
- **Role-Based Access Control**: USER and ADMIN roles with different permissions
- **Weather Data**: Integration with OpenWeatherMap API
- **Caching**: Weather data caching for improved performance
- **API Documentation**: Swagger UI for easy API exploration
- **Comprehensive Testing**: Unit and integration tests

## Technology Stack

- **Framework**: Spring Boot 3.5.6
- **Language**: Java 17+
- **Security**: Spring Security with JWT
- **Database**: H2 (development) / PostgreSQL (production)
- **External API**: OpenWeatherMap
- **Documentation**: Swagger UI (OpenAPI 3)
- **Testing**: JUnit 5, Mockito
- **Build Tool**: Maven

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- OpenWeatherMap API key (optional for testing)

## Getting Started

### 1. Clone the Repository

```bash
git clone <repository-url>
cd weather
```

### 2. Configure Application

Update the `application.properties` file with your OpenWeatherMap API key:

```properties
weather.api.key=your-openweathermap-api-key
```

### 3. Run the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### 4. Access the Application

- **Swagger UI**: http://localhost:8080/swagger-ui
- **H2 Console**: http://localhost:8080/h2-console (for development)
- **API Documentation**: http://localhost:8080/api-docs

## API Endpoints

### Public Endpoints

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/api/public/register` | POST | Register a new user |
| `/api/auth/login` | POST | User login |
| `/api/weather/public/current` | GET | Get current weather |

### Admin Endpoints

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/api/admin/create-user` | POST | Create user/admin |
| `/api/admin/users` | GET | List all users |
| `/api/weather/admin/details` | GET | Get detailed weather |
| `/api/weather/admin/refresh` | POST | Refresh weather cache |

## Usage Examples

### 1. Register a User

```bash
curl -X POST http://localhost:8080/api/public/register \
  -H "Content-Type: application/json" \
  -d '{"username": "john", "password": "pwd123"}'
```

### 2. Login

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "john", "password": "pwd123"}'
```

### 3. Get Weather (Public)

```bash
curl "http://localhost:8080/api/weather/public/current?city=London"
```

### 4. Get Detailed Weather (Admin)

```bash
curl -X GET "http://localhost:8080/api/weather/admin/details?city=London" \
  -H "Authorization: Bearer <your-jwt-token>"
```

### 5. Create Admin User

```bash
curl -X POST http://localhost:8080/api/admin/create-user \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <admin-jwt-token>" \
  -d '{"username": "admin", "password": "adminpass", "role": "ADMIN"}'
```

## Security

The application implements JWT-based authentication with the following security features:

- **Password Encryption**: BCrypt password hashing
- **JWT Tokens**: Secure token-based authentication
- **Role-Based Access**: Different permissions for USER and ADMIN roles
- **CORS Support**: Configurable cross-origin resource sharing

## Database Schema

### Users Table
- `id`: Primary key
- `username`: Unique username
- `password`: Encrypted password
- `role`: USER or ADMIN

### Weather Data Table
- `id`: Primary key
- `city`: City name
- `temperature`: Current temperature
- `condition`: Weather condition
- `humidity`: Humidity percentage
- `windSpeed`: Wind speed in km/h
- `lastUpdated`: Last update timestamp

## Testing

Run the test suite:

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=UserServiceTest

# Run with coverage
mvn test jacoco:report
```

## Configuration

### Environment Variables

You can override configuration using environment variables:

```bash
export WEATHER_API_KEY=your-api-key
export JWT_SECRET=your-secret-key
export SPRING_PROFILES_ACTIVE=production
```

### Profiles

- `default`: Development with H2 database
- `test`: Testing configuration
- `production`: Production with PostgreSQL

## Deployment

### Docker

Create a Dockerfile:

```dockerfile
FROM openjdk:17-jdk-slim
COPY target/weather-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

Build and run:

```bash
docker build -t weather-app .
docker run -p 8080:8080 weather-app
```

### Production Database

For production, configure PostgreSQL:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/weatherdb
spring.datasource.username=your-username
spring.datasource.password=your-password
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Submit a pull request

## License

This project is licensed under the MIT License.

## Support

For support and questions, please open an issue in the repository.
