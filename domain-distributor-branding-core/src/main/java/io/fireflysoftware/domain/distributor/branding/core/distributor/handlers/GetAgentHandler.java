package io.fireflysoftware.domain.distributor.branding.core.distributor.handlers;

import io.fireflysoftware.core.distributor.sdk.api.DistributorAgentsApi;
import io.fireflysoftware.core.distributor.sdk.model.DistributorAgentDTO;
import io.fireflysoftware.domain.distributor.branding.core.distributor.queries.GetAgentQuery;
import org.fireflyframework.cqrs.annotations.QueryHandlerComponent;
import org.fireflyframework.cqrs.query.QueryHandler;
import reactor.core.publisher.Mono;
import java.util.UUID;

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
        return agentsApi.getById4(query.distributorId(), query.agentId(), UUID.randomUUID().toString());
    }
}
