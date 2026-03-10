package com.firefly.domain.distributor.branding.core.distributor.services;

import com.firefly.core.distributor.sdk.model.DistributorConfigurationDTO;
import com.firefly.core.distributor.sdk.model.PaginationResponse;
import com.firefly.domain.distributor.branding.core.distributor.commands.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Service that orchestrates distributor configuration operations
 * through the CQRS command and query buses.
 */
public interface DistributorConfigService {

    /**
     * Creates a new configuration for a distributor.
     *
     * @param command the command containing configuration data
     * @return a Mono emitting the created configuration identifier
     */
    Mono<UUID> createConfiguration(CreateConfigurationCommand command);

    /**
     * Retrieves a single configuration by its identifier.
     *
     * @param distributorId the distributor identifier
     * @param configurationId the configuration identifier
     * @return a Mono emitting the configuration DTO
     */
    Mono<DistributorConfigurationDTO> getConfiguration(UUID distributorId, UUID configurationId);

    /**
     * Lists all configurations for a distributor.
     *
     * @param distributorId the distributor identifier
     * @return a Mono emitting the paginated response
     */
    Mono<PaginationResponse> listConfigurations(UUID distributorId);

    /**
     * Updates an existing configuration.
     *
     * @param command the command containing updated configuration data
     * @return a Mono emitting the updated configuration identifier
     */
    Mono<UUID> updateConfiguration(UpdateConfigurationCommand command);

    /**
     * Deletes a configuration from a distributor.
     *
     * @param distributorId the distributor identifier
     * @param configurationId the configuration identifier
     * @return a Mono completing when deleted
     */
    Mono<Void> deleteConfiguration(UUID distributorId, UUID configurationId);
}
