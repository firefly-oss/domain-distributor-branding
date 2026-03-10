package com.firefly.domain.distributor.branding.web.controller;

import com.firefly.domain.distributor.branding.core.distributor.commands.AssignAgentAgencyCommand;
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
@RequestMapping("/api/v1/distributors/{distributorId}/agent-agencies")
@RequiredArgsConstructor
@Tag(name = "Agent-Agency", description = "Operations for distributor agent-agency assignments")
public class AgentAgencyController {

    private final DistributorNetworkService distributorNetworkService;

    @Operation(summary = "List Agent-Agency Assignments", description = "List all agent-agency assignments for a distributor.")
    @GetMapping
    public Mono<ResponseEntity<Void>> listAgentAgencies(@PathVariable UUID distributorId) {
        return distributorNetworkService.listAgentAgencies(distributorId)
                .thenReturn(ResponseEntity.ok().build());
    }

    @Operation(summary = "Assign Agent to Agency", description = "Create an agent-agency assignment for a distributor.")
    @PostMapping
    public Mono<ResponseEntity<UUID>> assignAgentAgency(
            @PathVariable UUID distributorId,
            @Valid @RequestBody AssignAgentAgencyCommand command) {
        return distributorNetworkService.assignAgentAgency(command.withDistributorId(distributorId))
                .map(id -> ResponseEntity.status(HttpStatus.CREATED).body(id));
    }

    @Operation(summary = "Remove Agent-Agency Assignment", description = "Remove an agent-agency assignment from a distributor.")
    @DeleteMapping("/{agentAgencyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<ResponseEntity<Void>> removeAgentAgency(
            @PathVariable UUID distributorId,
            @PathVariable UUID agentAgencyId) {
        return distributorNetworkService.removeAgentAgency(distributorId, agentAgencyId)
                .thenReturn(ResponseEntity.noContent().build());
    }
}
