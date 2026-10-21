/*
 * Copyright 2025 Firefly Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package io.fireflysoftware.domain.distributor.branding.core.distributor.handlers;

import io.fireflysoftware.core.distributor.sdk.api.DistributorOperationsApi;
import io.fireflysoftware.core.distributor.sdk.model.DistributorOperationDTO;
import io.fireflysoftware.domain.distributor.branding.core.distributor.commands.*;
import io.fireflysoftware.domain.distributor.branding.core.distributor.queries.*;
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

        when(operationsApi.createDistributorOperation(eq(distributorId), any(CreateOperationCommand.class), anyString()))
                .thenReturn(Mono.just(dto));

        var cmd = new CreateOperationCommand();
        cmd.withDistributorId(distributorId);

        StepVerifier.create(createHandler.doHandle(cmd))
                .expectNext(operationId)
                .verifyComplete();

        verify(operationsApi).createDistributorOperation(eq(distributorId), any(), anyString());
    }

    @Test
    void getOperation_shouldCallApi() {
        var dto = new DistributorOperationDTO();
        when(operationsApi.getDistributorOperationById(eq(distributorId), eq(operationId), anyString()))
                .thenReturn(Mono.just(dto));

        StepVerifier.create(getHandler.doHandle(new GetOperationQuery(distributorId, operationId)))
                .expectNext(dto)
                .verifyComplete();

        verify(operationsApi).getDistributorOperationById(eq(distributorId), eq(operationId), anyString());
    }

    @Test
    void listOperations_shouldCallApi() {
        var dto = new DistributorOperationDTO();
        when(operationsApi.getOperationsByDistributorId(eq(distributorId), anyString()))
                .thenReturn(Mono.just(dto));

        StepVerifier.create(listHandler.doHandle(new ListOperationsQuery(distributorId)))
                .expectNext(dto)
                .verifyComplete();

        verify(operationsApi).getOperationsByDistributorId(eq(distributorId), anyString());
    }

    @Test
    void updateOperation_shouldCallApi() {
        var dto = new DistributorOperationDTO(operationId, null, null, null, null);

        when(operationsApi.updateDistributorOperation(eq(distributorId), eq(operationId), any(UpdateOperationCommand.class), anyString()))
                .thenReturn(Mono.just(dto));

        var cmd = new UpdateOperationCommand();
        cmd.withDistributorId(distributorId);
        cmd.withId(operationId);

        StepVerifier.create(updateHandler.doHandle(cmd))
                .expectNext(operationId)
                .verifyComplete();

        verify(operationsApi).updateDistributorOperation(eq(distributorId), eq(operationId), any(), anyString());
    }

    @Test
    void deleteOperation_shouldCallApi() {
        when(operationsApi.deleteDistributorOperation(eq(distributorId), eq(operationId), anyString()))
                .thenReturn(Mono.empty());

        StepVerifier.create(deleteHandler.doHandle(new DeleteOperationCommand(distributorId, operationId)))
                .verifyComplete();

        verify(operationsApi).deleteDistributorOperation(eq(distributorId), eq(operationId), anyString());
    }

    @Test
    void activateOperation_shouldCallApi() {
        var dto = new DistributorOperationDTO();
        when(operationsApi.activateDistributorOperation(eq(distributorId), eq(operationId), eq(userId), anyString()))
                .thenReturn(Mono.just(dto));

        var cmd = new ActivateOperationCommand(distributorId, operationId, userId);

        StepVerifier.create(activateHandler.doHandle(cmd))
                .expectNext(dto)
                .verifyComplete();

        verify(operationsApi).activateDistributorOperation(eq(distributorId), eq(operationId), eq(userId), anyString());
    }

    @Test
    void deactivateOperation_shouldCallApi() {
        var dto = new DistributorOperationDTO();
        when(operationsApi.deactivateDistributorOperation(eq(distributorId), eq(operationId), eq(userId), anyString()))
                .thenReturn(Mono.just(dto));

        var cmd = new DeactivateOperationCommand(distributorId, operationId, userId);

        StepVerifier.create(deactivateHandler.doHandle(cmd))
                .expectNext(dto)
                .verifyComplete();

        verify(operationsApi).deactivateDistributorOperation(eq(distributorId), eq(operationId), eq(userId), anyString());
    }

    @Test
    void canOperate_shouldCallApi() {
        when(operationsApi.canDistributorOperateInLocation(eq(distributorId), eq(operationId), eq(locationId), anyString()))
                .thenReturn(Mono.just(true));

        StepVerifier.create(canOperateHandler.doHandle(new CanOperateQuery(distributorId, operationId, locationId)))
                .expectNext(true)
                .verifyComplete();

        verify(operationsApi).canDistributorOperateInLocation(eq(distributorId), eq(operationId), eq(locationId), anyString());
    }
}
