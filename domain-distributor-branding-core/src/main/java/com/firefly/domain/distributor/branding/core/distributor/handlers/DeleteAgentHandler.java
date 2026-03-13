package com.firefly.domain.distributor.branding.core.distributor.handlers;

import com.firefly.core.distributor.sdk.api.DistributorAgentsApi;
import com.firefly.domain.distributor.branding.core.distributor.commands.DeleteAgentCommand;
import org.fireflyframework.cqrs.annotations.CommandHandlerComponent;
import org.fireflyframework.cqrs.command.CommandHandler;
import reactor.core.publisher.Mono;

/**
 * Handler that deletes an agent from a distributor.
 */
@CommandHandlerComponent
public class DeleteAgentHandler extends CommandHandler<DeleteAgentCommand, Void> {

    private final DistributorAgentsApi agentsApi;

    public DeleteAgentHandler(DistributorAgentsApi agentsApi) {
        this.agentsApi = agentsApi;
    }

    @Override
    protected Mono<Void> doHandle(DeleteAgentCommand cmd) {
        return agentsApi.delete4(cmd.distributorId(), cmd.agentId());
    }
}
