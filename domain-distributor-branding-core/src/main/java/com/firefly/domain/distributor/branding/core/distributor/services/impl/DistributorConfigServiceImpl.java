package com.firefly.domain.distributor.branding.core.distributor.services.impl;

import com.firefly.core.distributor.sdk.model.DistributorConfigurationDTO;
import com.firefly.core.distributor.sdk.model.PaginationResponse;
import com.firefly.domain.distributor.branding.core.distributor.commands.*;
import com.firefly.domain.distributor.branding.core.distributor.queries.*;
import com.firefly.domain.distributor.branding.core.distributor.services.DistributorConfigService;
import org.fireflyframework.cqrs.command.CommandBus;
import org.fireflyframework.cqrs.query.QueryBus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Implementation of {@link DistributorConfigService} that dispatches operations
 * through the CQRS command and query buses.
 */
@Service
public class DistributorConfigServiceImpl implements DistributorConfigService {

    private final CommandBus commandBus;
    private final QueryBus queryBus;

    public DistributorConfigServiceImpl(CommandBus commandBus, QueryBus queryBus) {
        this.commandBus = commandBus;
        this.queryBus = queryBus;
    }

    @Override
    public Mono<UUID> createConfiguration(CreateConfigurationCommand command) {
        return commandBus.send(command);
    }

    @Override
    public Mono<DistributorConfigurationDTO> getConfiguration(UUID distributorId, UUID configurationId) {
        return queryBus.query(new GetConfigurationQuery(distributorId, configurationId));
    }

    @Override
    public Mono<PaginationResponse> listConfigurations(UUID distributorId) {
        return queryBus.query(new ListConfigurationsQuery(distributorId));
    }

    @Override
    public Mono<UUID> updateConfiguration(UpdateConfigurationCommand command) {
        return commandBus.send(command);
    }

    @Override
    public Mono<Void> deleteConfiguration(UUID distributorId, UUID configurationId) {
        return commandBus.send(new DeleteConfigurationCommand(distributorId, configurationId));
    }
}
