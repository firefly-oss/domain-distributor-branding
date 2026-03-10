package com.firefly.domain.distributor.branding.web.controller;

import com.firefly.core.distributor.sdk.model.DistributorAuthorizedTerritoryDTO;
import com.firefly.core.distributor.sdk.model.PaginationResponse;
import com.firefly.domain.distributor.branding.core.distributor.commands.CreateTerritoryCommand;
import com.firefly.domain.distributor.branding.core.distributor.commands.UpdateTerritoryCommand;
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
@RequestMapping("/api/v1/distributors/{distributorId}/territories")
@RequiredArgsConstructor
@Tag(name = "Territory", description = "CRUD operations for distributor authorized territories")
public class TerritoryController {

    private final DistributorNetworkService distributorNetworkService;

    @Operation(summary = "List Territories", description = "List all authorized territories for a distributor.")
    @GetMapping
    public Mono<ResponseEntity<PaginationResponse>> listTerritories(@PathVariable UUID distributorId) {
        return distributorNetworkService.listTerritories(distributorId)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Create Territory", description = "Create a new authorized territory for a distributor.")
    @PostMapping
    public Mono<ResponseEntity<UUID>> createTerritory(
            @PathVariable UUID distributorId,
            @Valid @RequestBody CreateTerritoryCommand command) {
        return distributorNetworkService.createTerritory(command.withDistributorId(distributorId))
                .map(id -> ResponseEntity.status(HttpStatus.CREATED).body(id));
    }

    @Operation(summary = "Get Territory", description = "Retrieve a single authorized territory by its identifier.")
    @GetMapping("/{territoryId}")
    public Mono<ResponseEntity<DistributorAuthorizedTerritoryDTO>> getTerritory(
            @PathVariable UUID distributorId,
            @PathVariable UUID territoryId) {
        return distributorNetworkService.getTerritory(distributorId, territoryId)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Update Territory", description = "Update an existing authorized territory.")
    @PutMapping("/{territoryId}")
    public Mono<ResponseEntity<UUID>> updateTerritory(
            @PathVariable UUID distributorId,
            @PathVariable UUID territoryId,
            @Valid @RequestBody UpdateTerritoryCommand command) {
        return distributorNetworkService.updateTerritory(command.withDistributorId(distributorId).withId(territoryId))
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Delete Territory", description = "Delete an authorized territory from a distributor.")
    @DeleteMapping("/{territoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<ResponseEntity<Void>> deleteTerritory(
            @PathVariable UUID distributorId,
            @PathVariable UUID territoryId) {
        return distributorNetworkService.deleteTerritory(distributorId, territoryId)
                .thenReturn(ResponseEntity.noContent().build());
    }
}
