package com.firefly.domain.distributor.branding.core.distributor.handlers;

import com.firefly.core.distributor.sdk.api.DistributorConfigurationsApi;
import com.firefly.core.distributor.sdk.model.FilterRequestDistributorConfigurationDTO;
import com.firefly.core.distributor.sdk.model.PaginationResponse;
import com.firefly.domain.distributor.branding.core.distributor.queries.ListConfigurationsQuery;
import org.fireflyframework.cqrs.annotations.QueryHandlerComponent;
import org.fireflyframework.cqrs.query.QueryHandler;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Handler that lists all configurations for a distributor.
 */
@QueryHandlerComponent
public class ListConfigurationsHandler extends QueryHandler<ListConfigurationsQuery, PaginationResponse> {

    private final DistributorConfigurationsApi configurationsApi;

    public ListConfigurationsHandler(DistributorConfigurationsApi configurationsApi) {
        this.configurationsApi = configurationsApi;
    }

    @Override
    protected Mono<PaginationResponse> doHandle(ListConfigurationsQuery query) {
        return configurationsApi.filter2(query.distributorId(), new FilterRequestDistributorConfigurationDTO(), UUID.randomUUID().toString());
    }
}
