package com.firefly.domain.distributor.branding.core.distributor.handlers;

import com.firefly.core.distributor.sdk.api.DistributorAgentAgencyApi;
import com.firefly.domain.distributor.branding.core.distributor.commands.RemoveAgentAgencyCommand;
import org.fireflyframework.cqrs.annotations.CommandHandlerComponent;
import org.fireflyframework.cqrs.command.CommandHandler;
import reactor.core.publisher.Mono;

/**
 * Handler that removes an agent-agency assignment from a distributor.
 */
@CommandHandlerComponent
public class RemoveAgentAgencyHandler extends CommandHandler<RemoveAgentAgencyCommand, Void> {

    private final DistributorAgentAgencyApi agentAgencyApi;

    public RemoveAgentAgencyHandler(DistributorAgentAgencyApi agentAgencyApi) {
        this.agentAgencyApi = agentAgencyApi;
    }

    @Override
    protected Mono<Void> doHandle(RemoveAgentAgencyCommand cmd) {
        return agentAgencyApi.delete5(cmd.distributorId(), cmd.agentAgencyId());
    }
}
