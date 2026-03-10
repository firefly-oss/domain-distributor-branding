package com.firefly.domain.distributor.branding.core.distributor.handlers;

import com.firefly.core.distributor.sdk.api.DistributorAgenciesApi;
import com.firefly.domain.distributor.branding.core.distributor.commands.UpdateAgencyCommand;
import org.fireflyframework.cqrs.annotations.CommandHandlerComponent;
import org.fireflyframework.cqrs.command.CommandHandler;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.UUID;

/**
 * Handler that updates an existing agency for a distributor.
 */
@CommandHandlerComponent
public class UpdateAgencyHandler extends CommandHandler<UpdateAgencyCommand, UUID> {

    private final DistributorAgenciesApi agenciesApi;

    public UpdateAgencyHandler(DistributorAgenciesApi agenciesApi) {
        this.agenciesApi = agenciesApi;
    }

    @Override
    protected Mono<UUID> doHandle(UpdateAgencyCommand cmd) {
        return agenciesApi.update6(cmd.getDistributorId(), cmd.getId(), cmd, UUID.randomUUID().toString())
                .mapNotNull(dto -> Objects.requireNonNull(Objects.requireNonNull(dto).getId()));
    }
}
