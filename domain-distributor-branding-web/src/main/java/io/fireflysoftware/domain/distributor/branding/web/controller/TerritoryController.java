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

import io.fireflysoftware.core.distributor.sdk.model.DistributorAuthorizedTerritoryDTO;
import io.fireflysoftware.core.distributor.sdk.model.PaginationResponse;
import io.fireflysoftware.domain.distributor.branding.core.distributor.commands.CreateTerritoryCommand;
import io.fireflysoftware.domain.distributor.branding.core.distributor.commands.UpdateTerritoryCommand;
import io.fireflysoftware.domain.distributor.branding.core.distributor.services.DistributorNetworkService;
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
