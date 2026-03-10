package com.firefly.domain.distributor.branding.core.distributor.services.impl;

import com.firefly.core.distributor.sdk.model.DistributorAgencyDTO;
import com.firefly.core.distributor.sdk.model.DistributorAgentDTO;
import com.firefly.core.distributor.sdk.model.DistributorAuthorizedTerritoryDTO;
import com.firefly.core.distributor.sdk.model.PaginationResponse;
import com.firefly.domain.distributor.branding.core.distributor.commands.*;
import com.firefly.domain.distributor.branding.core.distributor.queries.*;
import com.firefly.domain.distributor.branding.core.distributor.services.DistributorNetworkService;
import org.fireflyframework.cqrs.command.CommandBus;
import org.fireflyframework.cqrs.query.QueryBus;
import org.fireflyframework.orchestration.saga.engine.SagaEngine;
import org.fireflyframework.orchestration.saga.engine.SagaResult;
import org.fireflyframework.orchestration.saga.engine.StepInputs;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static com.firefly.domain.distributor.branding.core.utils.constants.DistributorConstants.*;

/**
 * Implementation of {@link DistributorNetworkService} that dispatches operations
 * through the CQRS command and query buses, and uses saga orchestration
 * for composite multi-step operations.
 */
@Service
public class DistributorNetworkServiceImpl implements DistributorNetworkService {

    private final CommandBus commandBus;
    private final QueryBus queryBus;
    private final SagaEngine engine;

    public DistributorNetworkServiceImpl(CommandBus commandBus, QueryBus queryBus, SagaEngine engine) {
        this.commandBus = commandBus;
        this.queryBus = queryBus;
        this.engine = engine;
    }

    // --- Saga-orchestrated composite operations ---

    @Override
    public Mono<SagaResult> registerAgency(RegisterAgencyCommand command) {
        StepInputs inputs = StepInputs.builder()
                .forStepId(STEP_CREATE_TERRITORY, command.getTerritory())
                .forStepId(STEP_CREATE_AGENCY, command.getAgency())
                .forStepId(STEP_CREATE_OPERATION, command.getOperation())
                .build();
        return engine.execute(SAGA_REGISTER_AGENCY, inputs);
    }

    @Override
    public Mono<SagaResult> registerAgent(RegisterAgentWithAssignmentCommand command) {
        StepInputs inputs = StepInputs.builder()
                .forStepId(STEP_CREATE_AGENT, command.getAgent())
                .forStepId(STEP_ASSIGN_AGENT_AGENCY, command.getAssignment())
                .build();
        return engine.execute(SAGA_REGISTER_AGENT, inputs);
    }

    // --- Territory operations ---

    @Override
    public Mono<UUID> createTerritory(CreateTerritoryCommand command) {
        return commandBus.send(command);
    }

    @Override
    public Mono<DistributorAuthorizedTerritoryDTO> getTerritory(UUID distributorId, UUID territoryId) {
        return queryBus.query(new GetTerritoryQuery(distributorId, territoryId));
    }

    @Override
    public Mono<PaginationResponse> listTerritories(UUID distributorId) {
        return queryBus.query(new ListTerritoriesQuery(distributorId));
    }

    @Override
    public Mono<UUID> updateTerritory(UpdateTerritoryCommand command) {
        return commandBus.send(command);
    }

    @Override
    public Mono<Void> deleteTerritory(UUID distributorId, UUID territoryId) {
        return commandBus.send(new DeleteTerritoryCommand(distributorId, territoryId));
    }

    // --- Agency operations ---

    @Override
    public Mono<UUID> createAgency(CreateAgencyCommand command) {
        return commandBus.send(command);
    }

    @Override
    public Mono<DistributorAgencyDTO> getAgency(UUID distributorId, UUID agencyId) {
        return queryBus.query(new GetAgencyQuery(distributorId, agencyId));
    }

    @Override
    public Mono<PaginationResponse> listAgencies(UUID distributorId) {
        return queryBus.query(new ListAgenciesQuery(distributorId));
    }

    @Override
    public Mono<UUID> updateAgency(UpdateAgencyCommand command) {
        return commandBus.send(command);
    }

    @Override
    public Mono<Void> deleteAgency(UUID distributorId, UUID agencyId) {
        return commandBus.send(new DeleteAgencyCommand(distributorId, agencyId));
    }

    // --- Agent operations ---

    @Override
    public Mono<UUID> createAgent(CreateAgentCommand command) {
        return commandBus.send(command);
    }

    @Override
    public Mono<DistributorAgentDTO> getAgent(UUID distributorId, UUID agentId) {
        return queryBus.query(new GetAgentQuery(distributorId, agentId));
    }

    @Override
    public Mono<PaginationResponse> listAgents(UUID distributorId) {
        return queryBus.query(new ListAgentsQuery(distributorId));
    }

    @Override
    public Mono<UUID> updateAgent(UpdateAgentCommand command) {
        return commandBus.send(command);
    }

    @Override
    public Mono<Void> deleteAgent(UUID distributorId, UUID agentId) {
        return commandBus.send(new DeleteAgentCommand(distributorId, agentId));
    }

    // --- Agent-Agency operations ---

    @Override
    public Mono<UUID> assignAgentAgency(AssignAgentAgencyCommand command) {
        return commandBus.send(command);
    }

    @Override
    public Mono<Void> listAgentAgencies(UUID distributorId) {
        return queryBus.query(new ListAgentAgenciesQuery(distributorId));
    }

    @Override
    public Mono<Void> removeAgentAgency(UUID distributorId, UUID agentAgencyId) {
        return commandBus.send(new RemoveAgentAgencyCommand(distributorId, agentAgencyId));
    }
}
