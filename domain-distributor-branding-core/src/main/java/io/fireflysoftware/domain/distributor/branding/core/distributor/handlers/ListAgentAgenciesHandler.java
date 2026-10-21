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

import io.fireflysoftware.core.distributor.sdk.api.DistributorAgentAgencyApi;
import io.fireflysoftware.core.distributor.sdk.model.FilterRequestDistributorAgentAgencyDTO;
import io.fireflysoftware.domain.distributor.branding.core.distributor.queries.ListAgentAgenciesQuery;
import org.fireflyframework.cqrs.annotations.QueryHandlerComponent;
import org.fireflyframework.cqrs.query.QueryHandler;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Handler that lists all agent-agency assignments for a distributor.
 */
@QueryHandlerComponent
public class ListAgentAgenciesHandler extends QueryHandler<ListAgentAgenciesQuery, Void> {

    private final DistributorAgentAgencyApi agentAgencyApi;

    public ListAgentAgenciesHandler(DistributorAgentAgencyApi agentAgencyApi) {
        this.agentAgencyApi = agentAgencyApi;
    }

    @Override
    protected Mono<Void> doHandle(ListAgentAgenciesQuery query) {
        return agentAgencyApi.filter5(query.distributorId(), new FilterRequestDistributorAgentAgencyDTO(), UUID.randomUUID().toString());
    }
}
