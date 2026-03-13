package com.firefly.domain.distributor.branding.core.distributor.handlers;

import com.firefly.core.distributor.sdk.api.DistributorOperationsApi;
import com.firefly.core.distributor.sdk.model.DistributorOperationDTO;
import com.firefly.domain.distributor.branding.core.distributor.commands.*;
import com.firefly.domain.distributor.branding.core.distributor.queries.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for operations command and query handlers.
 */
@ExtendWith(MockitoExtension.class)
class OperationsHandlersTest {

    @Mock
    private DistributorOperationsApi operationsApi;

    private CreateOperationHandler createHandler;
    private GetOperationHandler getHandler;
    private ListOperationsHandler listHandler;
    private UpdateOperationHandler updateHandler;
    private DeleteOperationHandler deleteHandler;
    private ActivateOperationHandler activateHandler;
    private DeactivateOperationHandler deactivateHandler;
    private CanOperateHandler canOperateHandler;

    private final UUID distributorId = UUID.randomUUID();
    private final UUID operationId = UUID.randomUUID();
    private final UUID userId = UUID.randomUUID();
    private final UUID locationId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        createHandler = new CreateOperationHandler(operationsApi);
        getHandler = new GetOperationHandler(operationsApi);
        listHandler = new ListOperationsHandler(operationsApi);
        updateHandler = new UpdateOperationHandler(operationsApi);
        deleteHandler = new DeleteOperationHandler(operationsApi);
        activateHandler = new ActivateOperationHandler(operationsApi);
        deactivateHandler = new DeactivateOperationHandler(operationsApi);
        canOperateHandler = new CanOperateHandler(operationsApi);
    }

    @Test
    void createOperation_shouldCallApi() {
        var dto = new DistributorOperationDTO(operationId, null, null, null, null);

        when(operationsApi.createDistributorOperation(eq(distributorId), any(CreateOperationCommand.class)))
                .thenReturn(Mono.just(dto));

        var cmd = new CreateOperationCommand();
        cmd.withDistributorId(distributorId);

        StepVerifier.create(createHandler.doHandle(cmd))
                .expectNext(operationId)
                .verifyComplete();

        verify(operationsApi).createDistributorOperation(eq(distributorId), any());
    }

    @Test
    void getOperation_shouldCallApi() {
        var dto = new DistributorOperationDTO();
        when(operationsApi.getDistributorOperationById(distributorId, operationId))
                .thenReturn(Mono.just(dto));

        StepVerifier.create(getHandler.doHandle(new GetOperationQuery(distributorId, operationId)))
                .expectNext(dto)
                .verifyComplete();

        verify(operationsApi).getDistributorOperationById(distributorId, operationId);
    }

    @Test
    void listOperations_shouldCallApi() {
        var dto = new DistributorOperationDTO();
        when(operationsApi.getOperationsByDistributorId(distributorId))
                .thenReturn(Mono.just(dto));

        StepVerifier.create(listHandler.doHandle(new ListOperationsQuery(distributorId)))
                .expectNext(dto)
                .verifyComplete();

        verify(operationsApi).getOperationsByDistributorId(distributorId);
    }

    @Test
    void updateOperation_shouldCallApi() {
        var dto = new DistributorOperationDTO(operationId, null, null, null, null);

        when(operationsApi.updateDistributorOperation(eq(distributorId), eq(operationId), any(UpdateOperationCommand.class)))
                .thenReturn(Mono.just(dto));

        var cmd = new UpdateOperationCommand();
        cmd.withDistributorId(distributorId);
        cmd.withId(operationId);

        StepVerifier.create(updateHandler.doHandle(cmd))
                .expectNext(operationId)
                .verifyComplete();

        verify(operationsApi).updateDistributorOperation(eq(distributorId), eq(operationId), any());
    }

    @Test
    void deleteOperation_shouldCallApi() {
        when(operationsApi.deleteDistributorOperation(distributorId, operationId))
                .thenReturn(Mono.empty());

        StepVerifier.create(deleteHandler.doHandle(new DeleteOperationCommand(distributorId, operationId)))
                .verifyComplete();

        verify(operationsApi).deleteDistributorOperation(distributorId, operationId);
    }

    @Test
    void activateOperation_shouldCallApi() {
        var dto = new DistributorOperationDTO();
        when(operationsApi.activateDistributorOperation(distributorId, operationId, userId))
                .thenReturn(Mono.just(dto));

        var cmd = new ActivateOperationCommand(distributorId, operationId, userId);

        StepVerifier.create(activateHandler.doHandle(cmd))
                .expectNext(dto)
                .verifyComplete();

        verify(operationsApi).activateDistributorOperation(distributorId, operationId, userId);
    }

    @Test
    void deactivateOperation_shouldCallApi() {
        var dto = new DistributorOperationDTO();
        when(operationsApi.deactivateDistributorOperation(distributorId, operationId, userId))
                .thenReturn(Mono.just(dto));

        var cmd = new DeactivateOperationCommand(distributorId, operationId, userId);

        StepVerifier.create(deactivateHandler.doHandle(cmd))
                .expectNext(dto)
                .verifyComplete();

        verify(operationsApi).deactivateDistributorOperation(distributorId, operationId, userId);
    }

    @Test
    void canOperate_shouldCallApi() {
        when(operationsApi.canDistributorOperateInLocation(distributorId, operationId, locationId))
                .thenReturn(Mono.just(true));

        StepVerifier.create(canOperateHandler.doHandle(new CanOperateQuery(distributorId, operationId, locationId)))
                .expectNext(true)
                .verifyComplete();

        verify(operationsApi).canDistributorOperateInLocation(distributorId, operationId, locationId);
    }
}
