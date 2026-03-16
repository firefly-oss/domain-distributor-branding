package com.firefly.domain.distributor.branding.core.distributor.handlers;

import com.firefly.core.distributor.sdk.api.DistributorConfigurationsApi;
import com.firefly.core.distributor.sdk.model.DistributorConfigurationDTO;
import com.firefly.domain.distributor.branding.core.distributor.queries.GetConfigurationQuery;
import org.fireflyframework.cqrs.annotations.QueryHandlerComponent;
import org.fireflyframework.cqrs.query.QueryHandler;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Handler that retrieves a single configuration by its identifier.
 */
@QueryHandlerComponent
public class GetConfigurationHandler extends QueryHandler<GetConfigurationQuery, DistributorConfigurationDTO> {

    private final DistributorConfigurationsApi configurationsApi;

    public GetConfigurationHandler(DistributorConfigurationsApi configurationsApi) {
        this.configurationsApi = configurationsApi;
    }

    @Override
    protected Mono<DistributorConfigurationDTO> doHandle(GetConfigurationQuery query) {
        return configurationsApi.getById2(query.distributorId(), query.configurationId(), UUID.randomUUID().toString());
    }
}
