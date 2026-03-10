package com.firefly.domain.distributor.branding.core.distributor.services;

import com.firefly.core.distributor.sdk.model.DistributorOperationDTO;
import com.firefly.domain.distributor.branding.core.distributor.commands.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Service that orchestrates distributor operation lifecycle through
 * the CQRS command and query buses.
 */
public interface DistributorOperationsService {

    /**
     * Creates a new operation for a distributor.
     *
     * @param command the command containing operation data
     * @return a Mono emitting the created operation identifier
     */
    Mono<UUID> createOperation(CreateOperationCommand command);

    /**
     * Retrieves a single operation by its identifier.
     *
     * @param distributorId the distributor identifier
     * @param operationId the operation identifier
     * @return a Mono emitting the operation DTO
     */
    Mono<DistributorOperationDTO> getOperation(UUID distributorId, UUID operationId);

    /**
     * Lists all operations for a distributor.
     *
     * @param distributorId the distributor identifier
     * @return a Mono emitting the operations
     */
    Mono<DistributorOperationDTO> listOperations(UUID distributorId);

    /**
     * Updates an existing operation.
     *
     * @param command the command containing updated operation data
     * @return a Mono emitting the updated operation identifier
     */
    Mono<UUID> updateOperation(UpdateOperationCommand command);

    /**
     * Deletes an operation from a distributor.
     *
     * @param distributorId the distributor identifier
     * @param operationId the operation identifier
     * @return a Mono completing when deleted
     */
    Mono<Void> deleteOperation(UUID distributorId, UUID operationId);

    /**
     * Activates an operation for a distributor.
     *
     * @param command the activate command
     * @return a Mono emitting the activated operation
     */
    Mono<DistributorOperationDTO> activateOperation(ActivateOperationCommand command);

    /**
     * Deactivates an operation for a distributor.
     *
     * @param command the deactivate command
     * @return a Mono emitting the deactivated operation
     */
    Mono<DistributorOperationDTO> deactivateOperation(DeactivateOperationCommand command);

    /**
     * Checks whether a distributor can operate in a given location.
     *
     * @param distributorId the distributor identifier
     * @param operationId the operation identifier
     * @param locationId the location identifier
     * @return a Mono emitting true if the distributor can operate
     */
    Mono<Boolean> canOperate(UUID distributorId, UUID operationId, UUID locationId);
}
