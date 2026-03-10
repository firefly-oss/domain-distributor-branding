package com.firefly.domain.distributor.branding.core.distributor.queries;

import com.firefly.core.distributor.sdk.model.DistributorAuthorizedTerritoryDTO;
import org.fireflyframework.cqrs.query.Query;

import java.util.UUID;

/**
 * Query to retrieve a single authorized territory by its identifier.
 */
public record GetTerritoryQuery(
        UUID distributorId,
        UUID territoryId
) implements Query<DistributorAuthorizedTerritoryDTO> {}
