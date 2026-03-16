package com.firefly.domain.distributor.branding.core.distributor.handlers;

import com.firefly.core.distributor.sdk.api.DistributorAgenciesApi;
import com.firefly.domain.distributor.branding.core.distributor.commands.CreateAgencyCommand;
import org.fireflyframework.cqrs.annotations.CommandHandlerComponent;
import org.fireflyframework.cqrs.command.CommandHandler;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.UUID;

/**
 * Handler that creates a new agency for a distributor.
 */
@CommandHandlerComponent
public class CreateAgencyHandler extends CommandHandler<CreateAgencyCommand, UUID> {

    private final DistributorAgenciesApi agenciesApi;

    public CreateAgencyHandler(DistributorAgenciesApi agenciesApi) {
        this.agenciesApi = agenciesApi;
    }

    @Override
    protected Mono<UUID> doHandle(CreateAgencyCommand cmd) {
        return agenciesApi.create6(cmd.getDistributorId(), cmd, UUID.randomUUID().toString())
                .mapNotNull(dto -> Objects.requireNonNull(Objects.requireNonNull(dto)).getId());
    }
}
