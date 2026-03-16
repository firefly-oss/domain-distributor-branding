package com.firefly.domain.distributor.branding.core.distributor.handlers;

import com.firefly.core.distributor.sdk.api.DistributorBrandingApi;
import com.firefly.core.distributor.sdk.model.DistributorBrandingDTO;
import com.firefly.domain.distributor.branding.core.distributor.queries.GetDistributorBrandingQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fireflyframework.cqrs.annotations.QueryHandlerComponent;
import org.fireflyframework.cqrs.query.QueryHandler;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Handler that retrieves a distributor branding by its identifier.
 */
@Slf4j
@RequiredArgsConstructor
@QueryHandlerComponent
public class GetDistributorBrandingQueryHandler extends QueryHandler<GetDistributorBrandingQuery, DistributorBrandingDTO> {

    private final DistributorBrandingApi distributorBrandingApi;

    @Override
    protected Mono<DistributorBrandingDTO> doHandle(GetDistributorBrandingQuery query) {
        log.debug("Retrieving distributor branding for distributorId={}, brandingId={}", query.getDistributorId(), query.getBrandingId());
        return distributorBrandingApi.getDistributorBrandingById(query.getDistributorId(), query.getBrandingId(), UUID.randomUUID().toString());
    }
}
