package com.firefly.domain.distributor.branding.core.distributor.handlers;

import com.firefly.core.distributor.sdk.api.DistributorApi;
import com.firefly.core.distributor.sdk.model.DistributorDTO;
import com.firefly.domain.distributor.branding.core.distributor.queries.GetDistributorProfileQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fireflyframework.cqrs.annotations.QueryHandlerComponent;
import org.fireflyframework.cqrs.query.QueryHandler;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Handler that retrieves a distributor profile by its identifier.
 */
@Slf4j
@RequiredArgsConstructor
@QueryHandlerComponent
public class GetDistributorProfileHandler extends QueryHandler<GetDistributorProfileQuery, DistributorDTO> {

    private final DistributorApi distributorApi;

    @Override
    protected Mono<DistributorDTO> doHandle(GetDistributorProfileQuery query) {
        log.debug("Retrieving distributor profile for distributorId={}", query.getDistributorId());
        return distributorApi.getDistributorById(query.getDistributorId(), UUID.randomUUID().toString());
    }
}
