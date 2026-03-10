package com.firefly.domain.distributor.branding.web.controller;

import com.firefly.core.distributor.sdk.model.DistributorOperationDTO;
import com.firefly.domain.distributor.branding.core.distributor.commands.ActivateOperationCommand;
import com.firefly.domain.distributor.branding.core.distributor.commands.CreateOperationCommand;
import com.firefly.domain.distributor.branding.core.distributor.commands.DeactivateOperationCommand;
import com.firefly.domain.distributor.branding.core.distributor.commands.UpdateOperationCommand;
import com.firefly.domain.distributor.branding.core.distributor.services.DistributorOperationsService;
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
@RequestMapping("/api/v1/distributors/{distributorId}/operations")
@RequiredArgsConstructor
@Tag(name = "Operation", description = "Lifecycle operations for distributor operations")
public class OperationController {

    private final DistributorOperationsService distributorOperationsService;

    @Operation(summary = "List Operations", description = "List all operations for a distributor.")
    @GetMapping
    public Mono<ResponseEntity<DistributorOperationDTO>> listOperations(@PathVariable UUID distributorId) {
        return distributorOperationsService.listOperations(distributorId)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Create Operation", description = "Create a new operation for a distributor.")
    @PostMapping
    public Mono<ResponseEntity<UUID>> createOperation(
            @PathVariable UUID distributorId,
            @Valid @RequestBody CreateOperationCommand command) {
        return distributorOperationsService.createOperation(command.withDistributorId(distributorId))
                .map(id -> ResponseEntity.status(HttpStatus.CREATED).body(id));
    }

    @Operation(summary = "Get Operation", description = "Retrieve a single operation by its identifier.")
    @GetMapping("/{operationId}")
    public Mono<ResponseEntity<DistributorOperationDTO>> getOperation(
            @PathVariable UUID distributorId,
            @PathVariable UUID operationId) {
        return distributorOperationsService.getOperation(distributorId, operationId)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Update Operation", description = "Update an existing operation.")
    @PutMapping("/{operationId}")
    public Mono<ResponseEntity<UUID>> updateOperation(
            @PathVariable UUID distributorId,
            @PathVariable UUID operationId,
            @Valid @RequestBody UpdateOperationCommand command) {
        return distributorOperationsService.updateOperation(command.withDistributorId(distributorId).withId(operationId))
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Delete Operation", description = "Delete an operation from a distributor.")
    @DeleteMapping("/{operationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<ResponseEntity<Void>> deleteOperation(
            @PathVariable UUID distributorId,
            @PathVariable UUID operationId) {
        return distributorOperationsService.deleteOperation(distributorId, operationId)
                .thenReturn(ResponseEntity.noContent().build());
    }

    @Operation(summary = "Activate Operation", description = "Activate an operation for a distributor.")
    @PatchMapping("/{operationId}/activate")
    public Mono<ResponseEntity<DistributorOperationDTO>> activateOperation(
            @PathVariable UUID distributorId,
            @PathVariable UUID operationId,
            @RequestParam UUID activatedBy) {
        return distributorOperationsService.activateOperation(
                        new ActivateOperationCommand(distributorId, operationId, activatedBy))
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Deactivate Operation", description = "Deactivate an operation for a distributor.")
    @PatchMapping("/{operationId}/deactivate")
    public Mono<ResponseEntity<DistributorOperationDTO>> deactivateOperation(
            @PathVariable UUID distributorId,
            @PathVariable UUID operationId,
            @RequestParam UUID deactivatedBy) {
        return distributorOperationsService.deactivateOperation(
                        new DeactivateOperationCommand(distributorId, operationId, deactivatedBy))
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Can Operate", description = "Check whether a distributor can operate in a given location.")
    @GetMapping("/can-operate")
    public Mono<ResponseEntity<Boolean>> canOperate(
            @PathVariable UUID distributorId,
            @RequestParam UUID operationId,
            @RequestParam UUID locationId) {
        return distributorOperationsService.canOperate(distributorId, operationId, locationId)
                .map(ResponseEntity::ok);
    }
}
