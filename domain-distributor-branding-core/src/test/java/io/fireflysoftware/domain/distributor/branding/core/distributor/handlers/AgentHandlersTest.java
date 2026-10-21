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

import io.fireflysoftware.core.distributor.sdk.api.DistributorAgentsApi;
import io.fireflysoftware.core.distributor.sdk.model.DistributorAgentDTO;
import io.fireflysoftware.core.distributor.sdk.model.FilterRequestDistributorAgentDTO;
import io.fireflysoftware.core.distributor.sdk.model.PaginationResponse;
import io.fireflysoftware.domain.distributor.branding.core.distributor.commands.CreateAgentCommand;
import io.fireflysoftware.domain.distributor.branding.core.distributor.commands.DeleteAgentCommand;
import io.fireflysoftware.domain.distributor.branding.core.distributor.commands.UpdateAgentCommand;
import io.fireflysoftware.domain.distributor.branding.core.distributor.queries.GetAgentQuery;
import io.fireflysoftware.domain.distributor.branding.core.distributor.queries.ListAgentsQuery;
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
    void createAgent_shouldCallApi() {
        var dto = new DistributorAgentDTO(agentId, null, null, null, null);

        when(agentsApi.create4(eq(distributorId), any(CreateAgentCommand.class), anyString()))
                .thenReturn(Mono.just(dto));

        var cmd = new CreateAgentCommand();
        cmd.withDistributorId(distributorId);

        StepVerifier.create(createHandler.doHandle(cmd))
                .expectNext(agentId)
                .verifyComplete();

        verify(agentsApi).create4(eq(distributorId), any(), anyString());
    }

    @Test
    void getAgent_shouldCallApi() {
        var dto = new DistributorAgentDTO(agentId, null, null, null, null);

        when(agentsApi.getById4(eq(distributorId), eq(agentId), anyString()))
                .thenReturn(Mono.just(dto));

        StepVerifier.create(getHandler.doHandle(new GetAgentQuery(distributorId, agentId)))
                .expectNext(dto)
                .verifyComplete();

        verify(agentsApi).getById4(eq(distributorId), eq(agentId), anyString());
    }

    @Test
    void listAgents_shouldCallFilterApi() {
        var response = new PaginationResponse();

        when(agentsApi.filter4(eq(distributorId), any(FilterRequestDistributorAgentDTO.class), anyString()))
                .thenReturn(Mono.just(response));

        StepVerifier.create(listHandler.doHandle(new ListAgentsQuery(distributorId)))
                .expectNext(response)
                .verifyComplete();

        verify(agentsApi).filter4(eq(distributorId), any(), anyString());
    }

    @Test
    void updateAgent_shouldCallApi() {
        var dto = new DistributorAgentDTO(agentId, null, null, null, null);

        when(agentsApi.update4(eq(distributorId), eq(agentId), any(UpdateAgentCommand.class), anyString()))
                .thenReturn(Mono.just(dto));

        var cmd = new UpdateAgentCommand();
        cmd.withDistributorId(distributorId);
        cmd.withId(agentId);

        StepVerifier.create(updateHandler.doHandle(cmd))
                .expectNext(agentId)
                .verifyComplete();

        verify(agentsApi).update4(eq(distributorId), eq(agentId), any(), anyString());
    }

    @Test
    void deleteAgent_shouldCallApi() {
        when(agentsApi.delete4(eq(distributorId), eq(agentId), anyString()))
                .thenReturn(Mono.empty());

        StepVerifier.create(deleteHandler.doHandle(new DeleteAgentCommand(distributorId, agentId)))
                .verifyComplete();

        verify(agentsApi).delete4(eq(distributorId), eq(agentId), anyString());
    }
}
