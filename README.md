# distributor-domain-branding

Domain layer microservice responsible for orchestrating distributor branding and terms & conditions operations. This service acts as the domain orchestration layer between API consumers and the `common-platform-distributor-mgmt` management service, coordinating multi-step workflows through saga-based distributed transactions.

## Overview

The Distributor Domain Branding service manages the full lifecycle of distributor branding identities and legal agreements:

- **Distributor Onboarding** -- Orchestrates the complete onboarding of a new distributor, including registration of distributor info, terms & conditions templates, terms & conditions, audit logs, and branding configuration via a compensatable saga workflow.
- **Branding Management** -- Supports revising branding attributes (logo, colors, fonts, theme) and designating a default branding configuration per distributor.
- **Terms & Conditions Management** -- Enables versioning and revision of distributor terms & conditions, linked to reusable templates with auto-renewal support.
- **Audit Logging** -- Records audit events for distributor lifecycle actions (created, updated, terminated) during onboarding.
- **Saga Orchestration** -- All write operations are executed as saga workflows with compensating transactions, ensuring data consistency across distributed steps.

## Architecture

### Module Structure

| Module | Description |
|--------|-------------|
| `distributor-domain-branding-core` | Business logic: commands, handlers, saga workflows, service interfaces and implementations |
| `distributor-domain-branding-interfaces` | Interface adapters connecting core to infrastructure and external boundaries |
| `distributor-domain-branding-infra` | Infrastructure layer: API client factory, configuration properties, external service integration |
| `distributor-domain-branding-web` | Spring Boot WebFlux application: REST controllers, application entry point, configuration |
| `distributor-domain-branding-sdk` | Auto-generated client SDK from OpenAPI spec for downstream consumers |

### Tech Stack

