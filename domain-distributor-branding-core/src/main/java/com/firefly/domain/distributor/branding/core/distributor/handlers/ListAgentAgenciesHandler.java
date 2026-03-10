package com.firefly.domain.distributor.branding.core.distributor.handlers;

import com.firefly.core.distributor.sdk.api.DistributorAgentAgencyApi;
import com.firefly.core.distributor.sdk.model.FilterRequestDistributorAgentAgencyDTO;
import com.firefly.domain.distributor.branding.core.distributor.queries.ListAgentAgenciesQuery;
import org.fireflyframework.cqrs.annotations.QueryHandlerComponent;
import org.fireflyframework.cqrs.query.QueryHandler;
import reactor.core.publisher.Mono;

/**
 * Handler that lists all agent-agency assignments for a distributor.
 */
@QueryHandlerComponent
public class ListAgentAgenciesHandler extends QueryHandler<ListAgentAgenciesQuery, Void> {

    private final DistributorAgentAgencyApi agentAgencyApi;

    public ListAgentAgenciesHandler(DistributorAgentAgencyApi agentAgencyApi) {
        this.agentAgencyApi = agentAgencyApi;
    }

    @Override
    protected Mono<Void> doHandle(ListAgentAgenciesQuery query) {
        return agentAgencyApi.filter5(query.distributorId(), new FilterRequestDistributorAgentAgencyDTO(), null);
    }
}
