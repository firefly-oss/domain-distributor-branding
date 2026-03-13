package com.firefly.domain.distributor.branding.core.distributor.handlers;

import com.firefly.core.distributor.sdk.api.DistributorAuthorizedTerritoriesApi;
import com.firefly.domain.distributor.branding.core.distributor.commands.CreateTerritoryCommand;
import org.fireflyframework.cqrs.annotations.CommandHandlerComponent;
import org.fireflyframework.cqrs.command.CommandHandler;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.UUID;

/**
 * Handler that creates a new authorized territory for a distributor.
 */
@CommandHandlerComponent
public class CreateTerritoryHandler extends CommandHandler<CreateTerritoryCommand, UUID> {

    private final DistributorAuthorizedTerritoriesApi territoriesApi;

    public CreateTerritoryHandler(DistributorAuthorizedTerritoriesApi territoriesApi) {
        this.territoriesApi = territoriesApi;
    }

    @Override
    protected Mono<UUID> doHandle(CreateTerritoryCommand cmd) {
        return territoriesApi.create3(cmd.getDistributorId(), cmd)
                .mapNotNull(dto -> Objects.requireNonNull(Objects.requireNonNull(dto)).getId());
    }
}
