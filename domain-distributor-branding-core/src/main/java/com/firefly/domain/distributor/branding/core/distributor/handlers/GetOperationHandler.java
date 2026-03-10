package com.firefly.domain.distributor.branding.core.distributor.handlers;

import com.firefly.core.distributor.sdk.api.DistributorOperationsApi;
import com.firefly.core.distributor.sdk.model.DistributorOperationDTO;
import com.firefly.domain.distributor.branding.core.distributor.queries.GetOperationQuery;
import org.fireflyframework.cqrs.annotations.QueryHandlerComponent;
import org.fireflyframework.cqrs.query.QueryHandler;
import reactor.core.publisher.Mono;

/**
 * Handler that retrieves a single operation by its identifier.
 */
@QueryHandlerComponent
public class GetOperationHandler extends QueryHandler<GetOperationQuery, DistributorOperationDTO> {

    private final DistributorOperationsApi operationsApi;

    public GetOperationHandler(DistributorOperationsApi operationsApi) {
        this.operationsApi = operationsApi;
    }

    @Override
    protected Mono<DistributorOperationDTO> doHandle(GetOperationQuery query) {
        return operationsApi.getDistributorOperationById(query.distributorId(), query.operationId(), null);
    }
}
