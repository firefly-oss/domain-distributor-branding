package com.firefly.domain.distributor.branding.core.distributor.queries;

import com.firefly.core.distributor.sdk.model.DistributorTermsAndConditionsDTO;
import org.fireflyframework.cqrs.query.Query;

import java.util.UUID;

/**
 * Query to retrieve the latest terms and conditions for a distributor.
 */
public record GetLatestTermsAndConditionsQuery(
        UUID distributorId
) implements Query<DistributorTermsAndConditionsDTO> {}
