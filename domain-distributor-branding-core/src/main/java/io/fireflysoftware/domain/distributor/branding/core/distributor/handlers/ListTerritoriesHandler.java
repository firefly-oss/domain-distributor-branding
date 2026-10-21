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

import io.fireflysoftware.core.distributor.sdk.api.DistributorAuthorizedTerritoriesApi;
import io.fireflysoftware.core.distributor.sdk.model.FilterRequestDistributorAuthorizedTerritoryDTO;
import io.fireflysoftware.core.distributor.sdk.model.PaginationResponse;
import io.fireflysoftware.domain.distributor.branding.core.distributor.queries.ListTerritoriesQuery;
import org.fireflyframework.cqrs.annotations.QueryHandlerComponent;
import org.fireflyframework.cqrs.query.QueryHandler;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Handler that lists all authorized territories for a distributor.
 */
@QueryHandlerComponent
public class ListTerritoriesHandler extends QueryHandler<ListTerritoriesQuery, PaginationResponse> {

    private final DistributorAuthorizedTerritoriesApi territoriesApi;

    public ListTerritoriesHandler(DistributorAuthorizedTerritoriesApi territoriesApi) {
        this.territoriesApi = territoriesApi;
    }

    @Override
    protected Mono<PaginationResponse> doHandle(ListTerritoriesQuery query) {
        return territoriesApi.filter3(query.distributorId(), new FilterRequestDistributorAuthorizedTerritoryDTO(), UUID.randomUUID().toString());
    }
}
