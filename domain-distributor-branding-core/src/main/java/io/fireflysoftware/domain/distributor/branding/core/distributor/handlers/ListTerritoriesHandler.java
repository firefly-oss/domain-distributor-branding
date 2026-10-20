package io.fireflysoftware.domain.distributor.branding.core.distributor.handlers;

import io.fireflysoftware.core.distributor.sdk.api.DistributorAuthorizedTerritoriesApi;
import io.fireflysoftware.core.distributor.sdk.model.FilterRequestDistributorAuthorizedTerritoryDTO;
import io.fireflysoftware.core.distributor.sdk.model.PaginationResponse;
import io.fireflysoftware.domain.distributor.branding.core.distributor.queries.ListTerritoriesQuery;
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
