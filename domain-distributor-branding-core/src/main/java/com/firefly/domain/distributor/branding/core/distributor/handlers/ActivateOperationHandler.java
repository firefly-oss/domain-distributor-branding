package com.firefly.domain.distributor.branding.core.distributor.handlers;

import com.firefly.core.distributor.sdk.api.DistributorOperationsApi;
import com.firefly.core.distributor.sdk.model.DistributorOperationDTO;
import com.firefly.domain.distributor.branding.core.distributor.commands.ActivateOperationCommand;
import org.fireflyframework.cqrs.annotations.CommandHandlerComponent;
import org.fireflyframework.cqrs.command.CommandHandler;
import reactor.core.publisher.Mono;
import java.util.UUID;


/**
 * Handler that activates an operation for a distributor.
 */
@CommandHandlerComponent
public class ActivateOperationHandler extends CommandHandler<ActivateOperationCommand, DistributorOperationDTO> {

    private final DistributorOperationsApi operationsApi;

    public ActivateOperationHandler(DistributorOperationsApi operationsApi) {
        this.operationsApi = operationsApi;
    }

    @Override
    protected Mono<DistributorOperationDTO> doHandle(ActivateOperationCommand cmd) {
        return operationsApi.activateDistributorOperation(cmd.distributorId(), cmd.operationId(), cmd.activatedBy(), UUID.randomUUID().toString());
    }
}
