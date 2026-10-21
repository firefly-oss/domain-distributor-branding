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


package io.fireflysoftware.domain.distributor.branding.core.distributor.services.impl;

import io.fireflysoftware.core.distributor.sdk.model.DistributorTermsAndConditionsDTO;
import io.fireflysoftware.domain.distributor.branding.core.distributor.commands.*;
import io.fireflysoftware.domain.distributor.branding.core.distributor.queries.*;
import io.fireflysoftware.domain.distributor.branding.core.distributor.services.DistributorTermsService;
import org.fireflyframework.cqrs.command.CommandBus;
import org.fireflyframework.cqrs.query.QueryBus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Implementation of {@link DistributorTermsService} that dispatches operations
 * through the CQRS command and query buses.
 */
@Service
public class DistributorTermsServiceImpl implements DistributorTermsService {

    private final CommandBus commandBus;
    private final QueryBus queryBus;

    public DistributorTermsServiceImpl(CommandBus commandBus, QueryBus queryBus) {
        this.commandBus = commandBus;
        this.queryBus = queryBus;
    }

    @Override
    public Mono<DistributorTermsAndConditionsDTO> listTermsAndConditions(UUID distributorId) {
        return queryBus.query(new ListTermsAndConditionsQuery(distributorId));
    }

    @Override
    public Mono<DistributorTermsAndConditionsDTO> getActiveTermsAndConditions(UUID distributorId) {
        return queryBus.query(new GetActiveTermsAndConditionsQuery(distributorId));
    }

    @Override
    public Mono<DistributorTermsAndConditionsDTO> getLatestTermsAndConditions(UUID distributorId) {
        return queryBus.query(new GetLatestTermsAndConditionsQuery(distributorId));
    }

    @Override
    public Mono<UUID> createTermsAndConditions(CreateTermsAndConditionsCommand command) {
        return commandBus.send(command);
    }

    @Override
    public Mono<DistributorTermsAndConditionsDTO> getTermsAndConditionsDetail(UUID distributorId, UUID termsAndConditionsId) {
        return queryBus.query(new GetTermsAndConditionsDetailQuery(distributorId, termsAndConditionsId));
    }

    @Override
    public Mono<Void> deleteTermsAndConditions(UUID distributorId, UUID termsAndConditionsId) {
        return commandBus.send(new RemoveTermsAndConditionsCommand(distributorId, termsAndConditionsId));
    }

    @Override
    public Mono<DistributorTermsAndConditionsDTO> signTermsAndConditions(SignTermsAndConditionsCommand command) {
        return commandBus.send(command);
    }

    @Override
    public Mono<DistributorTermsAndConditionsDTO> activateTermsAndConditions(ActivateTermsAndConditionsCommand command) {
        return commandBus.send(command);
    }

    @Override
    public Mono<DistributorTermsAndConditionsDTO> deactivateTermsAndConditions(DeactivateTermsAndConditionsCommand command) {
        return commandBus.send(command);
    }

    @Override
    public Mono<Boolean> hasActiveSignedTerms(UUID distributorId) {
        return queryBus.query(new HasActiveSignedTermsQuery(distributorId));
    }
}
