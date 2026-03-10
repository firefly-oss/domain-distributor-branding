package com.firefly.domain.distributor.branding.core.distributor.services.impl;

import com.firefly.core.distributor.sdk.model.DistributorOperationDTO;
import com.firefly.domain.distributor.branding.core.distributor.commands.*;
import com.firefly.domain.distributor.branding.core.distributor.queries.*;
import com.firefly.domain.distributor.branding.core.distributor.services.DistributorOperationsService;
import org.fireflyframework.cqrs.command.CommandBus;
import org.fireflyframework.cqrs.query.QueryBus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Implementation of {@link DistributorOperationsService} that dispatches operations
 * through the CQRS command and query buses.
 */
@Service
public class DistributorOperationsServiceImpl implements DistributorOperationsService {

    private final CommandBus commandBus;
    private final QueryBus queryBus;

    public DistributorOperationsServiceImpl(CommandBus commandBus, QueryBus queryBus) {
        this.commandBus = commandBus;
        this.queryBus = queryBus;
    }

    @Override
    public Mono<UUID> createOperation(CreateOperationCommand command) {
        return commandBus.send(command);
    }

    @Override
    public Mono<DistributorOperationDTO> getOperation(UUID distributorId, UUID operationId) {
        return queryBus.query(new GetOperationQuery(distributorId, operationId));
    }

    @Override
    public Mono<DistributorOperationDTO> listOperations(UUID distributorId) {
        return queryBus.query(new ListOperationsQuery(distributorId));
    }

    @Override
    public Mono<UUID> updateOperation(UpdateOperationCommand command) {
        return commandBus.send(command);
    }

    @Override
    public Mono<Void> deleteOperation(UUID distributorId, UUID operationId) {
        return commandBus.send(new DeleteOperationCommand(distributorId, operationId));
    }

    @Override
    public Mono<DistributorOperationDTO> activateOperation(ActivateOperationCommand command) {
        return commandBus.send(command);
    }

    @Override
    public Mono<DistributorOperationDTO> deactivateOperation(DeactivateOperationCommand command) {
        return commandBus.send(command);
    }

    @Override
    public Mono<Boolean> canOperate(UUID distributorId, UUID operationId, UUID locationId) {
        return queryBus.query(new CanOperateQuery(distributorId, operationId, locationId));
    }
}
