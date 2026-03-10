package com.firefly.domain.distributor.branding.core.distributor.handlers;

import com.firefly.core.distributor.sdk.api.DistributorAuthorizedTerritoriesApi;
import com.firefly.core.distributor.sdk.model.DistributorAuthorizedTerritoryDTO;
import com.firefly.domain.distributor.branding.core.distributor.queries.GetTerritoryQuery;
import org.fireflyframework.cqrs.annotations.QueryHandlerComponent;
import org.fireflyframework.cqrs.query.QueryHandler;
import reactor.core.publisher.Mono;

/**
 * Handler that retrieves a single authorized territory by its identifier.
 */
@QueryHandlerComponent
public class GetTerritoryHandler extends QueryHandler<GetTerritoryQuery, DistributorAuthorizedTerritoryDTO> {

    private final DistributorAuthorizedTerritoriesApi territoriesApi;

    public GetTerritoryHandler(DistributorAuthorizedTerritoriesApi territoriesApi) {
        this.territoriesApi = territoriesApi;
    }

    @Override
    protected Mono<DistributorAuthorizedTerritoryDTO> doHandle(GetTerritoryQuery query) {
        return territoriesApi.getById3(query.distributorId(), query.territoryId(), null);
    }
}
