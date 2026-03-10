package com.firefly.domain.distributor.branding.core.distributor.handlers;

import com.firefly.core.distributor.sdk.api.DistributorAuthorizedTerritoriesApi;
import com.firefly.core.distributor.sdk.model.DistributorAuthorizedTerritoryDTO;
import com.firefly.core.distributor.sdk.model.FilterRequestDistributorAuthorizedTerritoryDTO;
import com.firefly.core.distributor.sdk.model.PaginationResponse;
import com.firefly.domain.distributor.branding.core.distributor.commands.CreateTerritoryCommand;
import com.firefly.domain.distributor.branding.core.distributor.commands.DeleteTerritoryCommand;
import com.firefly.domain.distributor.branding.core.distributor.commands.UpdateTerritoryCommand;
import com.firefly.domain.distributor.branding.core.distributor.queries.GetTerritoryQuery;
import com.firefly.domain.distributor.branding.core.distributor.queries.ListTerritoriesQuery;
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
    void createTerritory_shouldCallApiWithIdempotencyKey() {
        var dto = new DistributorAuthorizedTerritoryDTO();
        dto.setId(territoryId);

        when(territoriesApi.create3(eq(distributorId), any(CreateTerritoryCommand.class), argThat(s -> s != null && !s.isEmpty())))
                .thenReturn(Mono.just(dto));

        var cmd = new CreateTerritoryCommand();
        cmd.withDistributorId(distributorId);

        StepVerifier.create(createHandler.doHandle(cmd))
                .expectNext(territoryId)
                .verifyComplete();

        verify(territoriesApi).create3(eq(distributorId), any(), argThat(s -> s != null && !s.isEmpty()));
    }

    @Test
    void getTerritory_shouldCallApiWithNullIdempotencyKey() {
        var dto = new DistributorAuthorizedTerritoryDTO();
        dto.setId(territoryId);

        when(territoriesApi.getById3(distributorId, territoryId, null))
                .thenReturn(Mono.just(dto));

        var query = new GetTerritoryQuery(distributorId, territoryId);

        StepVerifier.create(getHandler.doHandle(query))
                .expectNext(dto)
                .verifyComplete();

        verify(territoriesApi).getById3(distributorId, territoryId, null);
    }

    @Test
    void listTerritories_shouldCallFilterApiWithNullIdempotencyKey() {
        var response = new PaginationResponse();

        when(territoriesApi.filter3(eq(distributorId), any(FilterRequestDistributorAuthorizedTerritoryDTO.class), isNull()))
                .thenReturn(Mono.just(response));

        var query = new ListTerritoriesQuery(distributorId);

        StepVerifier.create(listHandler.doHandle(query))
                .expectNext(response)
                .verifyComplete();

        verify(territoriesApi).filter3(eq(distributorId), any(), isNull());
    }

    @Test
    void updateTerritory_shouldCallApiWithIdempotencyKey() {
        var dto = new DistributorAuthorizedTerritoryDTO();
        dto.setId(territoryId);

        when(territoriesApi.update3(eq(distributorId), eq(territoryId), any(UpdateTerritoryCommand.class), argThat(s -> s != null && !s.isEmpty())))
                .thenReturn(Mono.just(dto));

        var cmd = new UpdateTerritoryCommand();
        cmd.withDistributorId(distributorId);
        cmd.withId(territoryId);

        StepVerifier.create(updateHandler.doHandle(cmd))
                .expectNext(territoryId)
                .verifyComplete();

        verify(territoriesApi).update3(eq(distributorId), eq(territoryId), any(), argThat(s -> s != null && !s.isEmpty()));
    }

    @Test
    void deleteTerritory_shouldCallApiWithNullIdempotencyKey() {
        when(territoriesApi.delete3(distributorId, territoryId, null))
                .thenReturn(Mono.empty());

        var cmd = new DeleteTerritoryCommand(distributorId, territoryId);

        StepVerifier.create(deleteHandler.doHandle(cmd))
                .verifyComplete();

        verify(territoriesApi).delete3(distributorId, territoryId, null);
    }
}
