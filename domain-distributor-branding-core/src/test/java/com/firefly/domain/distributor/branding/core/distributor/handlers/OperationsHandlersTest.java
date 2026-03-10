package com.firefly.domain.distributor.branding.core.distributor.handlers;

import com.firefly.core.distributor.sdk.api.DistributorOperationsApi;
import com.firefly.core.distributor.sdk.model.DistributorOperationDTO;
import com.firefly.core.distributor.sdk.model.FilterRequestDistributorOperationDTO;
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
    void createOperation_shouldCallApiWithIdempotencyKey() {
        var dto = new DistributorOperationDTO(operationId, null, null, null, null);

        when(operationsApi.createDistributorOperation(eq(distributorId), any(CreateOperationCommand.class), argThat(s -> s != null && !s.isEmpty())))
                .thenReturn(Mono.just(dto));

        var cmd = new CreateOperationCommand();
        cmd.withDistributorId(distributorId);

        StepVerifier.create(createHandler.doHandle(cmd))
                .expectNext(operationId)
                .verifyComplete();

        verify(operationsApi).createDistributorOperation(eq(distributorId), any(), argThat(s -> s != null && !s.isEmpty()));
    }

    @Test
    void getOperation_shouldCallApiWithNullIdempotencyKey() {
        var dto = new DistributorOperationDTO();
        when(operationsApi.getDistributorOperationById(distributorId, operationId, null))
                .thenReturn(Mono.just(dto));

        StepVerifier.create(getHandler.doHandle(new GetOperationQuery(distributorId, operationId)))
                .expectNext(dto)
                .verifyComplete();

        verify(operationsApi).getDistributorOperationById(distributorId, operationId, null);
    }

    @Test
    void listOperations_shouldCallApiWithNullIdempotencyKey() {
        var dto = new DistributorOperationDTO();
        when(operationsApi.getOperationsByDistributorId(distributorId, null))
                .thenReturn(Mono.just(dto));

        StepVerifier.create(listHandler.doHandle(new ListOperationsQuery(distributorId)))
                .expectNext(dto)
                .verifyComplete();

        verify(operationsApi).getOperationsByDistributorId(distributorId, null);
    }

    @Test
    void updateOperation_shouldCallApiWithIdempotencyKey() {
        var dto = new DistributorOperationDTO(operationId, null, null, null, null);

        when(operationsApi.updateDistributorOperation(eq(distributorId), eq(operationId), any(UpdateOperationCommand.class), argThat(s -> s != null && !s.isEmpty())))
                .thenReturn(Mono.just(dto));

        var cmd = new UpdateOperationCommand();
        cmd.withDistributorId(distributorId);
        cmd.withId(operationId);

        StepVerifier.create(updateHandler.doHandle(cmd))
                .expectNext(operationId)
                .verifyComplete();

        verify(operationsApi).updateDistributorOperation(eq(distributorId), eq(operationId), any(), argThat(s -> s != null && !s.isEmpty()));
    }

    @Test
    void deleteOperation_shouldCallApiWithNullIdempotencyKey() {
        when(operationsApi.deleteDistributorOperation(distributorId, operationId, null))
                .thenReturn(Mono.empty());

        StepVerifier.create(deleteHandler.doHandle(new DeleteOperationCommand(distributorId, operationId)))
                .verifyComplete();

        verify(operationsApi).deleteDistributorOperation(distributorId, operationId, null);
    }

    @Test
    void activateOperation_shouldCallApiWithIdempotencyKey() {
        var dto = new DistributorOperationDTO();
        when(operationsApi.activateDistributorOperation(eq(distributorId), eq(operationId), eq(userId), argThat(s -> s != null && !s.isEmpty())))
                .thenReturn(Mono.just(dto));

        var cmd = new ActivateOperationCommand(distributorId, operationId, userId);

        StepVerifier.create(activateHandler.doHandle(cmd))
                .expectNext(dto)
                .verifyComplete();

        verify(operationsApi).activateDistributorOperation(eq(distributorId), eq(operationId), eq(userId), argThat(s -> s != null && !s.isEmpty()));
    }

    @Test
    void deactivateOperation_shouldCallApiWithIdempotencyKey() {
        var dto = new DistributorOperationDTO();
        when(operationsApi.deactivateDistributorOperation(eq(distributorId), eq(operationId), eq(userId), argThat(s -> s != null && !s.isEmpty())))
                .thenReturn(Mono.just(dto));

        var cmd = new DeactivateOperationCommand(distributorId, operationId, userId);

        StepVerifier.create(deactivateHandler.doHandle(cmd))
                .expectNext(dto)
                .verifyComplete();

        verify(operationsApi).deactivateDistributorOperation(eq(distributorId), eq(operationId), eq(userId), argThat(s -> s != null && !s.isEmpty()));
    }

    @Test
    void canOperate_shouldCallApiWithNullIdempotencyKey() {
        when(operationsApi.canDistributorOperateInLocation(distributorId, operationId, locationId, null))
                .thenReturn(Mono.just(true));

        StepVerifier.create(canOperateHandler.doHandle(new CanOperateQuery(distributorId, operationId, locationId)))
                .expectNext(true)
                .verifyComplete();

        verify(operationsApi).canDistributorOperateInLocation(distributorId, operationId, locationId, null);
    }
}
