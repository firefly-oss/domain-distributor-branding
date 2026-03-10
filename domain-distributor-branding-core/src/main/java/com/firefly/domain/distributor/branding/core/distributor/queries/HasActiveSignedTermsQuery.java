package com.firefly.domain.distributor.branding.core.distributor.queries;

import org.fireflyframework.cqrs.query.Query;

import java.util.UUID;

/**
 * Query to check whether a distributor has active signed terms and conditions.
 */
public record HasActiveSignedTermsQuery(
        UUID distributorId
) implements Query<Boolean> {}
