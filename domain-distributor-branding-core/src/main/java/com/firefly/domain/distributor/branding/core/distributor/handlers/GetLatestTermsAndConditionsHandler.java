package com.firefly.domain.distributor.branding.core.distributor.handlers;

import com.firefly.core.distributor.sdk.api.DistributorTermsAndConditionsApi;
import com.firefly.core.distributor.sdk.model.DistributorTermsAndConditionsDTO;
import com.firefly.domain.distributor.branding.core.distributor.queries.GetLatestTermsAndConditionsQuery;
import org.fireflyframework.cqrs.annotations.QueryHandlerComponent;
import org.fireflyframework.cqrs.query.QueryHandler;
import reactor.core.publisher.Mono;

/**
 * Handler that retrieves the latest terms and conditions for a distributor.
 */
@QueryHandlerComponent
public class GetLatestTermsAndConditionsHandler extends QueryHandler<GetLatestTermsAndConditionsQuery, DistributorTermsAndConditionsDTO> {

    private final DistributorTermsAndConditionsApi termsApi;

    public GetLatestTermsAndConditionsHandler(DistributorTermsAndConditionsApi termsApi) {
        this.termsApi = termsApi;
    }

    @Override
    protected Mono<DistributorTermsAndConditionsDTO> doHandle(GetLatestTermsAndConditionsQuery query) {
        return termsApi.getLatestTermsAndConditions(query.distributorId(), null);
    }
}
