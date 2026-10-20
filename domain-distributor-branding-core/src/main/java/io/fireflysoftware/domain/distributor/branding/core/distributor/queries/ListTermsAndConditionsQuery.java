package io.fireflysoftware.domain.distributor.branding.core.distributor.queries;

import io.fireflysoftware.core.distributor.sdk.model.DistributorTermsAndConditionsDTO;
import org.fireflyframework.cqrs.query.Query;

import java.util.UUID;

/**
 * Query to list all terms and conditions for a distributor.
 */
public record ListTermsAndConditionsQuery(
        UUID distributorId
) implements Query<DistributorTermsAndConditionsDTO> {}
