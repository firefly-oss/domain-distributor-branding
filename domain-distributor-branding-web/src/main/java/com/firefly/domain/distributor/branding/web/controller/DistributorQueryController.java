package com.firefly.domain.distributor.branding.web.controller;

import com.firefly.core.distributor.sdk.api.DistributorApi;
import com.firefly.core.distributor.sdk.api.DistributorBrandingApi;
import com.firefly.core.distributor.sdk.model.DistributorBrandingDTO;
import com.firefly.core.distributor.sdk.model.DistributorDTO;
import com.firefly.core.distributor.sdk.model.FilterRequestDistributorBrandingDTO;
import com.firefly.domain.distributor.branding.core.distributor.queries.GetDistributorBrandingQuery;
import com.firefly.domain.distributor.branding.core.distributor.queries.GetDistributorProfileQuery;
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

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/distributors")
@RequiredArgsConstructor
@Tag(name = "Distributor Query", description = "Query endpoints for distributor profile and branding")
public class DistributorQueryController {

    private final QueryBus queryBus;
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
