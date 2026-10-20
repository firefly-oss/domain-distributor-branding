package io.fireflysoftware.domain.distributor.branding.core.distributor.queries;

import io.fireflysoftware.core.distributor.sdk.model.DistributorDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.fireflyframework.cqrs.query.Query;

import java.util.UUID;

/**
 * Query to retrieve a distributor profile by its identifier.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetDistributorProfileQuery implements Query<DistributorDTO> {
    private UUID distributorId;
}
