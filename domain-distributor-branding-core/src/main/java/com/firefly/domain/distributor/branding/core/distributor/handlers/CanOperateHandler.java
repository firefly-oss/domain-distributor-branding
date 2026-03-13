package com.firefly.domain.distributor.branding.core.distributor.handlers;

import com.firefly.core.distributor.sdk.api.DistributorOperationsApi;
import com.firefly.domain.distributor.branding.core.distributor.queries.CanOperateQuery;
import org.fireflyframework.cqrs.annotations.QueryHandlerComponent;
import org.fireflyframework.cqrs.query.QueryHandler;
import reactor.core.publisher.Mono;

/**
 * Handler that checks whether a distributor can operate in a given location.
 */
@QueryHandlerComponent
public class CanOperateHandler extends QueryHandler<CanOperateQuery, Boolean> {

    private final DistributorOperationsApi operationsApi;

    public CanOperateHandler(DistributorOperationsApi operationsApi) {
        this.operationsApi = operationsApi;
    }

    @Override
    protected Mono<Boolean> doHandle(CanOperateQuery query) {
        return operationsApi.canDistributorOperateInLocation(query.distributorId(), query.operationId(), query.locationId());
    }
}
