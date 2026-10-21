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

import org.fireflyframework.cqrs.annotations.CommandHandlerComponent;
import org.fireflyframework.cqrs.command.CommandHandler;
import io.fireflysoftware.core.distributor.sdk.api.DistributorBrandingApi;
import io.fireflysoftware.domain.distributor.branding.core.distributor.commands.RemoveBrandingCommand;
import reactor.core.publisher.Mono;
import java.util.UUID;

@CommandHandlerComponent
public class RemoveBrandingHandler extends CommandHandler<RemoveBrandingCommand, Void> {

    private final DistributorBrandingApi distributorBrandingApi;

    public RemoveBrandingHandler(DistributorBrandingApi distributorBrandingApi) {
        this.distributorBrandingApi = distributorBrandingApi;
    }

    @Override
    protected Mono<Void> doHandle(RemoveBrandingCommand cmd) {
        return distributorBrandingApi.deleteDistributorBranding(cmd.distributorId(), cmd.brandingId(), UUID.randomUUID().toString());
    }
}

