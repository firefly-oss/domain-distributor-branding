# domain-distributor-branding

> Reactive domain-layer microservice that manages the full lifecycle of distributor branding, terms and conditions, network hierarchy (agencies, agents, territories), operations, and configurations.

## Table of Contents
- [Overview](#overview)
- [Architecture](#architecture)
- [Module Structure](#module-structure)
- [API Endpoints](#api-endpoints)
- [Domain Logic](#domain-logic)
- [Dependencies](#dependencies)
- [Configuration](#configuration)
- [Running Locally](#running-locally)
- [Testing](#testing)

---

## Overview

`domain-distributor-branding` is a Spring WebFlux microservice that acts as the domain branding layer within the Firefly platform. It owns the onboarding and ongoing management of distributors — covering their visual identity (branding), legal agreements (terms and conditions), and distribution network (agencies, agents, authorized territories, operations, and configurations).

All mutations are expressed as commands dispatched through the Firefly CQRS `CommandBus` and are orchestrated using the `SagaEngine` when a business operation spans multiple downstream calls. All queries travel through the `QueryBus`, with a configurable query cache (default TTL: 15 minutes). Events are published to Kafka on the `domain-layer` topic after each saga step completes.

The service delegates all persistence and data retrieval to the `core-common-distributor-mgmt-sdk` — a generated REST client targeting the `common-platform-distributor-mgmt` upstream service. It never owns a database directly; instead, every command handler calls the appropriate upstream API method and returns the resulting resource identifier.

---

## Architecture

The service follows a **CQRS + Saga orchestration** pattern layered over a reactive WebFlux stack:

```
HTTP Client
    │
    ▼
[domain-distributor-branding-web]        ← Spring WebFlux controllers, OpenAPI docs
    │
    ▼
[domain-distributor-branding-interfaces] ← shared DTOs, command/query contracts
    │
    ▼
[domain-distributor-branding-core]       ← service facades, sagas, command/query handlers
    │
    ▼
[domain-distributor-branding-infra]      ← ClientFactory, SDK wiring, config properties
    │
    ▼
[core-common-distributor-mgmt-sdk]       ← generated REST client (upstream)
    │
    ▼
common-platform-distributor-mgmt         ← upstream persistence service
```

Kafka is used as the event bus. Each `@SagaStep` is annotated with `@StepEvent`, which publishes a domain event to the `domain-layer` topic upon successful completion. Saga compensation (rollback) executes in reverse step order when any step fails.

---

## Module Structure

| Module | Purpose |
|--------|---------|
| `domain-distributor-branding-interfaces` | Shared internal contracts: commands, queries, and service interfaces consumed by both `-core` and `-web` |
| `domain-distributor-branding-core` | Business logic: service implementations, CQRS command/query handlers, and saga workflow definitions |
| `domain-distributor-branding-infra` | Infrastructure wiring: `ClientFactory` that instantiates all upstream API clients from `core-common-distributor-mgmt-sdk`, and `DistributorCatalogProperties` for base-path binding |
| `domain-distributor-branding-web` | Spring Boot application entry point, REST controllers, SpringDoc OpenAPI configuration, Actuator, and Prometheus metrics |
| `domain-distributor-branding-sdk` | Auto-generated reactive WebClient SDK (via `openapi-generator-maven-plugin`) built from this service's own OpenAPI spec; consumed by downstream callers |

---

## API Endpoints

All endpoints are served under the `/api/v1` prefix. Interactive documentation is available at `/swagger-ui.html` and the raw spec at `/v3/api-docs`.

### Distributor

| Method | Path | Description | Status Code |
|--------|------|-------------|-------------|
| `POST` | `/api/v1/distributors` | Onboard a new distributor (branding + T&C in one saga) | `200 OK` |
| `PUT` | `/api/v1/distributors/{distributorId}/branding/{brandingId}` | Revise an existing branding configuration | `200 OK` |
| `PUT` | `/api/v1/distributors/{distributorId}/branding/{brandingId}/set-default` | Mark a branding entry as the distributor's default | `200 OK` |
| `PUT` | `/api/v1/distributors/{distributorId}/terms-and-conditions/{tcId}` | Revise (version) an existing terms-and-conditions entry | `200 OK` |

### Agency

| Method | Path | Description | Status Code |
|--------|------|-------------|-------------|
| `GET` | `/api/v1/distributors/{distributorId}/agencies` | List all agencies for a distributor | `200 OK` |
| `POST` | `/api/v1/distributors/{distributorId}/agencies` | Create a new agency | `201 Created` |
| `GET` | `/api/v1/distributors/{distributorId}/agencies/{agencyId}` | Retrieve a single agency | `200 OK` |
| `PUT` | `/api/v1/distributors/{distributorId}/agencies/{agencyId}` | Update an existing agency | `200 OK` |
| `DELETE` | `/api/v1/distributors/{distributorId}/agencies/{agencyId}` | Delete an agency | `204 No Content` |

### Agent

| Method | Path | Description | Status Code |
|--------|------|-------------|-------------|
| `GET` | `/api/v1/distributors/{distributorId}/agents` | List all agents for a distributor | `200 OK` |
| `POST` | `/api/v1/distributors/{distributorId}/agents` | Create a new agent | `201 Created` |
| `GET` | `/api/v1/distributors/{distributorId}/agents/{agentId}` | Retrieve a single agent | `200 OK` |
| `PUT` | `/api/v1/distributors/{distributorId}/agents/{agentId}` | Update an existing agent | `200 OK` |
| `DELETE` | `/api/v1/distributors/{distributorId}/agents/{agentId}` | Delete an agent | `204 No Content` |

### Agent-Agency Assignments

| Method | Path | Description | Status Code |
|--------|------|-------------|-------------|
| `GET` | `/api/v1/distributors/{distributorId}/agent-agencies` | List all agent-agency assignments | `200 OK` |
| `POST` | `/api/v1/distributors/{distributorId}/agent-agencies` | Assign an agent to an agency | `201 Created` |
| `DELETE` | `/api/v1/distributors/{distributorId}/agent-agencies/{agentAgencyId}` | Remove an agent-agency assignment | `204 No Content` |

### Terms and Conditions

| Method | Path | Description | Status Code |
|--------|------|-------------|-------------|
| `GET` | `/api/v1/distributors/{distributorId}/terms-and-conditions` | List all T&C versions for a distributor | `200 OK` |
| `POST` | `/api/v1/distributors/{distributorId}/terms-and-conditions` | Create a new T&C entry | `201 Created` |
| `GET` | `/api/v1/distributors/{distributorId}/terms-and-conditions/active` | Retrieve the currently active T&C | `200 OK` |
| `GET` | `/api/v1/distributors/{distributorId}/terms-and-conditions/latest` | Retrieve the most recently created T&C | `200 OK` |
| `GET` | `/api/v1/distributors/{distributorId}/terms-and-conditions/has-active-signed` | Check whether active signed T&C exist | `200 OK` |
| `GET` | `/api/v1/distributors/{distributorId}/terms-and-conditions/{tcId}` | Retrieve a specific T&C entry | `200 OK` |
| `DELETE` | `/api/v1/distributors/{distributorId}/terms-and-conditions/{tcId}` | Delete a T&C entry | `204 No Content` |
| `PATCH` | `/api/v1/distributors/{distributorId}/terms-and-conditions/{tcId}/sign` | Sign a T&C entry (`?signedBy={uuid}`) | `200 OK` |
| `PATCH` | `/api/v1/distributors/{distributorId}/terms-and-conditions/{tcId}/activate` | Activate a T&C entry (`?activatedBy={uuid}`) | `200 OK` |
| `PATCH` | `/api/v1/distributors/{distributorId}/terms-and-conditions/{tcId}/deactivate` | Deactivate a T&C entry (`?deactivatedBy={uuid}`) | `200 OK` |

### Operations

| Method | Path | Description | Status Code |
|--------|------|-------------|-------------|
| `GET` | `/api/v1/distributors/{distributorId}/operations` | List all operations for a distributor | `200 OK` |
| `POST` | `/api/v1/distributors/{distributorId}/operations` | Create a new operation | `201 Created` |
| `GET` | `/api/v1/distributors/{distributorId}/operations/{operationId}` | Retrieve a single operation | `200 OK` |
| `PUT` | `/api/v1/distributors/{distributorId}/operations/{operationId}` | Update an existing operation | `200 OK` |
| `DELETE` | `/api/v1/distributors/{distributorId}/operations/{operationId}` | Delete an operation | `204 No Content` |
| `PATCH` | `/api/v1/distributors/{distributorId}/operations/{operationId}/activate` | Activate an operation (`?activatedBy={uuid}`) | `200 OK` |
| `PATCH` | `/api/v1/distributors/{distributorId}/operations/{operationId}/deactivate` | Deactivate an operation (`?deactivatedBy={uuid}`) | `200 OK` |
| `GET` | `/api/v1/distributors/{distributorId}/operations/can-operate` | Check operational eligibility (`?operationId=&locationId=`) | `200 OK` |

### Configurations

| Method | Path | Description | Status Code |
|--------|------|-------------|-------------|
| `GET` | `/api/v1/distributors/{distributorId}/configurations` | List all configurations for a distributor | `200 OK` |
| `POST` | `/api/v1/distributors/{distributorId}/configurations` | Create a new configuration | `201 Created` |
| `GET` | `/api/v1/distributors/{distributorId}/configurations/{configurationId}` | Retrieve a single configuration | `200 OK` |
| `PUT` | `/api/v1/distributors/{distributorId}/configurations/{configurationId}` | Update an existing configuration | `200 OK` |
| `DELETE` | `/api/v1/distributors/{distributorId}/configurations/{configurationId}` | Delete a configuration | `204 No Content` |

### Territories

| Method | Path | Description | Status Code |
|--------|------|-------------|-------------|
| `GET` | `/api/v1/distributors/{distributorId}/territories` | List all authorized territories for a distributor | `200 OK` |
| `POST` | `/api/v1/distributors/{distributorId}/territories` | Create a new authorized territory | `201 Created` |
| `GET` | `/api/v1/distributors/{distributorId}/territories/{territoryId}` | Retrieve a single territory | `200 OK` |
| `PUT` | `/api/v1/distributors/{distributorId}/territories/{territoryId}` | Update an existing territory | `200 OK` |
| `DELETE` | `/api/v1/distributors/{distributorId}/territories/{territoryId}` | Delete an authorized territory | `204 No Content` |

---

## Domain Logic

### Services

The core layer exposes five service facades, each responsible for a specific domain concern. Every implementation dispatches to the Firefly `CommandBus` or `QueryBus`; saga-orchestrated operations additionally invoke the `SagaEngine`.

| Service | Implementation | Responsibility |
|---------|---------------|----------------|
| `DistributorService` | `DistributorServiceImpl` | Distributor onboarding, branding revisions, default branding selection, T&C versioning — all saga-driven |
| `DistributorNetworkService` | `DistributorNetworkServiceImpl` | Agency, agent, territory, and agent-agency assignment management |
| `DistributorTermsService` | `DistributorTermsServiceImpl` | Full T&C lifecycle: create, list, retrieve, sign, activate, deactivate, delete |
| `DistributorOperationsService` | `DistributorOperationsServiceImpl` | Operation lifecycle management and the `canOperate` eligibility check |
| `DistributorConfigService` | `DistributorConfigServiceImpl` | Distributor-scoped configuration management |

Every command handler extends `CommandHandler<C, R>`, is annotated with `@CommandHandlerComponent`, calls exactly one upstream SDK method, and returns a `UUID` or DTO wrapped in `Mono`. Query handlers follow the same structural pattern via `QueryHandler`.

---

### Sagas

#### `RegisterDistributorSaga`

Orchestrates the complete onboarding of a new distributor. Steps that have no declared `dependsOn` run in parallel; steps with dependencies run after all declared predecessors complete.

```
registerDistributor (Layer 0)          → event: distributor.registered
        │
        ├──► registerTAndCTemplate (Layer 0, parallel)   → event: tAndCTemplate.registered
        │              │
        │    registerTermsAndConditions (Layer 1, depends on registerDistributor + registerTAndCTemplate)
        │                                                → event: termsAndConditions.registered
        │
        ├──► registerAuditLog (Layer 1, depends on registerDistributor)
        │                                                → event: auditLog.registered
        │
        └──► registerBranding (Layer 1, depends on registerDistributor)
                                                         → event: branding.registered
```

Compensation runs in reverse: `removeBranding`, `removeAuditLog`, `removeTermsAndConditions`, `removeTAndCTemplate`, `removeDistributor`. The `ExecutionContext` carries `distributorId`, `templateId`, `auditLogId`, and `brandingId` across steps; downstream steps read these via `ctx.getVariableAs(key, Type.class)`.

---

#### `UpdateBrandingSaga`

Single-step saga that revises an existing branding entry.

```
updateBranding   → event: branding.updated
```

---

#### `SetDefaultBrandingSaga`

Single-step saga that marks a branding entry as the distributor's default.

```
setDefaultBranding   → event: branding.updated
```

---

#### `UpdateTermsAndConditionsSaga`

Single-step saga that revises an existing terms-and-conditions entry.

```
updateTermsAndConditions   → event: termsAndConditions.updated
```

---

#### `RegisterAgencySaga`

Orchestrates creation of a complete agency setup: authorized territory, agency entity, and linked operation. The `agencyId` produced by step 2 is injected into `CreateOperationCommand` via the `ExecutionContext` before step 3 executes.

```
createTerritory (Layer 0)              → event: territory.created
        │
createAgency (Layer 1, depends on createTerritory)
                                       → event: agency.created
        │
createOperation (Layer 2, depends on createAgency)
                                       → event: operation.created
```

Compensation: `deleteOperation` → `deleteAgency` → `deleteTerritory`.

---

#### `RegisterAgentSaga`

Orchestrates atomic creation of an agent and its agency assignment. The `distributor_agent_agency` table requires both `agent_id` and `agency_id` to be non-null; this saga ensures both records are created or both are rolled back.

```
createAgent (Layer 0)                  → event: agent.created
        │
assignAgentAgency (Layer 1, depends on createAgent)
                                       → event: agentAgency.assigned
```

The `agentId` from context is injected into `AssignAgentAgencyCommand` before dispatch. Compensation: `removeAgentAgency` → `deleteAgent`.

---

### CQRS Handler Reference

All handlers live in `domain-distributor-branding-core` and delegate to the upstream SDK client registered by `ClientFactory`.

**Command Handlers**

| Handler | Command | Upstream API Client |
|---------|---------|---------------------|
| `RegisterDistributorInfoHandler` | `RegisterDistributorInfoCommand` | `DistributorApi` |
| `RemoveDistributorInfoHandler` | `RemoveDistributorInfoCommand` | `DistributorApi` |
| `RegisterBrandingHandler` | `RegisterDistributorBrandingCommand` | `DistributorBrandingApi` |
| `ReviseBrandingHandler` | `ReviseBrandingCommand` | `DistributorBrandingApi` |
| `SetDefaultBrandingHandler` | `SetDefaultBrandingCommand` | `DistributorBrandingApi` |
| `RemoveBrandingHandler` | `RemoveBrandingCommand` | `DistributorBrandingApi` |
| `RegisterTAndCTemplateHandler` | `RegisterTandCTemplateCommand` | `TermsAndConditionsTemplatesApi` |
| `RemoveTAndCTemplateHandler` | `RemoveTAndCTemplateCommand` | `TermsAndConditionsTemplatesApi` |
| `RegisterTermsAndConditionsHandler` | `RegisterTermsAndConditionsCommand` | `DistributorTermsAndConditionsApi` |
| `ReviseTermsAndConditionsHandler` | `ReviseTermsAndConditionsCommand` | `DistributorTermsAndConditionsApi` |
| `CreateTermsAndConditionsHandler` | `CreateTermsAndConditionsCommand` | `DistributorTermsAndConditionsApi` |
| `RemoveTermsAndConditionsHandler` | `RemoveTermsAndConditionsCommand` | `DistributorTermsAndConditionsApi` |
| `SignTermsAndConditionsHandler` | `SignTermsAndConditionsCommand` | `DistributorTermsAndConditionsApi` |
| `ActivateTermsAndConditionsHandler` | `ActivateTermsAndConditionsCommand` | `DistributorTermsAndConditionsApi` |
| `DeactivateTermsAndConditionsHandler` | `DeactivateTermsAndConditionsCommand` | `DistributorTermsAndConditionsApi` |
| `RegisterAuditLogHandler` | `RegisterDistributorAuditLogCommand` | `DistributorAuditLogApi` |
| `RemoveAuditLogHandler` | `RemoveAuditLogCommand` | `DistributorAuditLogApi` |
| `CreateTerritoryHandler` | `CreateTerritoryCommand` | `DistributorAuthorizedTerritoriesApi` |
| `UpdateTerritoryHandler` | `UpdateTerritoryCommand` | `DistributorAuthorizedTerritoriesApi` |
| `DeleteTerritoryHandler` | `DeleteTerritoryCommand` | `DistributorAuthorizedTerritoriesApi` |
| `CreateAgencyHandler` | `CreateAgencyCommand` | `DistributorAgenciesApi` |
| `UpdateAgencyHandler` | `UpdateAgencyCommand` | `DistributorAgenciesApi` |
| `DeleteAgencyHandler` | `DeleteAgencyCommand` | `DistributorAgenciesApi` |
| `CreateAgentHandler` | `CreateAgentCommand` | `DistributorAgentsApi` |
| `UpdateAgentHandler` | `UpdateAgentCommand` | `DistributorAgentsApi` |
| `DeleteAgentHandler` | `DeleteAgentCommand` | `DistributorAgentsApi` |
| `AssignAgentAgencyHandler` | `AssignAgentAgencyCommand` | `DistributorAgentAgencyApi` |
| `RemoveAgentAgencyHandler` | `RemoveAgentAgencyCommand` | `DistributorAgentAgencyApi` |
| `CreateOperationHandler` | `CreateOperationCommand` | `DistributorOperationsApi` |
| `UpdateOperationHandler` | `UpdateOperationCommand` | `DistributorOperationsApi` |
| `DeleteOperationHandler` | `DeleteOperationCommand` | `DistributorOperationsApi` |
| `ActivateOperationHandler` | `ActivateOperationCommand` | `DistributorOperationsApi` |
| `DeactivateOperationHandler` | `DeactivateOperationCommand` | `DistributorOperationsApi` |
| `CreateConfigurationHandler` | `CreateConfigurationCommand` | `DistributorConfigurationsApi` |
| `UpdateConfigurationHandler` | `UpdateConfigurationCommand` | `DistributorConfigurationsApi` |
| `DeleteConfigurationHandler` | `DeleteConfigurationCommand` | `DistributorConfigurationsApi` |

**Query Handlers**

| Handler | Query | Upstream API Client |
|---------|-------|---------------------|
| `GetAgencyHandler` | `GetAgencyQuery` | `DistributorAgenciesApi` |
| `ListAgenciesHandler` | `ListAgenciesQuery` | `DistributorAgenciesApi` |
| `GetAgentHandler` | `GetAgentQuery` | `DistributorAgentsApi` |
| `ListAgentsHandler` | `ListAgentsQuery` | `DistributorAgentsApi` |
| `ListAgentAgenciesHandler` | `ListAgentAgenciesQuery` | `DistributorAgentAgencyApi` |
| `GetTerritoryHandler` | `GetTerritoryQuery` | `DistributorAuthorizedTerritoriesApi` |
| `ListTerritoriesHandler` | `ListTerritoriesQuery` | `DistributorAuthorizedTerritoriesApi` |
| `ListTermsAndConditionsHandler` | `ListTermsAndConditionsQuery` | `DistributorTermsAndConditionsApi` |
| `GetTermsAndConditionsDetailHandler` | `GetTermsAndConditionsDetailQuery` | `DistributorTermsAndConditionsApi` |
| `GetActiveTermsAndConditionsHandler` | `GetActiveTermsAndConditionsQuery` | `DistributorTermsAndConditionsApi` |
| `GetLatestTermsAndConditionsHandler` | `GetLatestTermsAndConditionsQuery` | `DistributorTermsAndConditionsApi` |
| `HasActiveSignedTermsHandler` | `HasActiveSignedTermsQuery` | `DistributorTermsAndConditionsApi` |
| `GetOperationHandler` | `GetOperationQuery` | `DistributorOperationsApi` |
| `ListOperationsHandler` | `ListOperationsQuery` | `DistributorOperationsApi` |
| `CanOperateHandler` | `CanOperateQuery` | `DistributorOperationsApi` |
| `GetConfigurationHandler` | `GetConfigurationQuery` | `DistributorConfigurationsApi` |
| `ListConfigurationsHandler` | `ListConfigurationsQuery` | `DistributorConfigurationsApi` |
| `ReviewBrandingAuditLogsQueryHandler` | `ReviewBrandingAuditLogsQuery` | `DistributorAuditLogApi` |

---

## Dependencies

### Upstream (consumes)

| Artifact | Role |
|----------|------|
| `core-common-distributor-mgmt-sdk` | Generated REST client for `common-platform-distributor-mgmt`; provides `DistributorApi`, `DistributorBrandingApi`, `DistributorTermsAndConditionsApi`, `DistributorAuditLogApi`, `DistributorAuthorizedTerritoriesApi`, `DistributorAgenciesApi`, `DistributorAgentsApi`, `DistributorAgentAgencyApi`, `DistributorOperationsApi`, `DistributorConfigurationsApi`, and `TermsAndConditionsTemplatesApi` |
| `fireflyframework-starter-domain` | Firefly CQRS engine (`CommandBus`, `QueryBus`), Saga engine (`SagaEngine`), `@Saga`/`@SagaStep`/`@StepEvent` annotations, and step-event infrastructure |
| `fireflyframework-web` | Common web library configuration (error handling, global filters) |
| `fireflyframework-utils` | Shared utility classes |
| `fireflyframework-validators` | Shared validation helpers |

### Downstream (consumed by)

| Artifact | Role |
|----------|------|
| `domain-distributor-branding-sdk` | Auto-generated reactive WebClient SDK built from this service's OpenAPI spec using `openapi-generator-maven-plugin` (library: `webclient`, reactive mode). Other Firefly domain services add this artifact as a Maven dependency to call this service's REST API. |

---

## Configuration

All properties are defined in `domain-distributor-branding-web/src/main/resources/application.yaml`.

| Property | Default / Env Override | Description |
|----------|------------------------|-------------|
| `spring.application.name` | `domain-distributor-branding` | Service name used in actuator info and OpenAPI title |
| `spring.application.version` | `1.0.0` | Service version exposed in the OpenAPI definition |
| `spring.application.description` | `Distributor Domain Branding Layer Application` | OpenAPI description |
| `spring.application.team.name` | `Firefly Software Solutions Inc` | Team name shown in OpenAPI contact |
| `spring.application.team.email` | `dev@getfirefly.io` | Team email shown in OpenAPI contact |
| `spring.threads.virtual.enabled` | `true` | Enables Java virtual threads |
| `server.address` | `localhost` / `SERVER_ADDRESS` | Bind address for the HTTP server |
| `server.port` | `8080` / `SERVER_PORT` | HTTP server port |
| `server.shutdown` | `graceful` | Graceful shutdown mode |
| `endpoints.core.common-platform-distributor-mgmt` | `http://localhost:8080` / `ENDPOINT_CORE_DISTRIBUTOR_MGMT` | Base URL of the upstream distributor management service |
| `api-configuration.common-platform.distributor-mgmt.base-path` | Resolved from `endpoints.core.common-platform-distributor-mgmt` | Injected into `DistributorCatalogProperties` and used by `ClientFactory` to configure all upstream API clients |
| `firefly.cqrs.enabled` | `true` | Activates the Firefly CQRS infrastructure |
| `firefly.cqrs.command.timeout` | `30s` | Maximum time allowed for a command to complete |
| `firefly.cqrs.command.metrics-enabled` | `true` | Enables Micrometer metrics for commands |
| `firefly.cqrs.command.tracing-enabled` | `true` | Enables distributed tracing for commands |
| `firefly.cqrs.query.timeout` | `15s` | Maximum time allowed for a query to complete |
| `firefly.cqrs.query.caching-enabled` | `true` | Activates query result caching |
| `firefly.cqrs.query.cache-ttl` | `15m` | Time-to-live for cached query results |
| `firefly.saga.performance.enabled` | `true` | Enables saga-level performance metrics |
| `firefly.eda.enabled` | `true` | Activates the event-driven architecture infrastructure |
| `firefly.eda.default-publisher-type` | `KAFKA` | Event publisher backend |
| `firefly.eda.default-connection-id` | `default` | Logical connection identifier for the default publisher |
| `firefly.eda.publishers.kafka.default.enabled` | `true` | Enables the default Kafka publisher |
| `firefly.eda.publishers.kafka.default.default-topic` | `domain-layer` | Kafka topic for domain events emitted by `@StepEvent` |
| `firefly.eda.publishers.kafka.default.bootstrap-servers` | `localhost:9092` / `FIREFLY_KAFKA_BOOTSTRAP_SERVERS` | Kafka bootstrap server addresses |
| `firefly.stepevents.enabled` | `true` | Enables step-level event emission from saga steps |
| `springdoc.api-docs.enabled` | `true` | Activates OpenAPI spec generation |
| `springdoc.api-docs.path` | `/v3/api-docs` | Path for the raw OpenAPI JSON/YAML spec |
| `springdoc.swagger-ui.path` | `/swagger-ui.html` | Path for the Swagger UI |
| `springdoc.paths-to-match` | `/api/**` | Only endpoints matching this pattern appear in the spec |
| `management.endpoints.web.exposure.include` | `health,info,prometheus` | Exposed actuator endpoints |
| `management.health.redis.enabled` | `false` | Disables the Redis health indicator |
| `management.health.livenessState.enabled` | `true` | Exposes Kubernetes liveness probe |
| `management.health.readinessState.enabled` | `true` | Exposes Kubernetes readiness probe |
| `logging.level.root` | `INFO` | Root logging level |
| `logging.level.com.firefly` | `DEBUG` | Debug logging for all Firefly packages |
| `logging.level.org.springframework.r2dbc` | `DEBUG` | R2DBC query logging |
| `logging.level.org.flywaydb` | `DEBUG` | Flyway migration logging |

---

## Running Locally

Ensure that `common-platform-distributor-mgmt` is reachable at the address configured via `ENDPOINT_CORE_DISTRIBUTOR_MGMT` (defaults to `http://localhost:8080`), and that a Kafka broker is available at the address configured via `FIREFLY_KAFKA_BOOTSTRAP_SERVERS` (defaults to `localhost:9092`).

```bash
mvn clean install -DskipTests
cd /Users/casanchez/Desktop/firefly-oss/domain-distributor-branding
mvn spring-boot:run -pl domain-distributor-branding-web
```

Server port: **8080** (override with the `SERVER_PORT` environment variable)

Once running:
- Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- OpenAPI spec: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)
- Health: [http://localhost:8080/actuator/health](http://localhost:8080/actuator/health)
- Prometheus metrics: [http://localhost:8080/actuator/prometheus](http://localhost:8080/actuator/prometheus)

---

## Testing

```bash
mvn clean verify
```

The test suite uses `spring-boot-starter-test` and `reactor-test` (StepVerifier). The `-web` module includes `OpenApiGenApplication`, which is executed during the build to generate the OpenAPI specification consumed by the `-sdk` module's `openapi-generator-maven-plugin` execution. Running `mvn clean install` in the root builds all modules in dependency order and produces the generated SDK sources under `domain-distributor-branding-sdk/target/generated-sources`.
