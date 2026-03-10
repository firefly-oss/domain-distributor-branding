package com.firefly.domain.distributor.branding.core.distributor.handlers;

import com.firefly.core.distributor.sdk.api.DistributorAgenciesApi;
import com.firefly.core.distributor.sdk.model.FilterRequestDistributorAgencyDTO;
import com.firefly.core.distributor.sdk.model.PaginationResponse;
import com.firefly.domain.distributor.branding.core.distributor.queries.ListAgenciesQuery;
import org.fireflyframework.cqrs.annotations.QueryHandlerComponent;
import org.fireflyframework.cqrs.query.QueryHandler;
import reactor.core.publisher.Mono;

/**
 * Handler that lists all agencies for a distributor.
 */
@QueryHandlerComponent
public class ListAgenciesHandler extends QueryHandler<ListAgenciesQuery, PaginationResponse> {

    private final DistributorAgenciesApi agenciesApi;

    public ListAgenciesHandler(DistributorAgenciesApi agenciesApi) {
        this.agenciesApi = agenciesApi;
    }

    @Override
    protected Mono<PaginationResponse> doHandle(ListAgenciesQuery query) {
        return agenciesApi.filter6(query.distributorId(), new FilterRequestDistributorAgencyDTO(), null);
    }
}
