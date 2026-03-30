# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Tech Stack

- **Spring Boot** 4.0.1 + **Kotlin** 2.2.21 + **Java 21** (virtual threads enabled)
- **Build tool**: Gradle 9.2.1
- **GraphQL**: Spring for GraphQL (no REST endpoints)
- **Databases**: H2 (local), MongoDB (cloud)

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

Spring Boot + Kotlin GraphQL service. The API is entirely GraphQL via `POST /graphql`, defined in `src/main/resources/graphql/schema.graphqls`.

**Layers:** `graphql/` (controllers) → `service/` (business logic) → `repositories/` (data access) → database

**Dual-database via Spring Profiles:**
- `local` profile (default): H2 in-memory, port **8082**, JPA entities (`JpaCard`), seeded from `src/main/resources/static/gd02.json` by `DataLoader` on startup
- `cloud` profile: MongoDB, document model (`MongoCard`), reads `card-service.db-host` env var for connection

The `CardRepository` interface is the abstraction boundary — `CardRepositoryJpaAdapter` implements it for local and `CardRepositoryMongoAdapter` for cloud. Spring profile annotations on `DataSourceConfig` control which beans are registered.

**Data model mapping:** `Card` is the core domain model. `JpaCard` and `MongoCard` are persistence-layer entities with extension functions (e.g., `.toCard()`, `.toJpaCard()`) for conversion. `CardDto` is the GraphQL response type.

## GraphQL Schema

```graphql
type Query {
    cards(filter: CardFilter): [Card!]!
    cardById(id: ID!): Card
    cardByName(name: String!): [Card!]!
    getCard(name: String!): [Card!]!
}

input CardFilter {
    level: String
    cost: String
    color: String
    unit: String
}
```

`Card` fields: `id`, `code`, `rarity`, `name`, `effect`, `level`, `cost`, `color`, `cardType`, `zone`, `trait`, `link`, `ap`, `hp`, `sourceTitle`, `getIt`, `imageSmall`, `imageLarge`

## Key Source Files

| Path | Purpose |
|------|---------|
| `graphql/CardController.kt` | GraphQL query resolvers |
| `graphql/GraphQLExceptionResolver.kt` | GraphQL error handling |
| `service/CardService.kt` | Business logic |
| `repositories/CardRepository.kt` | Repository interface (abstraction boundary) |
| `repositories/jpa/` | JPA adapter + Spring Data repo |
| `repositories/mongo/` | MongoDB adapter + Spring Data repo |
| `data/Card.kt` | Core domain model |
| `data/CardDto.kt` | GraphQL response type |
| `data/DataLoader.kt` | Seeds H2 from `gd02.json` on startup |
| `DataSourceConfig.kt` | Profile-scoped bean registration |

## Local Dev Endpoints

- GraphQL: `POST /graphql`
- H2 Console: `GET /h2-console`
- Actuator: `/actuator/*`

## Docker

```dockerfile
FROM bellsoft/liberica-openjre-alpine
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
```

Note: Docker exposes port **8080**; local dev runs on **8082**.
