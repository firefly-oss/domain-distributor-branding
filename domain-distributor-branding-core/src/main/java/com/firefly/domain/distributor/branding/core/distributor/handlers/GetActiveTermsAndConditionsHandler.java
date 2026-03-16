package com.firefly.domain.distributor.branding.core.distributor.handlers;

import com.firefly.core.distributor.sdk.api.DistributorTermsAndConditionsApi;
import com.firefly.core.distributor.sdk.model.DistributorTermsAndConditionsDTO;
import com.firefly.domain.distributor.branding.core.distributor.queries.GetActiveTermsAndConditionsQuery;
import org.fireflyframework.cqrs.annotations.QueryHandlerComponent;
import org.fireflyframework.cqrs.query.QueryHandler;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Handler that retrieves the active terms and conditions for a distributor.
 */
@QueryHandlerComponent
public class GetActiveTermsAndConditionsHandler extends QueryHandler<GetActiveTermsAndConditionsQuery, DistributorTermsAndConditionsDTO> {

    private final DistributorTermsAndConditionsApi termsApi;

    public GetActiveTermsAndConditionsHandler(DistributorTermsAndConditionsApi termsApi) {
        this.termsApi = termsApi;
    }

    @Override
    protected Mono<DistributorTermsAndConditionsDTO> doHandle(GetActiveTermsAndConditionsQuery query) {
        return termsApi.getActiveTermsAndConditionsByDistributorId(query.distributorId(), UUID.randomUUID().toString());
    }
}
