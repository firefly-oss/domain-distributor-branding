package com.firefly.domain.distributor.branding.web.controller;

import com.firefly.core.distributor.sdk.model.DistributorAgencyDTO;
import com.firefly.core.distributor.sdk.model.PaginationResponse;
import com.firefly.domain.distributor.branding.core.distributor.commands.CreateAgencyCommand;
import com.firefly.domain.distributor.branding.core.distributor.commands.UpdateAgencyCommand;
import com.firefly.domain.distributor.branding.core.distributor.services.DistributorNetworkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/distributors/{distributorId}/agencies")
@RequiredArgsConstructor
@Tag(name = "Agency", description = "CRUD operations for distributor agencies")
public class AgencyController {

    private final DistributorNetworkService distributorNetworkService;

    @Operation(summary = "List Agencies", description = "List all agencies for a distributor.")
    @GetMapping
    public Mono<ResponseEntity<PaginationResponse>> listAgencies(@PathVariable UUID distributorId) {
        return distributorNetworkService.listAgencies(distributorId)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Create Agency", description = "Create a new agency for a distributor.")
    @PostMapping
    public Mono<ResponseEntity<UUID>> createAgency(
            @PathVariable UUID distributorId,
            @Valid @RequestBody CreateAgencyCommand command) {
        return distributorNetworkService.createAgency(command.withDistributorId(distributorId))
                .map(id -> ResponseEntity.status(HttpStatus.CREATED).body(id));
    }

    @Operation(summary = "Get Agency", description = "Retrieve a single agency by its identifier.")
    @GetMapping("/{agencyId}")
    public Mono<ResponseEntity<DistributorAgencyDTO>> getAgency(
            @PathVariable UUID distributorId,
            @PathVariable UUID agencyId) {
        return distributorNetworkService.getAgency(distributorId, agencyId)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Update Agency", description = "Update an existing agency.")
    @PutMapping("/{agencyId}")
    public Mono<ResponseEntity<UUID>> updateAgency(
            @PathVariable UUID distributorId,
            @PathVariable UUID agencyId,
            @Valid @RequestBody UpdateAgencyCommand command) {
        return distributorNetworkService.updateAgency(command.withDistributorId(distributorId).withId(agencyId))
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Delete Agency", description = "Delete an agency from a distributor.")
    @DeleteMapping("/{agencyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<ResponseEntity<Void>> deleteAgency(
            @PathVariable UUID distributorId,
            @PathVariable UUID agencyId) {
        return distributorNetworkService.deleteAgency(distributorId, agencyId)
                .thenReturn(ResponseEntity.noContent().build());
    }
}
