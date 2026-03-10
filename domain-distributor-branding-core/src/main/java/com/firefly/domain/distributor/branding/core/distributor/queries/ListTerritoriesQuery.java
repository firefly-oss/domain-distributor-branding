package com.firefly.domain.distributor.branding.core.distributor.queries;

import com.firefly.core.distributor.sdk.model.PaginationResponse;
import org.fireflyframework.cqrs.query.Query;

import java.util.UUID;

/**
 * Query to list all authorized territories for a distributor.
 */
public record ListTerritoriesQuery(
        UUID distributorId
) implements Query<PaginationResponse> {}
