# Card Service

A high-performance, cloud-native microservice that serves as the authoritative data layer for Gundam TCG card information within the Gundam Hub platform. Exposes a **GraphQL API** for flexible, client-driven queries — enabling consumers to fetch exactly the data they need, nothing more.

---

## What It Does

Provides structured access to the full Gundam TCG card catalog. Clients can query cards by ID, name, or filtered attributes (level, cost, color, unit type), and receive rich card data including stats, imagery, rarity, and sourcing metadata.

---

## Technical Highlights

### API Design: GraphQL-First
The service exposes **no REST endpoints**. All data access flows through a single `POST /graphql` endpoint. This eliminates over-fetching, reduces client-server round trips, and gives frontend and downstream consumers precise control over response shape.

```graphql
# Example: filter cards by color and level
query {
  cards(filter: { color: "red", level: "3" }) {
    name
    ap
    hp
    rarity
    imageSmall
  }
}
```

### Runtime: Java 21 + Virtual Threads
Built on **Java 21** with **Project Loom virtual threads** enabled. This delivers high concurrency at minimal memory cost — thousands of simultaneous requests without the overhead of traditional thread-per-request models, and without the complexity of reactive programming.

### Stack
| Layer | Technology |
|-------|-----------|
| Language | Kotlin 2.2.21 |
| Framework | Spring Boot 4.0.1 |
| API | Spring for GraphQL |
| Runtime | Java 21 (Virtual Threads) |
| Build | Gradle 9.2.1 |
| Containerization | Docker (BellSoft Alpine JRE) |

### Dual-Database Architecture via Spring Profiles
The service is designed to run in two distinct environments without code changes — profile selection drives the entire data layer:

| Profile | Database | Use Case |
|---------|----------|----------|
| `local` (default) | H2 in-memory | Local dev, fast startup, zero infrastructure |
| `cloud` | MongoDB | Production, horizontally scalable document store |

Both profiles implement a shared `CardRepository` interface. Swapping environments is a single config flag — no application logic changes.

### Clean Layered Architecture
```
GraphQL Controllers  →  Service Layer  →  Repository Interface  →  DB Adapter (JPA or Mongo)
```
The repository interface is the abstraction boundary. JPA and MongoDB adapters are independently swappable, making the core business logic portable and testable in isolation.

### Containerized for Cloud Deployment
Docker image built on a minimal **BellSoft Liberica Alpine JRE** base — lean, secure, and optimized for Kubernetes and cloud-native environments.

---

## Local Development

**Prerequisites:** JDK 21, Docker (optional)

```bash
# Start the service (H2 in-memory, auto-seeded with card data)
./gradlew bootRun

# Run tests
./gradlew test

# Build Docker image
./gradlew bootBuildImage
```

**Local endpoints:**
- GraphQL API: `POST http://localhost:8082/graphql`
- H2 Console: `http://localhost:8082/h2-console`
- Actuator/Health: `http://localhost:8082/actuator`

The H2 database is automatically seeded from `gd02.json` on startup — no external database setup required.

---

## Project Structure

```
src/main/kotlin/com/gundamhub/card_service/
├── graphql/          # Query resolvers and error handling
├── service/          # Business logic
├── repositories/
│   ├── jpa/          # H2/relational adapter
│   └── mongo/        # MongoDB adapter
└── data/             # Domain model, DTOs, entities, data seeding
```
