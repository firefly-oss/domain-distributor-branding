package io.fireflysoftware.domain.distributor.branding.core.distributor.queries;

import io.fireflysoftware.core.distributor.sdk.model.DistributorAgentDTO;
import org.fireflyframework.cqrs.query.Query;

import java.util.UUID;

/**
 * Query to retrieve a single agent by its identifier.
 */
public record GetAgentQuery(
        UUID distributorId,
        UUID agentId
) implements Query<DistributorAgentDTO> {}
