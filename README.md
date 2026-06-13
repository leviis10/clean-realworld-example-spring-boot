# RealWorld Example - Backend

A backend implementation of the [RealWorld](https://github.com/gothinkster/realworld) specification — a Medium.com clone
that demonstrates production-grade backend engineering practices.

## Tech Stack

| Category             | Technology                                           |
|----------------------|------------------------------------------------------|
| **Language**         | Java 25                                              |
| **Framework**        | Spring Boot 4.0.6                                    |
| **Build Tool**       | Apache Maven 3.9.14 (via Maven Wrapper)              |
| **Database**         | MySQL (via `mysql-connector-j`)                      |
| **ORM**              | Spring Data JPA / Hibernate                          |
| **Security**         | Spring Security + JWT (jjwt 0.13.0)                  |
| **API Docs**         | SpringDoc OpenAPI 3.0.3 (`/swagger-ui`, `/api-docs`) |
| **Password Hashing** | Bouncy Castle (bcprov-jdk18on 1.84)                  |
| **Validation**       | Jakarta Bean Validation                              |
| **Observability**    | Micrometer Tracing (Brave), Spring Boot Actuator     |
| **Concurrency**      | Java Virtual Threads enabled                         |
| **Code Gen**         | Lombok 1.18.46                                       |

## Architecture

The project follows a **Hexagonal (Ports & Adapters) Architecture** combined with **Domain-Driven Design** and **CQRS
pattern**, organized into bounded contexts.

```
src/main/java/com/leviis/realworldexample/
  Application.java              # Spring Boot entry point
  user/                         # User Bounded Context
    domain/                     #   Core domain models & value objects
    application/                #   Application layer
      command/                  #     CQRS command handlers
      query/                    #     CQRS query handlers
      port/inbound/             #     Inbound ports (use case interfaces)
      port/outbound/            #     Outbound ports (repository, service interfaces)
    adapter/
      inbound/http/             #     REST controllers & DTOs
      outbound/                 #     JPA persistence, JWT, Password impls
  article/                      # Article Bounded Context (same structure)
  tag/                          # Tag Bounded Context (same structure)
  infrastructure/               # Cross-cutting concerns
    filter/                     #   JWT filter, trace ID filter
    security/                   #   Spring Security config, handlers
    exceptions/                 #   Global exception handler
```

### Key Principles

- **Domain layer** — pure Java objects with no framework dependencies; contains business logic, value objects (`Email`,
  `Slug`, `RawPassword`)
- **Application layer** — use case orchestration via command/query handlers; depends only on domain ports
- **Adapter layer** — REST controllers (inbound) and persistence/security implementations (outbound); depends on
  application ports
- **CQRS** — commands (writes) and queries (reads) are separated at both the handler and repository levels

### Bounded Contexts

1. **User** — registration, login, profile management, follow/unfollow
2. **Article** — CRUD, feed, tagging, favoriting, comments
3. **Tag** — listing available tags

## Environment Variables

All configuration is injected via Spring Boot's `${}` placeholders.

| Variable               | Required | Default | Description                                                              |
|------------------------|----------|---------|--------------------------------------------------------------------------|
| `DATABASE_URL`         | Yes      | —       | JDBC URL for MySQL, e.g. `jdbc:mysql://localhost:3306/realworld_example` |
| `DATABASE_USERNAME`    | Yes      | —       | Database user                                                            |
| `DATABASE_PASSWORD`    | Yes      | —       | Database password                                                        |
| `JWT_SECRET`           | Yes      | —       | HMAC-SHA256 key for JWT signing (256-bit hex-encoded)                    |
| `JWT_EXPIRATION`       | Yes      | —       | JWT token lifetime in seconds (e.g. `3600`)                              |
| `CORS_ALLOWED_ORIGINS` | Yes      | —       | Comma-separated allowed origins or `*`                                   |

Copy `.env.example` to `.env` and fill in your values:

```bash
cp .env.example .env
```

## Build & Run

### Locally (requires JDK 25+)

```bash
./mvnw clean package
java -jar target/realworld-example.jar
```

Or using Spring Boot Maven plugin:

```bash
./mvnw spring-boot:run
```

### With Docker

```bash
# Build the image
docker build -t realworld-example .

# Run with a MySQL database
docker run -p 8080:8080 \
  -e DATABASE_URL=jdbc:mysql://host.docker.internal:3306/realworld_example \
  -e DATABASE_USERNAME=root \
  -e DATABASE_PASSWORD=password \
  -e JWT_SECRET=your-256-bit-hex-secret \
  -e JWT_EXPIRATION=3600 \
  -e CORS_ALLOWED_ORIGINS=* \
  realworld-example
```

The Dockerfile uses a **multi-stage build**:

1. **Builder** — `eclipse-temurin:25-jdk-alpine` downloads Maven dependencies and compiles the JAR
2. **Runtime** — `eclipse-temurin:25-jre-alpine` runs the JAR with minimal footprint

## API Documentation

Once running, visit:

- **Swagger UI**: `http://localhost:8080/swagger-ui`
- **OpenAPI spec**: `http://localhost:8080/api-docs`

## Code Quality & Static Analysis

Four static analysis tools are integrated into the Maven build. All run during `verify` phase.

### Spotless (Palantir Java Format)

Auto-formats Java source code using Palantir Java Format conventions.

```bash
./mvnw spotless:apply
```

### Checkstyle

Enforces Sun Code Conventions — naming, imports, whitespace, blocks, coding problems, class design, Javadoc.
Suppressions are defined in `checkstyle-suppressions.xml` (e.g., `DesignForExtension` is suppressed on repository
implementations, controllers, and adapter configs since these classes are framework-managed and not designed for
subclassing).

```bash
./mvnw checkstyle:check
```

### PMD

Static analysis across 8 rule categories:

- **bestpractices** — Junit, logging, resource handling
- **codestyle** — naming, field declarations (excludes `CommentDefaultAccessModifier`, `LongVariable`, `ShortVariable`,
  `LinguisticNaming`, `OnlyOneReturn`)
- **design** — coupling, complexity (excludes `ExcessiveImports`, `TooManyMethods`, `UseObjectForClearerAPI`)
- **documentation** — (excludes `CommentRequired`)
- **errorprone** — null handling, resource leaks, `AvoidDuplicateLiterals` with annotation skip
- **multithreading** — concurrency issues
- **performance** — inefficient patterns
- **security** — insecure practices

Also runs **CPD** (Copy-Paste Detector) as a non-failing check.

```bash
./mvnw pmd:check pmd:cpd-check
```

### SpotBugs (with FindSecBugs)

Bytecode-level bug detection including security vulnerability scanning via FindSecBugs.

```bash
./mvnw spotbugs:check
```

### Run All Checks

```bash
./mvnw verify
```

## Tests

Unit and integration tests use Spring Boot test slices (`@DataJpaTest`, `@WebMvcTest`, etc.) and Mockito.

```bash
./mvnw test
```

## Maven Commands Reference

| Command                          | Description                     |
|----------------------------------|---------------------------------|
| `./mvnw compile`                 | Compile source code             |
| `./mvnw test`                    | Run tests                       |
| `./mvnw package`                 | Package as JAR (runs tests)     |
| `./mvnw verify`                  | Full build + all quality checks |
| `./mvnw clean`                   | Remove build artifacts          |
| `./mvnw spring-boot:run`         | Run application                 |
| `./mvnw spotless:apply`          | Format code with Palantir       |
| `./mvnw checkstyle:check`        | Run Checkstyle                  |
| `./mvnw pmd:check pmd:cpd-check` | Run PMD + CPD                   |
| `./mvnw spotbugs:check`          | Run SpotBugs + FindSecBugs      |

## Project Structure

```
.
├── .env.example              # Environment variable template
├── Dockerfile                # Multi-stage Docker build
├── pom.xml                   # Maven build descriptor
├── mvnw / mvnw.cmd           # Maven Wrapper scripts
├── checkstyle-configuration.xml
├── checkstyle-suppressions.xml
├── pmd-ruleset.xml
└── src/
    ├── main/java/com/leviis/realworldexample/
    │   ├── Application.java
    │   ├── user/              # User bounded context
    │   ├── article/           # Article bounded context
    │   ├── tag/               # Tag bounded context
    │   ├── infrastructure/    # Cross-cutting: security, filters, exceptions
    │   └── utils/             # Shared utilities
    └── test/java/com/leviis/realworldexample/
        └── user/              # User context tests
```
