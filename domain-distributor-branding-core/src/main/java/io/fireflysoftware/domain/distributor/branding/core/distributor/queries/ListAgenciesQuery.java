package io.fireflysoftware.domain.distributor.branding.core.distributor.queries;

import io.fireflysoftware.core.distributor.sdk.model.PaginationResponse;
import org.fireflyframework.cqrs.query.Query;

import java.util.UUID;

/**
 * Query to list all agencies for a distributor.
 */
public record ListAgenciesQuery(
        UUID distributorId
) implements Query<PaginationResponse> {}
