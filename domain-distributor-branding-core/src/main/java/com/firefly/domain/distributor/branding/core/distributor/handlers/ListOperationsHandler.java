package com.firefly.domain.distributor.branding.core.distributor.handlers;

import com.firefly.core.distributor.sdk.api.DistributorOperationsApi;
import com.firefly.core.distributor.sdk.model.DistributorOperationDTO;
import com.firefly.domain.distributor.branding.core.distributor.queries.ListOperationsQuery;
import org.fireflyframework.cqrs.annotations.QueryHandlerComponent;
import org.fireflyframework.cqrs.query.QueryHandler;
import reactor.core.publisher.Mono;

/**
 * Handler that lists all operations for a distributor.
 */
@QueryHandlerComponent
public class ListOperationsHandler extends QueryHandler<ListOperationsQuery, DistributorOperationDTO> {

    private final DistributorOperationsApi operationsApi;

    public ListOperationsHandler(DistributorOperationsApi operationsApi) {
        this.operationsApi = operationsApi;
    }

    @Override
    protected Mono<DistributorOperationDTO> doHandle(ListOperationsQuery query) {
        return operationsApi.getOperationsByDistributorId(query.distributorId());
    }
}
