package com.firefly.domain.distributor.branding.core.distributor.queries;

import com.firefly.core.distributor.sdk.model.DistributorOperationDTO;
import org.fireflyframework.cqrs.query.Query;

import java.util.UUID;

/**
 * Query to retrieve a single operation by its identifier.
 */
public record GetOperationQuery(
        UUID distributorId,
        UUID operationId
) implements Query<DistributorOperationDTO> {}
