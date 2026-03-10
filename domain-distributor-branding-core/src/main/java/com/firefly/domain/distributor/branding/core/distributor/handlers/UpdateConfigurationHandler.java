package com.firefly.domain.distributor.branding.core.distributor.handlers;

import com.firefly.core.distributor.sdk.api.DistributorConfigurationsApi;
import com.firefly.domain.distributor.branding.core.distributor.commands.UpdateConfigurationCommand;
import org.fireflyframework.cqrs.annotations.CommandHandlerComponent;
import org.fireflyframework.cqrs.command.CommandHandler;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.UUID;

/**
 * Handler that updates an existing configuration for a distributor.
 */
@CommandHandlerComponent
public class UpdateConfigurationHandler extends CommandHandler<UpdateConfigurationCommand, UUID> {

    private final DistributorConfigurationsApi configurationsApi;

    public UpdateConfigurationHandler(DistributorConfigurationsApi configurationsApi) {
        this.configurationsApi = configurationsApi;
    }

    @Override
    protected Mono<UUID> doHandle(UpdateConfigurationCommand cmd) {
        return configurationsApi.update2(cmd.getDistributorId(), cmd.getId(), cmd, UUID.randomUUID().toString())
                .mapNotNull(dto -> Objects.requireNonNull(Objects.requireNonNull(dto).getId()));
    }
}
