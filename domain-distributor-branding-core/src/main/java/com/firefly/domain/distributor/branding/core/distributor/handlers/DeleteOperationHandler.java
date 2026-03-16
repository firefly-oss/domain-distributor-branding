package com.firefly.domain.distributor.branding.core.distributor.handlers;

import com.firefly.core.distributor.sdk.api.DistributorOperationsApi;
import com.firefly.domain.distributor.branding.core.distributor.commands.DeleteOperationCommand;
import org.fireflyframework.cqrs.annotations.CommandHandlerComponent;
import org.fireflyframework.cqrs.command.CommandHandler;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Handler that deletes an operation from a distributor.
 */
@CommandHandlerComponent
public class DeleteOperationHandler extends CommandHandler<DeleteOperationCommand, Void> {

    private final DistributorOperationsApi operationsApi;

    public DeleteOperationHandler(DistributorOperationsApi operationsApi) {
        this.operationsApi = operationsApi;
    }

    @Override
    protected Mono<Void> doHandle(DeleteOperationCommand cmd) {
        return operationsApi.deleteDistributorOperation(cmd.distributorId(), cmd.operationId(), UUID.randomUUID().toString());
    }
}
