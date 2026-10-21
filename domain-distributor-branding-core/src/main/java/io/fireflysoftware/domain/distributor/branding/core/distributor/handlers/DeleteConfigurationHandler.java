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
import io.fireflysoftware.domain.distributor.branding.core.distributor.commands.DeleteConfigurationCommand;
import org.fireflyframework.cqrs.annotations.CommandHandlerComponent;
import org.fireflyframework.cqrs.command.CommandHandler;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Handler that deletes a configuration from a distributor.
 */
@CommandHandlerComponent
public class DeleteConfigurationHandler extends CommandHandler<DeleteConfigurationCommand, Void> {

    private final DistributorConfigurationsApi configurationsApi;

    public DeleteConfigurationHandler(DistributorConfigurationsApi configurationsApi) {
        this.configurationsApi = configurationsApi;
    }

    @Override
    protected Mono<Void> doHandle(DeleteConfigurationCommand cmd) {
        return configurationsApi.delete2(cmd.distributorId(), cmd.configurationId(), UUID.randomUUID().toString());
    }
}
