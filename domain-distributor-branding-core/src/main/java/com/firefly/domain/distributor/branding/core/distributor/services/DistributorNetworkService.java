package com.firefly.domain.distributor.branding.core.distributor.services;

import com.firefly.core.distributor.sdk.model.DistributorAgencyDTO;
import com.firefly.core.distributor.sdk.model.DistributorAgentDTO;
import com.firefly.core.distributor.sdk.model.DistributorAuthorizedTerritoryDTO;
import com.firefly.core.distributor.sdk.model.PaginationResponse;
import com.firefly.domain.distributor.branding.core.distributor.commands.*;
import org.fireflyframework.orchestration.saga.engine.SagaResult;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Service that orchestrates territory, agency, agent, and agent-agency operations
 * for a distributor network through the CQRS command and query buses.
 */
public interface DistributorNetworkService {

    // --- Saga-orchestrated composite operations ---

    /**
     * Registers a complete agency setup: authorized territory, agency, and operational coverage
     * in a single transactional saga with compensation on failure.
     *
     * @param command the composite command containing territory, agency, and operation data
     * @return a Mono emitting the saga result
     */
    Mono<SagaResult> registerAgency(RegisterAgencyCommand command);

    /**
     * Creates an agent and assigns it to an agency in a single transactional saga
     * with compensation on failure.
     *
     * @param command the composite command containing agent and assignment data
     * @return a Mono emitting the saga result
     */
    Mono<SagaResult> registerAgent(RegisterAgentWithAssignmentCommand command);

    // --- Territory operations ---

    /**
     * Creates a new authorized territory for a distributor.
     *
     * @param command the command containing territory data
     * @return a Mono emitting the created territory identifier
     */
    Mono<UUID> createTerritory(CreateTerritoryCommand command);

    /**
     * Retrieves a single authorized territory by its identifier.
     *
     * @param distributorId the distributor identifier
     * @param territoryId the territory identifier
     * @return a Mono emitting the territory DTO
     */
    Mono<DistributorAuthorizedTerritoryDTO> getTerritory(UUID distributorId, UUID territoryId);

    /**
     * Lists all authorized territories for a distributor.
     *
     * @param distributorId the distributor identifier
     * @return a Mono emitting the paginated response
     */
    Mono<PaginationResponse> listTerritories(UUID distributorId);

    /**
     * Updates an existing authorized territory.
     *
     * @param command the command containing updated territory data
     * @return a Mono emitting the updated territory identifier
     */
    Mono<UUID> updateTerritory(UpdateTerritoryCommand command);

    /**
     * Deletes an authorized territory from a distributor.
     *
     * @param distributorId the distributor identifier
     * @param territoryId the territory identifier
     * @return a Mono completing when the territory is deleted
     */
    Mono<Void> deleteTerritory(UUID distributorId, UUID territoryId);

    // --- Agency operations ---

    /**
     * Creates a new agency for a distributor.
     *
     * @param command the command containing agency data
     * @return a Mono emitting the created agency identifier
     */
    Mono<UUID> createAgency(CreateAgencyCommand command);

    /**
     * Retrieves a single agency by its identifier.
     *
     * @param distributorId the distributor identifier
     * @param agencyId the agency identifier
     * @return a Mono emitting the agency DTO
     */
    Mono<DistributorAgencyDTO> getAgency(UUID distributorId, UUID agencyId);

    /**
     * Lists all agencies for a distributor.
     *
     * @param distributorId the distributor identifier
     * @return a Mono emitting the paginated response
     */
    Mono<PaginationResponse> listAgencies(UUID distributorId);

    /**
     * Updates an existing agency.
     *
     * @param command the command containing updated agency data
     * @return a Mono emitting the updated agency identifier
     */
    Mono<UUID> updateAgency(UpdateAgencyCommand command);

    /**
     * Deletes an agency from a distributor.
     *
     * @param distributorId the distributor identifier
     * @param agencyId the agency identifier
     * @return a Mono completing when the agency is deleted
     */
    Mono<Void> deleteAgency(UUID distributorId, UUID agencyId);

    // --- Agent operations ---

    /**
     * Creates a new agent for a distributor.
     *
     * @param command the command containing agent data
     * @return a Mono emitting the created agent identifier
     */
    Mono<UUID> createAgent(CreateAgentCommand command);

    /**
     * Retrieves a single agent by its identifier.
     *
     * @param distributorId the distributor identifier
     * @param agentId the agent identifier
     * @return a Mono emitting the agent DTO
     */
    Mono<DistributorAgentDTO> getAgent(UUID distributorId, UUID agentId);

    /**
     * Lists all agents for a distributor.
     *
     * @param distributorId the distributor identifier
     * @return a Mono emitting the paginated response
     */
    Mono<PaginationResponse> listAgents(UUID distributorId);

    /**
     * Updates an existing agent.
     *
     * @param command the command containing updated agent data
     * @return a Mono emitting the updated agent identifier
     */
    Mono<UUID> updateAgent(UpdateAgentCommand command);

    /**
     * Deletes an agent from a distributor.
     *
     * @param distributorId the distributor identifier
     * @param agentId the agent identifier
     * @return a Mono completing when the agent is deleted
     */
    Mono<Void> deleteAgent(UUID distributorId, UUID agentId);

    // --- Agent-Agency operations ---

    /**
     * Assigns an agent to an agency within a distributor.
     *
     * @param command the command containing the assignment data
     * @return a Mono emitting the created assignment identifier
     */
    Mono<UUID> assignAgentAgency(AssignAgentAgencyCommand command);

    /**
     * Lists all agent-agency assignments for a distributor.
     *
     * @param distributorId the distributor identifier
     * @return a Mono completing with the filter result
     */
    Mono<Void> listAgentAgencies(UUID distributorId);

    /**
     * Removes an agent-agency assignment from a distributor.
     *
     * @param distributorId the distributor identifier
     * @param agentAgencyId the agent-agency assignment identifier
     * @return a Mono completing when the assignment is removed
     */
    Mono<Void> removeAgentAgency(UUID distributorId, UUID agentAgencyId);
}
