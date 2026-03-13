package com.firefly.domain.distributor.branding.core.distributor.handlers;

import com.firefly.core.distributor.sdk.api.DistributorConfigurationsApi;
import com.firefly.domain.distributor.branding.core.distributor.commands.DeleteConfigurationCommand;
import org.fireflyframework.cqrs.annotations.CommandHandlerComponent;
import org.fireflyframework.cqrs.command.CommandHandler;
import reactor.core.publisher.Mono;

/**
 * Handler that deletes a configuration from a distributor.
 */
@CommandHandlerComponent
public class DeleteConfigurationHandler extends CommandHandler<DeleteConfigurationCommand, Void> {

    private final DistributorConfigurationsApi configurationsApi;

    public DeleteConfigurationHandler(DistributorConfigurationsApi configurationsApi) {
        this.configurationsApi = configurationsApi;
    }

    @Override
    protected Mono<Void> doHandle(DeleteConfigurationCommand cmd) {
        return configurationsApi.delete2(cmd.distributorId(), cmd.configurationId());
    }
}
