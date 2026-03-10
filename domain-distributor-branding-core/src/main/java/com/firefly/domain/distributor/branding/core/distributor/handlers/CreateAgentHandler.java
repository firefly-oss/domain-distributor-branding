package com.firefly.domain.distributor.branding.core.distributor.handlers;

import com.firefly.core.distributor.sdk.api.DistributorAgentsApi;
import com.firefly.domain.distributor.branding.core.distributor.commands.CreateAgentCommand;
import org.fireflyframework.cqrs.annotations.CommandHandlerComponent;
import org.fireflyframework.cqrs.command.CommandHandler;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.UUID;

/**
 * Handler that creates a new agent for a distributor.
 */
@CommandHandlerComponent
public class CreateAgentHandler extends CommandHandler<CreateAgentCommand, UUID> {

    private final DistributorAgentsApi agentsApi;

    public CreateAgentHandler(DistributorAgentsApi agentsApi) {
        this.agentsApi = agentsApi;
    }

    @Override
    protected Mono<UUID> doHandle(CreateAgentCommand cmd) {
        return agentsApi.create4(cmd.getDistributorId(), cmd, UUID.randomUUID().toString())
                .mapNotNull(dto -> Objects.requireNonNull(Objects.requireNonNull(dto)).getId());
    }
}
