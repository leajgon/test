# Spring Boot Demo Application

This is a Spring Boot application created with Maven and Java 17, featuring a layered architecture with service layer, DTOs, domain objects, and MapStruct for object mapping.

## Architecture

The application follows a clean layered architecture:

- **Controller Layer**: Handles HTTP requests and responses
- **Service Layer**: Contains business logic
- **Domain Layer**: Contains domain models
- **DTO Layer**: Data Transfer Objects for API communication
- **Mapper Layer**: MapStruct for object mapping between layers

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── example/
│   │           └── demo/
│   │               ├── DemoApplication.java
│   │               ├── config/
│   │               │   └── OpenApiConfig.java
│   │               ├── controller/
│   │               │   └── HelloController.java
│   │               ├── domain/
│   │               │   ├── GreetingRequest.java
│   │               │   └── GreetingResponse.java
│   │               ├── dto/
│   │               │   ├── HelloRequestDto.java
│   │               │   └── HelloResponseDto.java
│   │               ├── mapper/
│   │               │   └── HelloMapper.java
│   │               └── service/
│   │                   ├── GreetingService.java
│   │                   └── impl/
│   │                       └── GreetingServiceImpl.java
│   └── resources/
│       └── application.properties
└── test/
    └── java/
        └── com/
            └── example/
                └── demo/
                    └── DemoApplicationTests.java
```

## Running the Application

### Using Maven

1. **Compile the project:**
   ```bash
   mvn clean compile
   ```

2. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```

3. **Build and run the JAR:**
   ```bash
   mvn clean package
   java -jar target/demo-0.0.1-SNAPSHOT.jar
   ```

### Using IDE

You can also run the `DemoApplication.java` class directly from your IDE.

## Testing the Application

Once the application is running, you can test it using:

### GET Endpoint
- **Basic:** http://localhost:8080/hello
- **With parameters:** http://localhost:8080/hello?name=John&language=es&timeOfDay=morning

### POST Endpoint
```bash
curl -X POST http://localhost:8080/hello \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John",
    "language": "es",
    "timeOfDay": "morning"
  }'
```

### Home Page
- http://localhost:8080/

## API Documentation (Swagger)

The application includes automatic API documentation using Swagger/OpenAPI 3:

- **Swagger UI:** http://localhost:8080/swagger-ui.html
- **OpenAPI JSON:** http://localhost:8080/api-docs

## Available Endpoints

- `GET /` - Welcome message
- `GET /hello` - Hello message with query parameters (name, language, timeOfDay)
- `POST /hello` - Hello message with JSON request body

## Supported Languages and Times

The service supports multiple languages and times of day:

### Languages
- `en` - English
- `es` - Spanish  
- `fr` - French

### Times of Day
- `morning` - Morning greetings
- `afternoon` - Afternoon greetings
- `evening` - Evening greetings
- `night` - Night greetings
- `day` - General day greeting (default)

## Technologies Used

- **Spring Boot 3.2.0** - Application framework
- **Java 17** - Programming language
- **Maven** - Build tool
- **MapStruct** - Object mapping
- **Lombok** - Reducing boilerplate code
- **SpringDoc OpenAPI** - API documentation
- **Swagger UI** - Interactive API documentation

## Running Tests

```bash
mvn test
```

## Building for Production

```bash
mvn clean package -DskipTests
```

The executable JAR file will be created in the `target/` directory.
