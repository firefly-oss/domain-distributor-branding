package com.firefly.domain.distributor.branding.core.distributor.handlers;

import com.firefly.core.distributor.sdk.api.DistributorTermsAndConditionsApi;
import com.firefly.core.distributor.sdk.model.DistributorTermsAndConditionsDTO;
import com.firefly.domain.distributor.branding.core.distributor.queries.GetTermsAndConditionsDetailQuery;
import org.fireflyframework.cqrs.annotations.QueryHandlerComponent;
import org.fireflyframework.cqrs.query.QueryHandler;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Handler that retrieves the detail of a specific terms and conditions entry.
 */
@QueryHandlerComponent
public class GetTermsAndConditionsDetailHandler extends QueryHandler<GetTermsAndConditionsDetailQuery, DistributorTermsAndConditionsDTO> {

    private final DistributorTermsAndConditionsApi termsApi;

    public GetTermsAndConditionsDetailHandler(DistributorTermsAndConditionsApi termsApi) {
        this.termsApi = termsApi;
    }

    @Override
    protected Mono<DistributorTermsAndConditionsDTO> doHandle(GetTermsAndConditionsDetailQuery query) {
        return termsApi.getDistributorTermsAndConditionsById(query.distributorId(), query.termsAndConditionsId(), UUID.randomUUID().toString());
    }
}
