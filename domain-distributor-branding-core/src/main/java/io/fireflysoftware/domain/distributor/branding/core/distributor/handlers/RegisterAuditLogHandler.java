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
import io.fireflysoftware.core.distributor.sdk.api.DistributorAuditLogApi;
import io.fireflysoftware.domain.distributor.branding.core.distributor.commands.RegisterDistributorAuditLogCommand;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.UUID;

@CommandHandlerComponent
public class RegisterAuditLogHandler extends CommandHandler<RegisterDistributorAuditLogCommand, UUID> {

    private final DistributorAuditLogApi distributorAuditLogApi;

    public RegisterAuditLogHandler(DistributorAuditLogApi distributorAuditLogApi) {
        this.distributorAuditLogApi = distributorAuditLogApi;
    }

    @Override
    protected Mono<UUID> doHandle(RegisterDistributorAuditLogCommand cmd) {
        return distributorAuditLogApi.createDistributorAuditLog(cmd.getDistributorId(), cmd, UUID.randomUUID().toString())
                .mapNotNull(distributorAuditLogDTO ->
                        Objects.requireNonNull(Objects.requireNonNull(distributorAuditLogDTO)).getId());
    }
}

