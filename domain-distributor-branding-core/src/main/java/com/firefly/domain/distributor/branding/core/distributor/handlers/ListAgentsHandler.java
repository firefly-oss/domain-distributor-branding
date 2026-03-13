package com.firefly.domain.distributor.branding.core.distributor.handlers;

import com.firefly.core.distributor.sdk.api.DistributorAgentsApi;
import com.firefly.core.distributor.sdk.model.FilterRequestDistributorAgentDTO;
import com.firefly.core.distributor.sdk.model.PaginationResponse;
import com.firefly.domain.distributor.branding.core.distributor.queries.ListAgentsQuery;
import org.fireflyframework.cqrs.annotations.QueryHandlerComponent;
import org.fireflyframework.cqrs.query.QueryHandler;
import reactor.core.publisher.Mono;

/**
 * Handler that lists all agents for a distributor.
 */
@QueryHandlerComponent
public class ListAgentsHandler extends QueryHandler<ListAgentsQuery, PaginationResponse> {

    private final DistributorAgentsApi agentsApi;

    public ListAgentsHandler(DistributorAgentsApi agentsApi) {
        this.agentsApi = agentsApi;
    }

    @Override
    protected Mono<PaginationResponse> doHandle(ListAgentsQuery query) {
        return agentsApi.filter4(query.distributorId(), new FilterRequestDistributorAgentDTO());
    }
}
