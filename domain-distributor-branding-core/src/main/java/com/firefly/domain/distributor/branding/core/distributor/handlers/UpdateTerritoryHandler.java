package com.firefly.domain.distributor.branding.core.distributor.handlers;

import com.firefly.core.distributor.sdk.api.DistributorAuthorizedTerritoriesApi;
import com.firefly.domain.distributor.branding.core.distributor.commands.UpdateTerritoryCommand;
import org.fireflyframework.cqrs.annotations.CommandHandlerComponent;
import org.fireflyframework.cqrs.command.CommandHandler;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.UUID;

/**
 * Handler that updates an existing authorized territory for a distributor.
 */
@CommandHandlerComponent
public class UpdateTerritoryHandler extends CommandHandler<UpdateTerritoryCommand, UUID> {

    private final DistributorAuthorizedTerritoriesApi territoriesApi;

    public UpdateTerritoryHandler(DistributorAuthorizedTerritoriesApi territoriesApi) {
        this.territoriesApi = territoriesApi;
    }

    @Override
    protected Mono<UUID> doHandle(UpdateTerritoryCommand cmd) {
        return territoriesApi.update3(cmd.getDistributorId(), cmd.getId(), cmd, UUID.randomUUID().toString())
                .mapNotNull(dto -> Objects.requireNonNull(Objects.requireNonNull(dto).getId()));
    }
}
