package com.firefly.domain.distributor.branding.core.distributor.handlers;

import com.firefly.core.distributor.sdk.api.DistributorAgentAgencyApi;
import com.firefly.core.distributor.sdk.model.DistributorAgentAgencyDTO;
import com.firefly.core.distributor.sdk.model.FilterRequestDistributorAgentAgencyDTO;
import com.firefly.domain.distributor.branding.core.distributor.commands.AssignAgentAgencyCommand;
import com.firefly.domain.distributor.branding.core.distributor.commands.RemoveAgentAgencyCommand;
import com.firefly.domain.distributor.branding.core.distributor.queries.ListAgentAgenciesQuery;
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

        when(agentAgencyApi.create5(eq(distributorId), any(AssignAgentAgencyCommand.class), isNull()))
                .thenReturn(Mono.just(dto));

        var cmd = new AssignAgentAgencyCommand();
        cmd.withDistributorId(distributorId);

        StepVerifier.create(assignHandler.doHandle(cmd))
                .expectNext(agentAgencyId)
                .verifyComplete();

        verify(agentAgencyApi).create5(eq(distributorId), any(), isNull());
    }

    @Test
    void listAgentAgencies_shouldCallFilterApi() {
        when(agentAgencyApi.filter5(eq(distributorId), any(FilterRequestDistributorAgentAgencyDTO.class), isNull()))
                .thenReturn(Mono.empty());

        StepVerifier.create(listHandler.doHandle(new ListAgentAgenciesQuery(distributorId)))
                .verifyComplete();

        verify(agentAgencyApi).filter5(eq(distributorId), any(), isNull());
    }

    @Test
    void removeAgentAgency_shouldCallApi() {
        when(agentAgencyApi.delete5(distributorId, agentAgencyId, null))
                .thenReturn(Mono.empty());

        StepVerifier.create(removeHandler.doHandle(new RemoveAgentAgencyCommand(distributorId, agentAgencyId)))
                .verifyComplete();

        verify(agentAgencyApi).delete5(distributorId, agentAgencyId, null);
    }
}