- **Java 25**
- **Spring Boot** with **WebFlux** (reactive, non-blocking)
- **[FireflyFramework](https://github.com/fireflyframework/)** -- Parent POM (`fireflyframework-parent`), BOM (`fireflyframework-bom` v26.01.01), and libraries:
  - `fireflyframework-web` -- Common web configurations
  - `fireflyframework-domain` -- Domain layer CQRS and saga support
  - `fireflyframework-utils` -- Shared utilities
  - `fireflyframework-validators` -- Validation framework
- **FireflyFramework Transactional Saga Engine** -- `@Saga`, `@SagaStep`, `@StepEvent` annotations with `SagaEngine` for orchestrating distributed transactions with compensation
- **FireflyFramework CQRS** -- `CommandBus` for command dispatch
- **Project Reactor** (`Mono`/`Flux`) -- Reactive streams throughout
- **MapStruct** -- Object mapping between layers
- **Lombok** -- Boilerplate reduction
- **SpringDoc OpenAPI** -- API documentation and Swagger UI
- **Micrometer + Prometheus** -- Metrics export
- **Spring Boot Actuator** -- Health checks and operational endpoints
- **Spring Cloud Config** -- Externalized configuration via config server
- **OpenAPI Generator** -- SDK generation from the OpenAPI spec (WebClient-based reactive client)

### Saga Workflows

| Saga | Steps | Description |
|------|-------|-------------|
| `RegisterDistributorSaga` | `registerDistributor` -> `registerTAndCTemplate` -> `registerTermsAndConditions` (depends on distributor + template) -> `registerAuditLog` (depends on distributor) -> `registerBranding` (depends on distributor) | Full distributor onboarding with compensating rollback for each step |
| `UpdateBrandingSaga` | `reviseBranding` | Updates branding attributes for an existing distributor |
| `SetDefaultBrandingSaga` | `setDefaultBranding` | Marks a branding configuration as the default |
| `UpdateTermsAndConditionsSaga` | `reviseTermsAndConditions` | Revises or versions terms & conditions |

### Domain Events

The service emits the following events via `@StepEvent`:

- `distributor.registered`
- `tAndCTemplate.registered`
- `termsAndConditions.registered`
- `auditLog.registered`
- `branding.registered`
- `branding.updated`
- `termsAndConditions.updated`

## Setup

### Prerequisites

- **Java 25**
- **Maven 3.9+**
- Access to the FireflyFramework Maven repository for parent POM and BOM dependencies
- Running instance of `common-platform-distributor-mgmt` service (or its API accessible at the configured base path)

### Environment Variables

| Variable | Default | Description |
|----------|---------|-------------|
| `CONFIG_SERVER_URL` | `https://app-firefly-config-server.dev.soon.es/` | Spring Cloud Config Server URL for externalized configuration |

### Application Configuration

The service imports its full configuration from Spring Cloud Config Server. The local `application.yaml` defines:

```yaml
spring:
  application:
    name: distributor-domain-branding
    version: 1.0.0
    description: Distributor Domain Branding Layer Application
    team:
      name: Firefly Software Solutions Inc
      email: dev@getfirefly.io
  config:
    import: "optional:configserver:${CONFIG_SERVER_URL:https://app-firefly-config-server.dev.soon.es/}"
```

Additional configuration provided by the config server typically includes:

- `api-configuration.common-platform.distributor-mgmt.base-path` -- Base URL for the downstream distributor management service
- Server port, logging levels, and profile-specific settings

### Build

```bash
mvn clean install
```

### Run

```bash
mvn -pl distributor-domain-branding-web spring-boot:run
```

Or run the packaged JAR:

```bash
java -jar distributor-domain-branding-web/target/distributor-domain-branding.jar
```

## API Endpoints

Base path: `/api/v1/distributors`

### Onboard Distributor

```
POST /api/v1/distributors
```

Onboards a new distributor with branding, terms & conditions template, terms & conditions, and audit log in a single saga transaction.

**Request Body** (`RegisterDistributorCommand`):
- `distributorInfo` -- Distributor profile (name, tax ID, address, contact details, locale, timezone)
- `termsAndConditionsTemplate` -- T&C template (name, category, content, version, approval/renewal settings)
- `termsAndConditions` -- T&C instance (title, content, version, effective/expiration dates, status)
- `auditLog` -- Audit entry (action: `CREATED`/`UPDATED`/`TERMINATED`, entity, IP address, user ID, timestamp)
- `branding` -- Branding config (logo URL, favicon URL, colors, font, theme: `LIGHT`/`DARK`/`CUSTOM`)

### Revise Branding

```
PUT /api/v1/distributors/{distributorId}/branding/{brandingId}
```

Revises an existing branding configuration (logo, colors, font family, theme).

### Set Default Branding

```
PUT /api/v1/distributors/{distributorId}/branding/{brandingId}/set-default
```

Marks a specific branding configuration as the default for the distributor.

### Revise Terms and Conditions

```
PUT /api/v1/distributors/{distributorId}/terms-and-conditions/{tcId}
```

Revises or versions a distributor's terms & conditions (content, effective dates, status, signing details).

### Common Headers

| Header | Required | Description |
|--------|----------|-------------|
| `X-Idempotency-Key` | No | Ensures identical requests are processed only once |
| `X-Party-ID` | Conditional | Client identifier (at least one identity header required) |
| `X-Employee-ID` | Conditional | Employee identifier |
| `X-Service-Account-ID` | Conditional | Service account identifier |
| `X-Auth-Roles` | No | Comma-separated roles (CUSTOMER, ADMIN, CUSTOMER_SUPPORT, SUPERVISOR, MANAGER, BRANCH_STAFF, SERVICE_ACCOUNT) |
| `X-Auth-Scopes` | No | Comma-separated OAuth2 scopes |
| `X-Request-ID` | No | Request traceability identifier |

## Development Guidelines

- Follow the CQRS pattern: commands for writes, queries for reads, dispatched via `CommandBus`
- All write operations must be implemented as saga workflows using `@Saga` and `@SagaStep` annotations
- Define compensating actions for each saga step that mutates state
- Use `@StepEvent` to emit domain events from saga steps
- Maintain constants in `DistributorConstants` (saga names, step IDs, event types) and `GlobalConstants` (context variable keys)
- Use `SagaContext` to pass variables between dependent saga steps
- Keep reactive chains unbroken -- return `Mono`/`Flux` throughout the stack
- Use MapStruct for object mapping between layers
- SDK is auto-generated from the OpenAPI spec; do not modify generated code directly
- Infrastructure clients are created via `ClientFactory` using the configured base path

## Monitoring

The service exposes the following operational endpoints via Spring Boot Actuator:

| Endpoint | Description |
|----------|-------------|
| `/actuator/health` | Application health status |
| `/actuator/info` | Application information |
| `/actuator/prometheus` | Prometheus metrics endpoint |

OpenAPI documentation is available at:

- **Swagger UI**: [http://localhost:{port}/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **API Docs (JSON)**: [http://localhost:{port}/v3/api-docs](http://localhost:8080/v3/api-docs)

## Repository

[https://github.com/firefly-oss/distributor-domain-branding](https://github.com/firefly-oss/distributor-domain-branding)
