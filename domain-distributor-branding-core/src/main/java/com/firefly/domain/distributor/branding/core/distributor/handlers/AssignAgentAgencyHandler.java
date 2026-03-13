package com.firefly.domain.distributor.branding.core.distributor.handlers;

import com.firefly.core.distributor.sdk.api.DistributorAgentAgencyApi;
import com.firefly.domain.distributor.branding.core.distributor.commands.AssignAgentAgencyCommand;
import org.fireflyframework.cqrs.annotations.CommandHandlerComponent;
import org.fireflyframework.cqrs.command.CommandHandler;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.UUID;

/**
 * Handler that assigns an agent to an agency within a distributor.
 */
@CommandHandlerComponent
public class AssignAgentAgencyHandler extends CommandHandler<AssignAgentAgencyCommand, UUID> {

    private final DistributorAgentAgencyApi agentAgencyApi;

    public AssignAgentAgencyHandler(DistributorAgentAgencyApi agentAgencyApi) {
        this.agentAgencyApi = agentAgencyApi;
    }

    @Override
    protected Mono<UUID> doHandle(AssignAgentAgencyCommand cmd) {
        return agentAgencyApi.create5(cmd.getDistributorId(), cmd)
                .mapNotNull(dto -> Objects.requireNonNull(Objects.requireNonNull(dto)).getId());
    }
}
