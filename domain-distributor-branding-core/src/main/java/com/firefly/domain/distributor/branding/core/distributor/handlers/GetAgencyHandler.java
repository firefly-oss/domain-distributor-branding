package com.firefly.domain.distributor.branding.core.distributor.handlers;

import com.firefly.core.distributor.sdk.api.DistributorAgenciesApi;
import com.firefly.core.distributor.sdk.model.DistributorAgencyDTO;
import com.firefly.domain.distributor.branding.core.distributor.queries.GetAgencyQuery;
import org.fireflyframework.cqrs.annotations.QueryHandlerComponent;
import org.fireflyframework.cqrs.query.QueryHandler;
import reactor.core.publisher.Mono;

/**
 * Handler that retrieves a single agency by its identifier.
 */
@QueryHandlerComponent
public class GetAgencyHandler extends QueryHandler<GetAgencyQuery, DistributorAgencyDTO> {

    private final DistributorAgenciesApi agenciesApi;

    public GetAgencyHandler(DistributorAgenciesApi agenciesApi) {
        this.agenciesApi = agenciesApi;
    }

    @Override
    protected Mono<DistributorAgencyDTO> doHandle(GetAgencyQuery query) {
        return agenciesApi.getById6(query.distributorId(), query.agencyId());
    }
}
