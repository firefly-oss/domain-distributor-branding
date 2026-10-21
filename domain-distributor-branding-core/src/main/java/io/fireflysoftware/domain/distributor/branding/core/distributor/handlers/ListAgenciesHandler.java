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

import io.fireflysoftware.core.distributor.sdk.api.DistributorAgenciesApi;
import io.fireflysoftware.core.distributor.sdk.model.FilterRequestDistributorAgencyDTO;
import io.fireflysoftware.core.distributor.sdk.model.PaginationResponse;
import io.fireflysoftware.domain.distributor.branding.core.distributor.queries.ListAgenciesQuery;
import org.fireflyframework.cqrs.annotations.QueryHandlerComponent;
import org.fireflyframework.cqrs.query.QueryHandler;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Handler that lists all agencies for a distributor.
 */
@QueryHandlerComponent
public class ListAgenciesHandler extends QueryHandler<ListAgenciesQuery, PaginationResponse> {

    private final DistributorAgenciesApi agenciesApi;

    public ListAgenciesHandler(DistributorAgenciesApi agenciesApi) {
        this.agenciesApi = agenciesApi;
    }

    @Override
    protected Mono<PaginationResponse> doHandle(ListAgenciesQuery query) {
        return agenciesApi.filter6(query.distributorId(), new FilterRequestDistributorAgencyDTO(), UUID.randomUUID().toString());
    }
}
