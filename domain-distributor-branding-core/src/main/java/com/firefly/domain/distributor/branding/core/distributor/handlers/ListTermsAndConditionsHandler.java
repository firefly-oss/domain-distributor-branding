package com.firefly.domain.distributor.branding.core.distributor.handlers;

import com.firefly.core.distributor.sdk.api.DistributorTermsAndConditionsApi;
import com.firefly.core.distributor.sdk.model.DistributorTermsAndConditionsDTO;
import com.firefly.domain.distributor.branding.core.distributor.queries.ListTermsAndConditionsQuery;
import org.fireflyframework.cqrs.annotations.QueryHandlerComponent;
import org.fireflyframework.cqrs.query.QueryHandler;
import reactor.core.publisher.Mono;

/**
 * Handler that lists all terms and conditions for a distributor.
 */
@QueryHandlerComponent
public class ListTermsAndConditionsHandler extends QueryHandler<ListTermsAndConditionsQuery, DistributorTermsAndConditionsDTO> {

    private final DistributorTermsAndConditionsApi termsApi;

    public ListTermsAndConditionsHandler(DistributorTermsAndConditionsApi termsApi) {
        this.termsApi = termsApi;
    }

    @Override
    protected Mono<DistributorTermsAndConditionsDTO> doHandle(ListTermsAndConditionsQuery query) {
        return termsApi.getTermsAndConditionsByDistributorId(query.distributorId());
    }
}
