package com.firefly.domain.distributor.branding.web.controller;

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

@WebFluxTest(AgentAgencyController.class)
class AgentAgencyControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private DistributorNetworkService distributorNetworkService;

    private static final UUID DISTRIBUTOR_ID = UUID.randomUUID();
    private static final UUID AGENT_AGENCY_ID = UUID.randomUUID();

    @Test
    void listAgentAgencies_returnsOk() {
        when(distributorNetworkService.listAgentAgencies(DISTRIBUTOR_ID))
                .thenReturn(Mono.empty());

        webTestClient.get()
                .uri("/api/v1/distributors/{distributorId}/agent-agencies", DISTRIBUTOR_ID)
                .exchange()
                .expectStatus().isOk();

        verify(distributorNetworkService).listAgentAgencies(DISTRIBUTOR_ID);
    }

    @Test
    void assignAgentAgency_returnsCreated() {
        when(distributorNetworkService.assignAgentAgency(any()))
                .thenReturn(Mono.just(AGENT_AGENCY_ID));

        webTestClient.post()
                .uri("/api/v1/distributors/{distributorId}/agent-agencies", DISTRIBUTOR_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{}")
                .exchange()
                .expectStatus().isCreated();

        verify(distributorNetworkService).assignAgentAgency(any());
    }

    @Test
    void removeAgentAgency_returnsNoContent() {
        when(distributorNetworkService.removeAgentAgency(eq(DISTRIBUTOR_ID), eq(AGENT_AGENCY_ID)))
                .thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/api/v1/distributors/{distributorId}/agent-agencies/{agentAgencyId}", DISTRIBUTOR_ID, AGENT_AGENCY_ID)
                .exchange()
                .expectStatus().isNoContent();

        verify(distributorNetworkService).removeAgentAgency(DISTRIBUTOR_ID, AGENT_AGENCY_ID);
    }
}
