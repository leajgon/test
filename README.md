# Spring Boot Demo Application

This is a simple Spring Boot application created with Maven and Java 17.

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
│   │               └── controller/
│   │                   └── HelloController.java
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

1. **Home page:** http://localhost:8080/
2. **Hello endpoint:** http://localhost:8080/hello
3. **Hello with parameter:** http://localhost:8080/hello?name=YourName

## Available Endpoints

- `GET /` - Welcome message
- `GET /hello` - Hello message with optional name parameter

## Running Tests

```bash
mvn test
```

## Building for Production

```bash
mvn clean package -DskipTests
```

The executable JAR file will be created in the `target/` directory.
