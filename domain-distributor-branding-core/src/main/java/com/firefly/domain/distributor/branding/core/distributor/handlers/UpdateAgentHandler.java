package com.firefly.domain.distributor.branding.core.distributor.handlers;

import com.firefly.core.distributor.sdk.api.DistributorAgentsApi;
import com.firefly.domain.distributor.branding.core.distributor.commands.UpdateAgentCommand;
import org.fireflyframework.cqrs.annotations.CommandHandlerComponent;
import org.fireflyframework.cqrs.command.CommandHandler;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.UUID;

/**
 * Handler that updates an existing agent for a distributor.
 */
@CommandHandlerComponent
public class UpdateAgentHandler extends CommandHandler<UpdateAgentCommand, UUID> {

    private final DistributorAgentsApi agentsApi;

    public UpdateAgentHandler(DistributorAgentsApi agentsApi) {
        this.agentsApi = agentsApi;
    }

    @Override
    protected Mono<UUID> doHandle(UpdateAgentCommand cmd) {
        return agentsApi.update4(cmd.getDistributorId(), cmd.getId(), cmd, UUID.randomUUID().toString())
                .mapNotNull(dto -> Objects.requireNonNull(Objects.requireNonNull(dto).getId()));
    }
}
