package com.firefly.domain.distributor.branding.core.distributor.handlers;

import com.firefly.core.distributor.sdk.api.DistributorAgentsApi;
import com.firefly.core.distributor.sdk.model.DistributorAgentDTO;
import com.firefly.domain.distributor.branding.core.distributor.queries.GetAgentQuery;
import org.fireflyframework.cqrs.annotations.QueryHandlerComponent;
import org.fireflyframework.cqrs.query.QueryHandler;
import reactor.core.publisher.Mono;

/**
 * Handler that retrieves a single agent by its identifier.
 */
@QueryHandlerComponent
public class GetAgentHandler extends QueryHandler<GetAgentQuery, DistributorAgentDTO> {

    private final DistributorAgentsApi agentsApi;

    public GetAgentHandler(DistributorAgentsApi agentsApi) {
        this.agentsApi = agentsApi;
    }

    @Override
    protected Mono<DistributorAgentDTO> doHandle(GetAgentQuery query) {
        return agentsApi.getById4(query.distributorId(), query.agentId());
    }
}
