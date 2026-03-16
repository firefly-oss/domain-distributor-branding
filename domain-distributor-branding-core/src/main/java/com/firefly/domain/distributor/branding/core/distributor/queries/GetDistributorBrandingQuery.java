package com.firefly.domain.distributor.branding.core.distributor.queries;

import com.firefly.core.distributor.sdk.model.DistributorBrandingDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.fireflyframework.cqrs.query.Query;

import java.util.UUID;

/**
 * Query to retrieve a distributor branding by its identifier.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetDistributorBrandingQuery implements Query<DistributorBrandingDTO> {
    private UUID distributorId;
    private UUID brandingId;
}
