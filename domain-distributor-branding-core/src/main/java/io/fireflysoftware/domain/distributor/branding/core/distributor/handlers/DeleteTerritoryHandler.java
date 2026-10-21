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
import io.fireflysoftware.domain.distributor.branding.core.distributor.commands.DeleteTerritoryCommand;
import org.fireflyframework.cqrs.annotations.CommandHandlerComponent;
import org.fireflyframework.cqrs.command.CommandHandler;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Handler that deletes an authorized territory from a distributor.
 */
@CommandHandlerComponent
public class DeleteTerritoryHandler extends CommandHandler<DeleteTerritoryCommand, Void> {

    private final DistributorAuthorizedTerritoriesApi territoriesApi;

    public DeleteTerritoryHandler(DistributorAuthorizedTerritoriesApi territoriesApi) {
        this.territoriesApi = territoriesApi;
    }

    @Override
    protected Mono<Void> doHandle(DeleteTerritoryCommand cmd) {
        return territoriesApi.delete3(cmd.distributorId(), cmd.territoryId(), UUID.randomUUID().toString());
    }
}
