package com.firefly.domain.distributor.branding.core.distributor.queries;

import com.firefly.core.distributor.sdk.model.DistributorAgencyDTO;
import org.fireflyframework.cqrs.query.Query;

import java.util.UUID;

/**
 * Query to retrieve a single agency by its identifier.
 */
public record GetAgencyQuery(
        UUID distributorId,
        UUID agencyId
) implements Query<DistributorAgencyDTO> {}
