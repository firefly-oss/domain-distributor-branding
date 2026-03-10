package com.firefly.domain.distributor.branding.core.distributor.queries;

import com.firefly.core.distributor.sdk.model.DistributorConfigurationDTO;
import org.fireflyframework.cqrs.query.Query;

import java.util.UUID;

/**
 * Query to retrieve a single configuration by its identifier.
 */
public record GetConfigurationQuery(
        UUID distributorId,
        UUID configurationId
) implements Query<DistributorConfigurationDTO> {}
