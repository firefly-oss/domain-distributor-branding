package com.firefly.domain.distributor.branding.core.distributor.handlers;

import com.firefly.core.distributor.sdk.api.DistributorAgentsApi;
import com.firefly.core.distributor.sdk.model.DistributorAgentDTO;
import com.firefly.core.distributor.sdk.model.FilterRequestDistributorAgentDTO;
import com.firefly.core.distributor.sdk.model.PaginationResponse;
import com.firefly.domain.distributor.branding.core.distributor.commands.CreateAgentCommand;
import com.firefly.domain.distributor.branding.core.distributor.commands.DeleteAgentCommand;
import com.firefly.domain.distributor.branding.core.distributor.commands.UpdateAgentCommand;
import com.firefly.domain.distributor.branding.core.distributor.queries.GetAgentQuery;
import com.firefly.domain.distributor.branding.core.distributor.queries.ListAgentsQuery;
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
 * Unit tests for agent command and query handlers.
 */
@ExtendWith(MockitoExtension.class)
class AgentHandlersTest {

    @Mock
    private DistributorAgentsApi agentsApi;

    private CreateAgentHandler createHandler;
    private GetAgentHandler getHandler;
    private ListAgentsHandler listHandler;
    private UpdateAgentHandler updateHandler;
    private DeleteAgentHandler deleteHandler;

    private final UUID distributorId = UUID.randomUUID();
    private final UUID agentId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        createHandler = new CreateAgentHandler(agentsApi);
        getHandler = new GetAgentHandler(agentsApi);
        listHandler = new ListAgentsHandler(agentsApi);
        updateHandler = new UpdateAgentHandler(agentsApi);
        deleteHandler = new DeleteAgentHandler(agentsApi);
    }

    @Test
    void createAgent_shouldCallApiWithIdempotencyKey() {
        var dto = new DistributorAgentDTO(agentId, null, null, null, null);

        when(agentsApi.create4(eq(distributorId), any(CreateAgentCommand.class), argThat(s -> s != null && !s.isEmpty())))
                .thenReturn(Mono.just(dto));

        var cmd = new CreateAgentCommand();
        cmd.withDistributorId(distributorId);

        StepVerifier.create(createHandler.doHandle(cmd))
                .expectNext(agentId)
                .verifyComplete();

        verify(agentsApi).create4(eq(distributorId), any(), argThat(s -> s != null && !s.isEmpty()));
    }

    @Test
    void getAgent_shouldCallApiWithNullIdempotencyKey() {
        var dto = new DistributorAgentDTO(agentId, null, null, null, null);

        when(agentsApi.getById4(distributorId, agentId, null))
                .thenReturn(Mono.just(dto));

        StepVerifier.create(getHandler.doHandle(new GetAgentQuery(distributorId, agentId)))
                .expectNext(dto)
                .verifyComplete();

        verify(agentsApi).getById4(distributorId, agentId, null);
    }

    @Test
    void listAgents_shouldCallFilterApiWithNullIdempotencyKey() {
        var response = new PaginationResponse();

        when(agentsApi.filter4(eq(distributorId), any(FilterRequestDistributorAgentDTO.class), isNull()))
                .thenReturn(Mono.just(response));

        StepVerifier.create(listHandler.doHandle(new ListAgentsQuery(distributorId)))
                .expectNext(response)
                .verifyComplete();

        verify(agentsApi).filter4(eq(distributorId), any(), isNull());
    }

    @Test
    void updateAgent_shouldCallApiWithIdempotencyKey() {
        var dto = new DistributorAgentDTO(agentId, null, null, null, null);

        when(agentsApi.update4(eq(distributorId), eq(agentId), any(UpdateAgentCommand.class), argThat(s -> s != null && !s.isEmpty())))
                .thenReturn(Mono.just(dto));

        var cmd = new UpdateAgentCommand();
        cmd.withDistributorId(distributorId);
        cmd.withId(agentId);

        StepVerifier.create(updateHandler.doHandle(cmd))
                .expectNext(agentId)
                .verifyComplete();

        verify(agentsApi).update4(eq(distributorId), eq(agentId), any(), argThat(s -> s != null && !s.isEmpty()));
    }

    @Test
    void deleteAgent_shouldCallApiWithNullIdempotencyKey() {
        when(agentsApi.delete4(distributorId, agentId, null))
                .thenReturn(Mono.empty());

        StepVerifier.create(deleteHandler.doHandle(new DeleteAgentCommand(distributorId, agentId)))
                .verifyComplete();

        verify(agentsApi).delete4(distributorId, agentId, null);
    }
}
