package com.firefly.domain.distributor.branding.core.distributor.workflows;

import com.firefly.domain.distributor.branding.core.distributor.commands.*;
import org.fireflyframework.cqrs.command.CommandBus;
import org.fireflyframework.orchestration.core.context.ExecutionContext;
import org.fireflyframework.orchestration.saga.annotation.Saga;
import org.fireflyframework.orchestration.saga.annotation.SagaStep;
import org.fireflyframework.orchestration.saga.annotation.StepEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static com.firefly.domain.distributor.branding.core.utils.constants.DistributorConstants.*;
import static com.firefly.domain.distributor.branding.core.utils.constants.GlobalConstants.*;

/**
 * Saga that orchestrates agent creation and agency assignment atomically.
 *
 * <p>The {@code distributor_agent_agency} table requires both {@code agent_id NOT NULL}
 * and {@code agency_id NOT NULL}. Creating an agent without assigning it to an agency
 * leaves the agent functionally orphaned. This saga ensures both operations
 * succeed or both are rolled back.
 *
 * <p>Step DAG:
 * <pre>
 *   createAgent          (Layer 0)
 *        |
 *   assignAgentAgency    (Layer 1, sets agent_id from context)
 * </pre>
 *
 * <p>Compensation: removeAgentAgency → deleteAgent.
 */
@Saga(name = SAGA_REGISTER_AGENT)
@Service
public class RegisterAgentSaga {

    private final CommandBus commandBus;

    @Autowired
    public RegisterAgentSaga(CommandBus commandBus) {
        this.commandBus = commandBus;
    }

    @SagaStep(id = STEP_CREATE_AGENT, compensate = COMPENSATE_DELETE_AGENT)
    @StepEvent(type = EVENT_AGENT_CREATED)
    public Mono<UUID> createAgent(CreateAgentCommand cmd, ExecutionContext ctx) {
        ctx.putVariable(CTX_DISTRIBUTOR_ID, cmd.getDistributorId());
        return commandBus.<UUID>send(cmd)
                .doOnNext(agentId -> ctx.putVariable(CTX_AGENT_ID, agentId));
    }

    public Mono<Void> deleteAgent(UUID agentId, ExecutionContext ctx) {
        UUID distributorId = ctx.getVariableAs(CTX_DISTRIBUTOR_ID, UUID.class);
        return commandBus.send(new DeleteAgentCommand(distributorId, agentId));
    }

    @SagaStep(id = STEP_ASSIGN_AGENT_AGENCY, compensate = COMPENSATE_REMOVE_AGENT_AGENCY, dependsOn = STEP_CREATE_AGENT)
    @StepEvent(type = EVENT_AGENT_AGENCY_ASSIGNED)
    public Mono<UUID> assignAgentAgency(AssignAgentAgencyCommand cmd, ExecutionContext ctx) {
        UUID agentId = ctx.getVariableAs(CTX_AGENT_ID, UUID.class);
        cmd.setAgentId(agentId);
        return commandBus.<UUID>send(cmd)
                .doOnNext(agentAgencyId -> ctx.putVariable(CTX_AGENT_AGENCY_ID, agentAgencyId));
    }

    public Mono<Void> removeAgentAgency(UUID agentAgencyId, ExecutionContext ctx) {
        UUID distributorId = ctx.getVariableAs(CTX_DISTRIBUTOR_ID, UUID.class);
        return commandBus.send(new RemoveAgentAgencyCommand(distributorId, agentAgencyId));
    }
}
