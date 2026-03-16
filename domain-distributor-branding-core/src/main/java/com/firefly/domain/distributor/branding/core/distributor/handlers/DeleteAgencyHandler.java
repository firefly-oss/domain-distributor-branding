package com.firefly.domain.distributor.branding.core.distributor.handlers;

import com.firefly.core.distributor.sdk.api.DistributorAgenciesApi;
import com.firefly.domain.distributor.branding.core.distributor.commands.DeleteAgencyCommand;
import org.fireflyframework.cqrs.annotations.CommandHandlerComponent;
import org.fireflyframework.cqrs.command.CommandHandler;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Handler that deletes an agency from a distributor.
 */
@CommandHandlerComponent
public class DeleteAgencyHandler extends CommandHandler<DeleteAgencyCommand, Void> {

    private final DistributorAgenciesApi agenciesApi;

    public DeleteAgencyHandler(DistributorAgenciesApi agenciesApi) {
        this.agenciesApi = agenciesApi;
    }

    @Override
    protected Mono<Void> doHandle(DeleteAgencyCommand cmd) {
        return agenciesApi.delete6(cmd.distributorId(), cmd.agencyId(), UUID.randomUUID().toString());
    }
}
