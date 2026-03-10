package com.firefly.domain.distributor.branding.core.distributor.handlers;

import com.firefly.core.distributor.sdk.api.DistributorOperationsApi;
import com.firefly.core.distributor.sdk.model.DistributorOperationDTO;
import com.firefly.domain.distributor.branding.core.distributor.commands.DeactivateOperationCommand;
import org.fireflyframework.cqrs.annotations.CommandHandlerComponent;
import org.fireflyframework.cqrs.command.CommandHandler;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Handler that deactivates an operation for a distributor.
 */
@CommandHandlerComponent
public class DeactivateOperationHandler extends CommandHandler<DeactivateOperationCommand, DistributorOperationDTO> {

    private final DistributorOperationsApi operationsApi;

    public DeactivateOperationHandler(DistributorOperationsApi operationsApi) {
        this.operationsApi = operationsApi;
    }

    @Override
    protected Mono<DistributorOperationDTO> doHandle(DeactivateOperationCommand cmd) {
        return operationsApi.deactivateDistributorOperation(cmd.distributorId(), cmd.operationId(), cmd.deactivatedBy(), UUID.randomUUID().toString());
    }
}
