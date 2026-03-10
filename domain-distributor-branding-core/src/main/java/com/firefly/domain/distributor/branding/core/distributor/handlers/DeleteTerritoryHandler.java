package com.firefly.domain.distributor.branding.core.distributor.handlers;

import com.firefly.core.distributor.sdk.api.DistributorAuthorizedTerritoriesApi;
import com.firefly.domain.distributor.branding.core.distributor.commands.DeleteTerritoryCommand;
import org.fireflyframework.cqrs.annotations.CommandHandlerComponent;
import org.fireflyframework.cqrs.command.CommandHandler;
import reactor.core.publisher.Mono;

/**
 * Handler that deletes an authorized territory from a distributor.
 */
@CommandHandlerComponent
public class DeleteTerritoryHandler extends CommandHandler<DeleteTerritoryCommand, Void> {

    private final DistributorAuthorizedTerritoriesApi territoriesApi;

    public DeleteTerritoryHandler(DistributorAuthorizedTerritoriesApi territoriesApi) {
        this.territoriesApi = territoriesApi;
    }

    @Override
    protected Mono<Void> doHandle(DeleteTerritoryCommand cmd) {
        return territoriesApi.delete3(cmd.distributorId(), cmd.territoryId(), null);
    }
}
