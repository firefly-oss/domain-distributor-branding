package com.firefly.domain.distributor.branding.core.distributor.handlers;

import com.firefly.core.distributor.sdk.api.DistributorAuthorizedTerritoriesApi;
import com.firefly.core.distributor.sdk.model.FilterRequestDistributorAuthorizedTerritoryDTO;
import com.firefly.core.distributor.sdk.model.PaginationResponse;
import com.firefly.domain.distributor.branding.core.distributor.queries.ListTerritoriesQuery;
import org.fireflyframework.cqrs.annotations.QueryHandlerComponent;
import org.fireflyframework.cqrs.query.QueryHandler;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Handler that lists all authorized territories for a distributor.
 */
@QueryHandlerComponent
public class ListTerritoriesHandler extends QueryHandler<ListTerritoriesQuery, PaginationResponse> {

    private final DistributorAuthorizedTerritoriesApi territoriesApi;

    public ListTerritoriesHandler(DistributorAuthorizedTerritoriesApi territoriesApi) {
        this.territoriesApi = territoriesApi;
    }

    @Override
    protected Mono<PaginationResponse> doHandle(ListTerritoriesQuery query) {
        return territoriesApi.filter3(query.distributorId(), new FilterRequestDistributorAuthorizedTerritoryDTO(), UUID.randomUUID().toString());
    }
}
