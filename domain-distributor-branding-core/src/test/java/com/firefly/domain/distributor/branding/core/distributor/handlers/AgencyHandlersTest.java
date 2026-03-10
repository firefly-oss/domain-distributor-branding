package com.firefly.domain.distributor.branding.core.distributor.handlers;

import com.firefly.core.distributor.sdk.api.DistributorAgenciesApi;
import com.firefly.core.distributor.sdk.model.DistributorAgencyDTO;
import com.firefly.core.distributor.sdk.model.FilterRequestDistributorAgencyDTO;
import com.firefly.core.distributor.sdk.model.PaginationResponse;
import com.firefly.domain.distributor.branding.core.distributor.commands.CreateAgencyCommand;
import com.firefly.domain.distributor.branding.core.distributor.commands.DeleteAgencyCommand;
import com.firefly.domain.distributor.branding.core.distributor.commands.UpdateAgencyCommand;
import com.firefly.domain.distributor.branding.core.distributor.queries.GetAgencyQuery;
import com.firefly.domain.distributor.branding.core.distributor.queries.ListAgenciesQuery;
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
 * Unit tests for agency command and query handlers.
 */
@ExtendWith(MockitoExtension.class)
class AgencyHandlersTest {

    @Mock
    private DistributorAgenciesApi agenciesApi;

    private CreateAgencyHandler createHandler;
    private GetAgencyHandler getHandler;
    private ListAgenciesHandler listHandler;
    private UpdateAgencyHandler updateHandler;
    private DeleteAgencyHandler deleteHandler;

    private final UUID distributorId = UUID.randomUUID();
    private final UUID agencyId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        createHandler = new CreateAgencyHandler(agenciesApi);
        getHandler = new GetAgencyHandler(agenciesApi);
        listHandler = new ListAgenciesHandler(agenciesApi);
        updateHandler = new UpdateAgencyHandler(agenciesApi);
        deleteHandler = new DeleteAgencyHandler(agenciesApi);
    }

    @Test
    void createAgency_shouldCallApiWithIdempotencyKey() {
        var dto = new DistributorAgencyDTO(agencyId, null, null, null, null);

        when(agenciesApi.create6(eq(distributorId), any(CreateAgencyCommand.class), argThat(s -> s != null && !s.isEmpty())))
                .thenReturn(Mono.just(dto));

        var cmd = new CreateAgencyCommand();
        cmd.withDistributorId(distributorId);

        StepVerifier.create(createHandler.doHandle(cmd))
                .expectNext(agencyId)
                .verifyComplete();

        verify(agenciesApi).create6(eq(distributorId), any(), argThat(s -> s != null && !s.isEmpty()));
    }

    @Test
    void getAgency_shouldCallApiWithNullIdempotencyKey() {
        var dto = new DistributorAgencyDTO(agencyId, null, null, null, null);

        when(agenciesApi.getById6(distributorId, agencyId, null))
                .thenReturn(Mono.just(dto));

        StepVerifier.create(getHandler.doHandle(new GetAgencyQuery(distributorId, agencyId)))
                .expectNext(dto)
                .verifyComplete();

        verify(agenciesApi).getById6(distributorId, agencyId, null);
    }

    @Test
    void listAgencies_shouldCallFilterApiWithNullIdempotencyKey() {
        var response = new PaginationResponse();

        when(agenciesApi.filter6(eq(distributorId), any(FilterRequestDistributorAgencyDTO.class), isNull()))
                .thenReturn(Mono.just(response));

        StepVerifier.create(listHandler.doHandle(new ListAgenciesQuery(distributorId)))
                .expectNext(response)
                .verifyComplete();

        verify(agenciesApi).filter6(eq(distributorId), any(), isNull());
    }

    @Test
    void updateAgency_shouldCallApiWithIdempotencyKey() {
        var dto = new DistributorAgencyDTO(agencyId, null, null, null, null);

        when(agenciesApi.update6(eq(distributorId), eq(agencyId), any(UpdateAgencyCommand.class), argThat(s -> s != null && !s.isEmpty())))
                .thenReturn(Mono.just(dto));

        var cmd = new UpdateAgencyCommand();
        cmd.withDistributorId(distributorId);
        cmd.withId(agencyId);

        StepVerifier.create(updateHandler.doHandle(cmd))
                .expectNext(agencyId)
                .verifyComplete();

        verify(agenciesApi).update6(eq(distributorId), eq(agencyId), any(), argThat(s -> s != null && !s.isEmpty()));
    }

    @Test
    void deleteAgency_shouldCallApiWithNullIdempotencyKey() {
        when(agenciesApi.delete6(distributorId, agencyId, null))
                .thenReturn(Mono.empty());

        StepVerifier.create(deleteHandler.doHandle(new DeleteAgencyCommand(distributorId, agencyId)))
                .verifyComplete();

        verify(agenciesApi).delete6(distributorId, agencyId, null);
    }
}
