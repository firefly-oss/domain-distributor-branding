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

import io.fireflysoftware.core.distributor.sdk.api.DistributorAuthorizedTerritoriesApi;
import io.fireflysoftware.core.distributor.sdk.model.DistributorAuthorizedTerritoryDTO;
import io.fireflysoftware.core.distributor.sdk.model.FilterRequestDistributorAuthorizedTerritoryDTO;
import io.fireflysoftware.core.distributor.sdk.model.PaginationResponse;
import io.fireflysoftware.domain.distributor.branding.core.distributor.commands.CreateTerritoryCommand;
import io.fireflysoftware.domain.distributor.branding.core.distributor.commands.DeleteTerritoryCommand;
import io.fireflysoftware.domain.distributor.branding.core.distributor.commands.UpdateTerritoryCommand;
import io.fireflysoftware.domain.distributor.branding.core.distributor.queries.GetTerritoryQuery;
import io.fireflysoftware.domain.distributor.branding.core.distributor.queries.ListTerritoriesQuery;
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
 * Unit tests for territory command and query handlers.
 */
@ExtendWith(MockitoExtension.class)
class TerritoryHandlersTest {

    @Mock
    private DistributorAuthorizedTerritoriesApi territoriesApi;

    private CreateTerritoryHandler createHandler;
    private GetTerritoryHandler getHandler;
    private ListTerritoriesHandler listHandler;
    private UpdateTerritoryHandler updateHandler;
    private DeleteTerritoryHandler deleteHandler;

    private final UUID distributorId = UUID.randomUUID();
    private final UUID territoryId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        createHandler = new CreateTerritoryHandler(territoriesApi);
        getHandler = new GetTerritoryHandler(territoriesApi);
        listHandler = new ListTerritoriesHandler(territoriesApi);
        updateHandler = new UpdateTerritoryHandler(territoriesApi);
        deleteHandler = new DeleteTerritoryHandler(territoriesApi);
    }

    @Test
    void createTerritory_shouldCallApi() {
        var dto = new DistributorAuthorizedTerritoryDTO();
        dto.setId(territoryId);

        when(territoriesApi.create3(eq(distributorId), any(CreateTerritoryCommand.class), anyString()))
                .thenReturn(Mono.just(dto));

        var cmd = new CreateTerritoryCommand();
        cmd.withDistributorId(distributorId);

        StepVerifier.create(createHandler.doHandle(cmd))
                .expectNext(territoryId)
                .verifyComplete();

        verify(territoriesApi).create3(eq(distributorId), any(), anyString());
    }

    @Test
    void getTerritory_shouldCallApi() {
        var dto = new DistributorAuthorizedTerritoryDTO();
        dto.setId(territoryId);

        when(territoriesApi.getById3(eq(distributorId), eq(territoryId), anyString()))
                .thenReturn(Mono.just(dto));

        var query = new GetTerritoryQuery(distributorId, territoryId);

        StepVerifier.create(getHandler.doHandle(query))
                .expectNext(dto)
                .verifyComplete();

        verify(territoriesApi).getById3(eq(distributorId), eq(territoryId), anyString());
    }

    @Test
    void listTerritories_shouldCallFilterApi() {
        var response = new PaginationResponse();

        when(territoriesApi.filter3(eq(distributorId), any(FilterRequestDistributorAuthorizedTerritoryDTO.class), anyString()))
                .thenReturn(Mono.just(response));

        var query = new ListTerritoriesQuery(distributorId);

        StepVerifier.create(listHandler.doHandle(query))
                .expectNext(response)
                .verifyComplete();

        verify(territoriesApi).filter3(eq(distributorId), any(), anyString());
    }

    @Test
    void updateTerritory_shouldCallApi() {
        var dto = new DistributorAuthorizedTerritoryDTO();
        dto.setId(territoryId);

        when(territoriesApi.update3(eq(distributorId), eq(territoryId), any(UpdateTerritoryCommand.class), anyString()))
                .thenReturn(Mono.just(dto));

        var cmd = new UpdateTerritoryCommand();
        cmd.withDistributorId(distributorId);
        cmd.withId(territoryId);

        StepVerifier.create(updateHandler.doHandle(cmd))
                .expectNext(territoryId)
                .verifyComplete();

        verify(territoriesApi).update3(eq(distributorId), eq(territoryId), any(), anyString());
    }

    @Test
    void deleteTerritory_shouldCallApi() {
        when(territoriesApi.delete3(eq(distributorId), eq(territoryId), anyString()))
                .thenReturn(Mono.empty());

        var cmd = new DeleteTerritoryCommand(distributorId, territoryId);

        StepVerifier.create(deleteHandler.doHandle(cmd))
                .verifyComplete();

        verify(territoriesApi).delete3(eq(distributorId), eq(territoryId), anyString());
    }
}
