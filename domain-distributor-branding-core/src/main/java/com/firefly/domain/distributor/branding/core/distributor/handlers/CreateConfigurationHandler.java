package com.firefly.domain.distributor.branding.core.distributor.handlers;

import com.firefly.core.distributor.sdk.api.DistributorConfigurationsApi;
import com.firefly.domain.distributor.branding.core.distributor.commands.CreateConfigurationCommand;
import org.fireflyframework.cqrs.annotations.CommandHandlerComponent;
import org.fireflyframework.cqrs.command.CommandHandler;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.UUID;

/**
 * Handler that creates a new configuration for a distributor.
 */
@CommandHandlerComponent
public class CreateConfigurationHandler extends CommandHandler<CreateConfigurationCommand, UUID> {

    private final DistributorConfigurationsApi configurationsApi;

    public CreateConfigurationHandler(DistributorConfigurationsApi configurationsApi) {
        this.configurationsApi = configurationsApi;
    }

    @Override
    protected Mono<UUID> doHandle(CreateConfigurationCommand cmd) {
        return configurationsApi.create2(cmd.getDistributorId(), cmd)
                .mapNotNull(dto -> Objects.requireNonNull(Objects.requireNonNull(dto)).getId());
    }
}
