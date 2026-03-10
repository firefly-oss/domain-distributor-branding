package com.firefly.domain.distributor.branding.web.controller;

import com.firefly.core.distributor.sdk.model.DistributorAgentDTO;
import com.firefly.core.distributor.sdk.model.PaginationResponse;
import com.firefly.domain.distributor.branding.core.distributor.commands.CreateAgentCommand;
import com.firefly.domain.distributor.branding.core.distributor.commands.UpdateAgentCommand;
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
@RequestMapping("/api/v1/distributors/{distributorId}/agents")
@RequiredArgsConstructor
@Tag(name = "Agent", description = "CRUD operations for distributor agents")
public class AgentController {

    private final DistributorNetworkService distributorNetworkService;

    @Operation(summary = "List Agents", description = "List all agents for a distributor.")
    @GetMapping
    public Mono<ResponseEntity<PaginationResponse>> listAgents(@PathVariable UUID distributorId) {
        return distributorNetworkService.listAgents(distributorId)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Create Agent", description = "Create a new agent for a distributor.")
    @PostMapping
    public Mono<ResponseEntity<UUID>> createAgent(
            @PathVariable UUID distributorId,
            @Valid @RequestBody CreateAgentCommand command) {
        return distributorNetworkService.createAgent(command.withDistributorId(distributorId))
                .map(id -> ResponseEntity.status(HttpStatus.CREATED).body(id));
    }

    @Operation(summary = "Get Agent", description = "Retrieve a single agent by its identifier.")
    @GetMapping("/{agentId}")
    public Mono<ResponseEntity<DistributorAgentDTO>> getAgent(
            @PathVariable UUID distributorId,
            @PathVariable UUID agentId) {
        return distributorNetworkService.getAgent(distributorId, agentId)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Update Agent", description = "Update an existing agent.")
    @PutMapping("/{agentId}")
    public Mono<ResponseEntity<UUID>> updateAgent(
            @PathVariable UUID distributorId,
            @PathVariable UUID agentId,
            @Valid @RequestBody UpdateAgentCommand command) {
        return distributorNetworkService.updateAgent(command.withDistributorId(distributorId).withId(agentId))
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Delete Agent", description = "Delete an agent from a distributor.")
    @DeleteMapping("/{agentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<ResponseEntity<Void>> deleteAgent(
            @PathVariable UUID distributorId,
            @PathVariable UUID agentId) {
        return distributorNetworkService.deleteAgent(distributorId, agentId)
                .thenReturn(ResponseEntity.noContent().build());
    }
}
