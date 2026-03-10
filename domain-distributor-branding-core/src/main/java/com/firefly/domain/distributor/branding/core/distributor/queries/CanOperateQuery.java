package com.firefly.domain.distributor.branding.core.distributor.queries;

import org.fireflyframework.cqrs.query.Query;

import java.util.UUID;

/**
 * Query to check whether a distributor can operate in a given location.
 */
public record CanOperateQuery(
        UUID distributorId,
        UUID operationId,
        UUID locationId
) implements Query<Boolean> {}
