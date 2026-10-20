package io.fireflysoftware.domain.distributor.branding.core.distributor.handlers;

import org.fireflyframework.cqrs.annotations.CommandHandlerComponent;
import org.fireflyframework.cqrs.command.CommandHandler;
import io.fireflysoftware.core.distributor.sdk.api.DistributorAuditLogApi;
import io.fireflysoftware.domain.distributor.branding.core.distributor.commands.RemoveAuditLogCommand;
import reactor.core.publisher.Mono;
import java.util.UUID;

@CommandHandlerComponent
public class RemoveAuditLogHandler extends CommandHandler<RemoveAuditLogCommand, Void> {

    private final DistributorAuditLogApi distributorAuditLogApi;

    public RemoveAuditLogHandler(DistributorAuditLogApi distributorAuditLogApi) {
        this.distributorAuditLogApi = distributorAuditLogApi;
    }

    @Override
    protected Mono<Void> doHandle(RemoveAuditLogCommand cmd) {
        return distributorAuditLogApi.deleteDistributorAuditLog(cmd.distributorId(), cmd.auditLogId(), UUID.randomUUID().toString());
    }
}

