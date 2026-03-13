package com.firefly.domain.distributor.branding.web.controller;

import com.firefly.core.distributor.sdk.model.DistributorAgentDTO;
import com.firefly.core.distributor.sdk.model.PaginationResponse;
import com.firefly.domain.distributor.branding.core.distributor.services.DistributorNetworkService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@WebFluxTest(AgentController.class)
class AgentControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private DistributorNetworkService distributorNetworkService;

    private static final UUID DISTRIBUTOR_ID = UUID.randomUUID();
    private static final UUID AGENT_ID = UUID.randomUUID();

    @Test
    void listAgents_returnsOk() {
        when(distributorNetworkService.listAgents(DISTRIBUTOR_ID))
                .thenReturn(Mono.just(new PaginationResponse()));

        webTestClient.get()
                .uri("/api/v1/distributors/{distributorId}/agents", DISTRIBUTOR_ID)
                .exchange()
                .expectStatus().isOk();

        verify(distributorNetworkService).listAgents(DISTRIBUTOR_ID);
    }

    @Test
    void createAgent_returnsCreated() {
        when(distributorNetworkService.createAgent(any()))
                .thenReturn(Mono.just(AGENT_ID));

        webTestClient.post()
                .uri("/api/v1/distributors/{distributorId}/agents", DISTRIBUTOR_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{}")
                .exchange()
                .expectStatus().isCreated();

        verify(distributorNetworkService).createAgent(any());
    }

    @Test
    void getAgent_returnsOk() {
        when(distributorNetworkService.getAgent(DISTRIBUTOR_ID, AGENT_ID))
                .thenReturn(Mono.just(new DistributorAgentDTO()));

        webTestClient.get()
                .uri("/api/v1/distributors/{distributorId}/agents/{agentId}", DISTRIBUTOR_ID, AGENT_ID)
                .exchange()
                .expectStatus().isOk();

        verify(distributorNetworkService).getAgent(DISTRIBUTOR_ID, AGENT_ID);
    }

    @Test
    void updateAgent_returnsOk() {
        when(distributorNetworkService.updateAgent(any()))
                .thenReturn(Mono.just(AGENT_ID));

        webTestClient.put()
                .uri("/api/v1/distributors/{distributorId}/agents/{agentId}", DISTRIBUTOR_ID, AGENT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{}")
                .exchange()
                .expectStatus().isOk();

        verify(distributorNetworkService).updateAgent(any());
    }

    @Test
    void deleteAgent_returnsNoContent() {
        when(distributorNetworkService.deleteAgent(eq(DISTRIBUTOR_ID), eq(AGENT_ID)))
                .thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/api/v1/distributors/{distributorId}/agents/{agentId}", DISTRIBUTOR_ID, AGENT_ID)
                .exchange()
                .expectStatus().isNoContent();

        verify(distributorNetworkService).deleteAgent(DISTRIBUTOR_ID, AGENT_ID);
    }
}
