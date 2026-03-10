package com.firefly.domain.distributor.branding.core.distributor.queries;

import org.fireflyframework.cqrs.query.Query;

import java.util.UUID;

/**
 * Query to list all agent-agency assignments for a distributor.
 */
public record ListAgentAgenciesQuery(
        UUID distributorId
) implements Query<Void> {}
