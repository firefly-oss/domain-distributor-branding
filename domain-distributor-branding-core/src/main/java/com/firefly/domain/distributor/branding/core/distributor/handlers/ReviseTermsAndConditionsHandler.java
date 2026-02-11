/*
 * Copyright 2025 Firefly Software Solutions Inc
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

package com.firefly.domain.distributor.branding.core.distributor.handlers;

import com.firefly.common.cqrs.annotations.CommandHandlerComponent;
import com.firefly.common.cqrs.command.CommandHandler;
import com.firefly.core.distributor.sdk.api.DistributorTermsAndConditionsApi;
import com.firefly.domain.distributor.branding.core.distributor.commands.ReviseTermsAndConditionsCommand;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.UUID;

@CommandHandlerComponent
public class ReviseTermsAndConditionsHandler extends CommandHandler<ReviseTermsAndConditionsCommand, UUID> {

    private final DistributorTermsAndConditionsApi distributorTermsAndConditionsApi;

    public ReviseTermsAndConditionsHandler(DistributorTermsAndConditionsApi distributorTermsAndConditionsApi) {
        this.distributorTermsAndConditionsApi = distributorTermsAndConditionsApi;
    }

    @Override
    protected Mono<UUID> doHandle(ReviseTermsAndConditionsCommand cmd) {
        return distributorTermsAndConditionsApi.updateDistributorTermsAndConditions(cmd.getDistributorId(), cmd.getId(), cmd, UUID.randomUUID().toString())
                .mapNotNull(distributorTermsAndConditionsDTO ->
                        Objects.requireNonNull(Objects.requireNonNull(distributorTermsAndConditionsDTO).getId()));
    }
}

