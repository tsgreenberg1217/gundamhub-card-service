# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

```bash
# Build
./gradlew build

# Run locally (activates H2 + local profile by default)
./gradlew bootRun

# Run tests
./gradlew test

# Run a single test class
./gradlew test --tests "com.gundamhub.card_service.CardServiceApplicationTests"

# Clean build
./gradlew clean build

# Build Docker image
./gradlew bootBuildImage
```

## Architecture

Spring Boot + Kotlin GraphQL service (no REST endpoints). The API is entirely GraphQL via `POST /graphql`, defined in `src/main/resources/graphql/schema.graphqls`.

**Layers:** `graphql/` (controllers) → `service/` (business logic) → `repositories/` (data access) → database

**Dual-database via Spring Profiles:**
- `local` profile (default): H2 in-memory, JPA entities (`JpaCard`), seeded from `src/main/resources/static/gd02.json` by `DataLoader` on startup
- `cloud` profile: MongoDB, document model (`MongoCard`), reads `card-service.db-host` env var for connection

The `CardRepository` interface is the abstraction boundary — `CardRepositoryJpaAdapter` implements it for local and `CardRepositoryMongoAdapter` for cloud. Spring profile annotations on `DataSourceConfig` control which beans are registered.

**Data model mapping:** `Card` is the core domain model. `JpaCard` and `MongoCard` are persistence-layer entities with extension functions (e.g., `.toCard()`, `.toJpaCard()`) for conversion. `CardDto` is the GraphQL response type.

**Local dev endpoints:**
- H2 Console: `GET /h2-console`
- Actuator: `/actuator/*`
