/*
 * Copyright 2025 Firefly Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package io.fireflysoftware.domain.distributor.branding.core.distributor.handlers;

import io.fireflysoftware.core.distributor.sdk.api.DistributorBrandingApi;
import io.fireflysoftware.core.distributor.sdk.model.DistributorBrandingDTO;
import io.fireflysoftware.domain.distributor.branding.core.distributor.queries.GetDistributorBrandingQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fireflyframework.cqrs.annotations.QueryHandlerComponent;
import org.fireflyframework.cqrs.query.QueryHandler;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Handler that retrieves a distributor branding by its identifier.
 */
@Slf4j
@RequiredArgsConstructor
@QueryHandlerComponent
public class GetDistributorBrandingQueryHandler extends QueryHandler<GetDistributorBrandingQuery, DistributorBrandingDTO> {

    private final DistributorBrandingApi distributorBrandingApi;

    @Override
    protected Mono<DistributorBrandingDTO> doHandle(GetDistributorBrandingQuery query) {
        log.debug("Retrieving distributor branding for distributorId={}, brandingId={}", query.getDistributorId(), query.getBrandingId());
        return distributorBrandingApi.getDistributorBrandingById(query.getDistributorId(), query.getBrandingId(), UUID.randomUUID().toString());
    }
}
