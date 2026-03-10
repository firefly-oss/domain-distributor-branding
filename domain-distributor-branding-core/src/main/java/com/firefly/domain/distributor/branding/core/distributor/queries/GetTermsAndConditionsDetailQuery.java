package com.firefly.domain.distributor.branding.core.distributor.queries;

import com.firefly.core.distributor.sdk.model.DistributorTermsAndConditionsDTO;
import org.fireflyframework.cqrs.query.Query;

import java.util.UUID;

/**
 * Query to retrieve the detail of a specific terms and conditions entry.
 */
public record GetTermsAndConditionsDetailQuery(
        UUID distributorId,
        UUID termsAndConditionsId
) implements Query<DistributorTermsAndConditionsDTO> {}
