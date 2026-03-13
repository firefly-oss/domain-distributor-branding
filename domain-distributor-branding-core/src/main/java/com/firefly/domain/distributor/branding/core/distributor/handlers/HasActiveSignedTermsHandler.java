package com.firefly.domain.distributor.branding.core.distributor.handlers;

import com.firefly.core.distributor.sdk.api.DistributorTermsAndConditionsApi;
import com.firefly.domain.distributor.branding.core.distributor.queries.HasActiveSignedTermsQuery;
import org.fireflyframework.cqrs.annotations.QueryHandlerComponent;
import org.fireflyframework.cqrs.query.QueryHandler;
import reactor.core.publisher.Mono;

/**
 * Handler that checks whether a distributor has active signed terms and conditions.
 */
@QueryHandlerComponent
public class HasActiveSignedTermsHandler extends QueryHandler<HasActiveSignedTermsQuery, Boolean> {

    private final DistributorTermsAndConditionsApi termsApi;

    public HasActiveSignedTermsHandler(DistributorTermsAndConditionsApi termsApi) {
        this.termsApi = termsApi;
    }

    @Override
    protected Mono<Boolean> doHandle(HasActiveSignedTermsQuery query) {
        return termsApi.hasActiveSignedTerms(query.distributorId());
    }
}
