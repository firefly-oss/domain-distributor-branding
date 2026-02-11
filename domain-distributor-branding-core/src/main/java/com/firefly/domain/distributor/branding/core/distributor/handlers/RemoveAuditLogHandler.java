package com.firefly.domain.distributor.branding.core.distributor.handlers;

import com.firefly.common.cqrs.annotations.CommandHandlerComponent;
import com.firefly.common.cqrs.command.CommandHandler;
import com.firefly.core.distributor.sdk.api.DistributorAuditLogApi;
import com.firefly.domain.distributor.branding.core.distributor.commands.RemoveAuditLogCommand;
import reactor.core.publisher.Mono;

@CommandHandlerComponent
public class RemoveAuditLogHandler extends CommandHandler<RemoveAuditLogCommand, Void> {

    private final DistributorAuditLogApi distributorAuditLogApi;

    public RemoveAuditLogHandler(DistributorAuditLogApi distributorAuditLogApi) {
        this.distributorAuditLogApi = distributorAuditLogApi;
    }

    @Override
    protected Mono<Void> doHandle(RemoveAuditLogCommand cmd) {
        return distributorAuditLogApi.deleteDistributorAuditLog(cmd.distributorId(), cmd.auditLogId());
    }
}

