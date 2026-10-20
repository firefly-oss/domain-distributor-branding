package io.fireflysoftware.domain.distributor.branding.core.distributor.queries;

import io.fireflysoftware.core.distributor.sdk.model.DistributorOperationDTO;
import org.fireflyframework.cqrs.query.Query;

import java.util.UUID;

/**
 * Query to list all operations for a distributor.
 */
public record ListOperationsQuery(
        UUID distributorId
) implements Query<DistributorOperationDTO> {}
