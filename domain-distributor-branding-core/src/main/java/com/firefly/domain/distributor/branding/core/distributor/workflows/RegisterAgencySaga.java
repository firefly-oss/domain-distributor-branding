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
 * Saga that orchestrates the creation of a complete agency setup:
 * authorized territory, agency, and operational coverage.
 *
 * <p>Step DAG:
 * <pre>
 *   createTerritory (Layer 0)
 *         |
 *   createAgency   (Layer 1, depends on territory for geographic consistency)
 *         |
 *   createOperation (Layer 2, links agency to operational coverage)
 * </pre>
 *
 * <p>Compensation runs in reverse order: deleteOperation → deleteAgency → deleteTerritory.
 */
@Saga(name = SAGA_REGISTER_AGENCY)
@Service
public class RegisterAgencySaga {

    private final CommandBus commandBus;

    @Autowired
    public RegisterAgencySaga(CommandBus commandBus) {
        this.commandBus = commandBus;
    }

    @SagaStep(id = STEP_CREATE_TERRITORY, compensate = COMPENSATE_DELETE_TERRITORY)
    @StepEvent(type = EVENT_TERRITORY_CREATED)
    public Mono<UUID> createTerritory(CreateTerritoryCommand cmd, ExecutionContext ctx) {
        ctx.putVariable(CTX_DISTRIBUTOR_ID, cmd.getDistributorId());
        return commandBus.<UUID>send(cmd)
                .doOnNext(territoryId -> ctx.putVariable(CTX_TERRITORY_ID, territoryId));
    }

    public Mono<Void> deleteTerritory(UUID territoryId, ExecutionContext ctx) {
        UUID distributorId = ctx.getVariableAs(CTX_DISTRIBUTOR_ID, UUID.class);
        return commandBus.send(new DeleteTerritoryCommand(distributorId, territoryId));
    }

    @SagaStep(id = STEP_CREATE_AGENCY, compensate = COMPENSATE_DELETE_AGENCY, dependsOn = STEP_CREATE_TERRITORY)
    @StepEvent(type = EVENT_AGENCY_CREATED)
    public Mono<UUID> createAgency(CreateAgencyCommand cmd, ExecutionContext ctx) {
        return commandBus.<UUID>send(cmd)
                .doOnNext(agencyId -> ctx.putVariable(CTX_AGENCY_ID, agencyId));
    }

    public Mono<Void> deleteAgency(UUID agencyId, ExecutionContext ctx) {
        UUID distributorId = ctx.getVariableAs(CTX_DISTRIBUTOR_ID, UUID.class);
        return commandBus.send(new DeleteAgencyCommand(distributorId, agencyId));
    }

    @SagaStep(id = STEP_CREATE_OPERATION, compensate = COMPENSATE_DELETE_OPERATION, dependsOn = STEP_CREATE_AGENCY)
    @StepEvent(type = EVENT_OPERATION_CREATED)
    public Mono<UUID> createOperation(CreateOperationCommand cmd, ExecutionContext ctx) {
        UUID agencyId = ctx.getVariableAs(CTX_AGENCY_ID, UUID.class);
        cmd.setAgencyId(agencyId);
        return commandBus.<UUID>send(cmd)
                .doOnNext(operationId -> ctx.putVariable(CTX_OPERATION_ID, operationId));
    }

    public Mono<Void> deleteOperation(UUID operationId, ExecutionContext ctx) {
        UUID distributorId = ctx.getVariableAs(CTX_DISTRIBUTOR_ID, UUID.class);
        return commandBus.send(new DeleteOperationCommand(distributorId, operationId));
    }
}
