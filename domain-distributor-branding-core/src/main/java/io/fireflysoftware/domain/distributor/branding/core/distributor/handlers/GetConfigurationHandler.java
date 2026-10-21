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

import io.fireflysoftware.core.distributor.sdk.api.DistributorConfigurationsApi;
import io.fireflysoftware.core.distributor.sdk.model.DistributorConfigurationDTO;
import io.fireflysoftware.domain.distributor.branding.core.distributor.queries.GetConfigurationQuery;
import org.fireflyframework.cqrs.annotations.QueryHandlerComponent;
import org.fireflyframework.cqrs.query.QueryHandler;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Handler that retrieves a single configuration by its identifier.
 */
@QueryHandlerComponent
public class GetConfigurationHandler extends QueryHandler<GetConfigurationQuery, DistributorConfigurationDTO> {

    private final DistributorConfigurationsApi configurationsApi;

    public GetConfigurationHandler(DistributorConfigurationsApi configurationsApi) {
        this.configurationsApi = configurationsApi;
    }

    @Override
    protected Mono<DistributorConfigurationDTO> doHandle(GetConfigurationQuery query) {
        return configurationsApi.getById2(query.distributorId(), query.configurationId(), UUID.randomUUID().toString());
    }
}
