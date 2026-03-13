package com.firefly.domain.distributor.branding.core.distributor.handlers;

import com.firefly.core.distributor.sdk.api.DistributorOperationsApi;
import com.firefly.domain.distributor.branding.core.distributor.commands.UpdateOperationCommand;
import org.fireflyframework.cqrs.annotations.CommandHandlerComponent;
import org.fireflyframework.cqrs.command.CommandHandler;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.UUID;

/**
 * Handler that updates an existing operation for a distributor.
 */
@CommandHandlerComponent
public class UpdateOperationHandler extends CommandHandler<UpdateOperationCommand, UUID> {

    private final DistributorOperationsApi operationsApi;

    public UpdateOperationHandler(DistributorOperationsApi operationsApi) {
        this.operationsApi = operationsApi;
    }

    @Override
    protected Mono<UUID> doHandle(UpdateOperationCommand cmd) {
        return operationsApi.updateDistributorOperation(cmd.getDistributorId(), cmd.getId(), cmd)
                .mapNotNull(dto -> Objects.requireNonNull(Objects.requireNonNull(dto).getId()));
    }
}
