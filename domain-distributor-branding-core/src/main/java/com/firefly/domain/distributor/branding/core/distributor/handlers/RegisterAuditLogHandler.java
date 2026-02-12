package com.firefly.domain.distributor.branding.core.distributor.handlers;

import org.fireflyframework.cqrs.annotations.CommandHandlerComponent;
import org.fireflyframework.cqrs.command.CommandHandler;
import com.firefly.core.distributor.sdk.api.DistributorAuditLogApi;
import com.firefly.domain.distributor.branding.core.distributor.commands.RegisterDistributorAuditLogCommand;
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

