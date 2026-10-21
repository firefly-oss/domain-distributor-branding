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

import io.fireflysoftware.core.distributor.sdk.api.DistributorAgentAgencyApi;
import io.fireflysoftware.core.distributor.sdk.model.DistributorAgentAgencyDTO;
import io.fireflysoftware.core.distributor.sdk.model.FilterRequestDistributorAgentAgencyDTO;
import io.fireflysoftware.domain.distributor.branding.core.distributor.commands.AssignAgentAgencyCommand;
import io.fireflysoftware.domain.distributor.branding.core.distributor.commands.RemoveAgentAgencyCommand;
import io.fireflysoftware.domain.distributor.branding.core.distributor.queries.ListAgentAgenciesQuery;
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
 * Unit tests for agent-agency command and query handlers.
 */
@ExtendWith(MockitoExtension.class)
class AgentAgencyHandlersTest {

    @Mock
    private DistributorAgentAgencyApi agentAgencyApi;

    private AssignAgentAgencyHandler assignHandler;
    private ListAgentAgenciesHandler listHandler;
    private RemoveAgentAgencyHandler removeHandler;

    private final UUID distributorId = UUID.randomUUID();
    private final UUID agentAgencyId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        assignHandler = new AssignAgentAgencyHandler(agentAgencyApi);
        listHandler = new ListAgentAgenciesHandler(agentAgencyApi);
        removeHandler = new RemoveAgentAgencyHandler(agentAgencyApi);
    }

    @Test
    void assignAgentAgency_shouldCallApi() {
        var dto = new DistributorAgentAgencyDTO(agentAgencyId, null, null, null, null);

        when(agentAgencyApi.create5(eq(distributorId), any(AssignAgentAgencyCommand.class), anyString()))
                .thenReturn(Mono.just(dto));

        var cmd = new AssignAgentAgencyCommand();
        cmd.withDistributorId(distributorId);

        StepVerifier.create(assignHandler.doHandle(cmd))
                .expectNext(agentAgencyId)
                .verifyComplete();

        verify(agentAgencyApi).create5(eq(distributorId), any(), anyString());
    }

    @Test
    void listAgentAgencies_shouldCallFilterApi() {
        when(agentAgencyApi.filter5(eq(distributorId), any(FilterRequestDistributorAgentAgencyDTO.class), anyString()))
                .thenReturn(Mono.empty());

        StepVerifier.create(listHandler.doHandle(new ListAgentAgenciesQuery(distributorId)))
                .verifyComplete();

        verify(agentAgencyApi).filter5(eq(distributorId), any(), anyString());
    }

    @Test
    void removeAgentAgency_shouldCallApi() {
        when(agentAgencyApi.delete5(eq(distributorId), eq(agentAgencyId), anyString()))
                .thenReturn(Mono.empty());

        StepVerifier.create(removeHandler.doHandle(new RemoveAgentAgencyCommand(distributorId, agentAgencyId)))
                .verifyComplete();

        verify(agentAgencyApi).delete5(eq(distributorId), eq(agentAgencyId), anyString());
    }
}
