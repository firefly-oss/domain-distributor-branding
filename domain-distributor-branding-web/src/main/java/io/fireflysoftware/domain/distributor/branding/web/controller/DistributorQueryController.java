/*
 * Copyright 2025 Firefly Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package io.fireflysoftware.domain.distributor.branding.web.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.fireflysoftware.core.distributor.sdk.api.DistributorApi;
import io.fireflysoftware.core.distributor.sdk.api.DistributorBrandingApi;
import io.fireflysoftware.core.distributor.sdk.model.DistributorBrandingDTO;
import io.fireflysoftware.core.distributor.sdk.model.DistributorDTO;
import io.fireflysoftware.core.distributor.sdk.model.FilterRequestDistributorBrandingDTO;
import io.fireflysoftware.core.distributor.sdk.model.FilterRequestDistributorDTO;
import io.fireflysoftware.core.distributor.sdk.model.PaginationRequest;
import io.fireflysoftware.core.distributor.sdk.model.PaginationResponse;
import io.fireflysoftware.domain.distributor.branding.core.distributor.commands.RegisterDistributorAuditLogCommand;
import io.fireflysoftware.domain.distributor.branding.core.distributor.queries.GetDistributorBrandingQuery;
import io.fireflysoftware.domain.distributor.branding.core.distributor.queries.GetDistributorProfileQuery;
import org.fireflyframework.cqrs.command.CommandBus;
import org.springframework.http.HttpStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fireflyframework.cqrs.query.QueryBus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/distributors")
@RequiredArgsConstructor
@Tag(name = "Distributor Query", description = "Query endpoints for distributor profile and branding")
public class DistributorQueryController {

    private static final ObjectMapper JSON = new ObjectMapper();

    private final QueryBus queryBus;
    private final CommandBus commandBus;
    private final DistributorApi distributorApi;
    private final DistributorBrandingApi distributorBrandingApi;

    @Operation(
            summary = "Get Distributor Profile",
            description = "Retrieve the profile of a distributor by its identifier."
    )
    @ApiResponse(responseCode = "200", description = "Distributor profile retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Distributor not found")
    @GetMapping("/{distributorId}/profile")
    public Mono<ResponseEntity<DistributorDTO>> getDistributorProfile(
            @PathVariable UUID distributorId) {
        log.debug("Fetching distributor profile for distributorId={}", distributorId);
        var query = GetDistributorProfileQuery.builder()
                .distributorId(distributorId)
                .build();
        return queryBus.<DistributorDTO>query(query)
                .map(ResponseEntity::ok);
    }

    @Operation(
            summary = "Update Distributor",
            description = "Update an existing distributor's profile."
    )
    @ApiResponse(responseCode = "200", description = "Distributor updated successfully")
    @PutMapping("/{distributorId}/profile")
    public Mono<ResponseEntity<DistributorDTO>> updateDistributor(
            @PathVariable UUID distributorId,
            @Valid @RequestBody DistributorDTO distributorDTO) {
        log.debug("Updating distributor profile for distributorId={}", distributorId);
        return distributorApi.updateDistributor(distributorId, distributorDTO, UUID.randomUUID().toString())
                .map(ResponseEntity::ok);
    }

    @Operation(
            summary = "Delete Distributor",
            description = "Delete a distributor by its identifier."
    )
    @ApiResponse(responseCode = "204", description = "Distributor deleted successfully")
    @DeleteMapping("/{distributorId}/profile")
    public Mono<ResponseEntity<Void>> deleteDistributor(@PathVariable UUID distributorId) {
        log.debug("Deleting distributor for distributorId={}", distributorId);
        return distributorApi.deleteDistributor(distributorId, UUID.randomUUID().toString())
                .then(Mono.just(ResponseEntity.noContent().<Void>build()));
    }

    @Operation(
            summary = "Get Distributor Branding",
            description = "Retrieve a specific branding configuration for a distributor."
    )
    @ApiResponse(responseCode = "200", description = "Distributor branding retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Distributor or branding not found")
    @GetMapping("/{distributorId}/branding/{brandingId}")
    public Mono<ResponseEntity<DistributorBrandingDTO>> getDistributorBranding(
            @PathVariable UUID distributorId,
            @PathVariable UUID brandingId) {
        log.debug("Fetching branding for distributorId={}, brandingId={}", distributorId, brandingId);
        var query = GetDistributorBrandingQuery.builder()
                .distributorId(distributorId)
                .brandingId(brandingId)
                .build();
        return queryBus.<DistributorBrandingDTO>query(query)
                .map(ResponseEntity::ok);
    }

    @Operation(
            summary = "List Distributor Brandings",
            description = "Retrieve all branding configurations for a distributor."
    )
    @ApiResponse(responseCode = "200", description = "Brandings listed successfully")
    @PostMapping("/{distributorId}/branding/filter")
    public Mono<ResponseEntity<Flux<DistributorBrandingDTO>>> filterDistributorBrandings(
            @PathVariable UUID distributorId,
            @Valid @RequestBody FilterRequestDistributorBrandingDTO filterRequest) {
        log.debug("Filtering brandings for distributorId={}", distributorId);
        return distributorBrandingApi
                .filterDistributorBrandingsWithResponseSpec(distributorId, filterRequest, UUID.randomUUID().toString())
                .bodyToFlux(DistributorBrandingDTO.class)
                .collectList()
                .map(list -> ResponseEntity.ok(Flux.fromIterable(list)));
    }

    @Operation(
            summary = "Create Distributor Branding",
            description = "Create a new branding configuration for a distributor."
    )
    @ApiResponse(responseCode = "200", description = "Branding created successfully")
    @PostMapping("/{distributorId}/branding")
    public Mono<ResponseEntity<DistributorBrandingDTO>> createDistributorBranding(
            @PathVariable UUID distributorId,
            @Valid @RequestBody DistributorBrandingDTO brandingDTO) {
        log.debug("Creating branding for distributorId={}", distributorId);
        return distributorBrandingApi.createDistributorBranding(distributorId, brandingDTO, UUID.randomUUID().toString())
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Filter distributors", description = "Returns distributors from the distributor core.")
    @PostMapping("/filter")
    public Mono<ResponseEntity<PaginationResponse>> filterDistributors() {
        FilterRequestDistributorDTO request = new FilterRequestDistributorDTO();
        request.setPagination(new PaginationRequest().pageNumber(0).pageSize(200));
        return distributorApi.filterDistributorsWithResponseSpec(request, UUID.randomUUID().toString())
                .bodyToMono(String.class)
                .map(DistributorQueryController::page)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Record a staff attachment", description = "Writes an audit log row through the distributor core.")
    @PostMapping("/{distributorId}/audit-logs")
    public Mono<ResponseEntity<Map<String, Object>>> recordAuditLog(
            @PathVariable UUID distributorId,
            @Valid @RequestBody RegisterDistributorAuditLogCommand command) {
        return commandBus.<UUID>send(command.withDistributorId(distributorId))
                .flatMap(id -> {
                    if (id == null) {
                        return Mono.error(new IllegalStateException("Distributor audit log was not recorded"));
                    }
                    Map<String, Object> recorded = new LinkedHashMap<>();
                    recorded.put("id", id);
                    recorded.put("distributorId", distributorId);
                    recorded.put("action", command.getAction());
                    recorded.put("entity", command.getEntity());
                    recorded.put("entityId", command.getEntityId());
                    recorded.put("auditMetadata", command.getAuditMetadata());
                    recorded.put("userId", command.getUserId());
                    recorded.put("auditTimestamp", command.getAuditTimestamp());
                    return Mono.just(ResponseEntity.status(HttpStatus.CREATED).body(recorded));
                });
    }

    private static PaginationResponse page(String raw) {
        PaginationResponse page = new PaginationResponse();
        page.setContent(new ArrayList<>());
        page.setTotalElements(0L);
        page.setTotalPages(0);
        page.setCurrentPage(0);
        if (raw == null || raw.isBlank()) {
            return page;
        }
        try {
            JsonNode body = JSON.readTree(raw);
            JsonNode rows = body.isArray() ? body : firstArray(body);
            if (rows != null && rows.isArray()) {
                List<Object> content = new ArrayList<>();
                rows.forEach(row -> content.add(JSON.convertValue(row, Object.class)));
                page.setContent(content);
                page.setTotalElements(body.path("totalElements").isNumber() ? body.path("totalElements").asLong() : content.size());
                page.setTotalPages(body.path("totalPages").isNumber() ? body.path("totalPages").asInt() : (content.isEmpty() ? 0 : 1));
                page.setCurrentPage(body.path("currentPage").isNumber() ? body.path("currentPage").asInt() : 0);
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Distributor page was not readable");
        }
        return page;
    }

    private static JsonNode firstArray(JsonNode body) {
        for (String field : new String[]{"content", "items", "data", "results"}) {
            if (body.path(field).isArray()) {
                return body.path(field);
            }
        }
        return null;
    }

    @Operation(
            summary = "Delete Distributor Branding",
            description = "Delete a specific branding configuration for a distributor."
    )
    @ApiResponse(responseCode = "204", description = "Branding deleted successfully")
    @DeleteMapping("/{distributorId}/branding/{brandingId}")
    public Mono<ResponseEntity<Void>> deleteDistributorBranding(
            @PathVariable UUID distributorId,
            @PathVariable UUID brandingId) {
        log.debug("Deleting branding for distributorId={}, brandingId={}", distributorId, brandingId);
        return distributorBrandingApi.deleteDistributorBranding(distributorId, brandingId, UUID.randomUUID().toString())
                .then(Mono.just(ResponseEntity.noContent().<Void>build()));
    }
}
