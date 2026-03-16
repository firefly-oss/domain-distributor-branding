package com.firefly.domain.distributor.branding.core.distributor.handlers;

import com.firefly.core.distributor.sdk.api.DistributorOperationsApi;
import com.firefly.domain.distributor.branding.core.distributor.commands.CreateOperationCommand;
import org.fireflyframework.cqrs.annotations.CommandHandlerComponent;
import org.fireflyframework.cqrs.command.CommandHandler;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.UUID;

/**
 * Handler that creates a new operation for a distributor.
 */
@CommandHandlerComponent
public class CreateOperationHandler extends CommandHandler<CreateOperationCommand, UUID> {

    private final DistributorOperationsApi operationsApi;

    public CreateOperationHandler(DistributorOperationsApi operationsApi) {
        this.operationsApi = operationsApi;
    }

    @Override
    protected Mono<UUID> doHandle(CreateOperationCommand cmd) {
        return operationsApi.createDistributorOperation(cmd.getDistributorId(), cmd, UUID.randomUUID().toString())
                .mapNotNull(dto -> Objects.requireNonNull(Objects.requireNonNull(dto)).getId());
    }
}
