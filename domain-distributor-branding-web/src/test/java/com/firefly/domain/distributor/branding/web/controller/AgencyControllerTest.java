package com.firefly.domain.distributor.branding.web.controller;

import com.firefly.core.distributor.sdk.model.DistributorAgencyDTO;
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

@WebFluxTest(AgencyController.class)
class AgencyControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private DistributorNetworkService distributorNetworkService;

    private static final UUID DISTRIBUTOR_ID = UUID.randomUUID();
    private static final UUID AGENCY_ID = UUID.randomUUID();

    @Test
    void listAgencies_returnsOk() {
        when(distributorNetworkService.listAgencies(DISTRIBUTOR_ID))
                .thenReturn(Mono.just(new PaginationResponse()));

        webTestClient.get()
                .uri("/api/v1/distributors/{distributorId}/agencies", DISTRIBUTOR_ID)
                .exchange()
                .expectStatus().isOk();

        verify(distributorNetworkService).listAgencies(DISTRIBUTOR_ID);
    }

    @Test
    void createAgency_returnsCreated() {
        when(distributorNetworkService.createAgency(any()))
                .thenReturn(Mono.just(AGENCY_ID));

        webTestClient.post()
                .uri("/api/v1/distributors/{distributorId}/agencies", DISTRIBUTOR_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{}")
                .exchange()
                .expectStatus().isCreated();

        verify(distributorNetworkService).createAgency(any());
    }

    @Test
    void getAgency_returnsOk() {
        when(distributorNetworkService.getAgency(DISTRIBUTOR_ID, AGENCY_ID))
                .thenReturn(Mono.just(new DistributorAgencyDTO()));

        webTestClient.get()
                .uri("/api/v1/distributors/{distributorId}/agencies/{agencyId}", DISTRIBUTOR_ID, AGENCY_ID)
                .exchange()
                .expectStatus().isOk();

        verify(distributorNetworkService).getAgency(DISTRIBUTOR_ID, AGENCY_ID);
    }

    @Test
    void updateAgency_returnsOk() {
        when(distributorNetworkService.updateAgency(any()))
                .thenReturn(Mono.just(AGENCY_ID));

        webTestClient.put()
                .uri("/api/v1/distributors/{distributorId}/agencies/{agencyId}", DISTRIBUTOR_ID, AGENCY_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{}")
                .exchange()
                .expectStatus().isOk();

        verify(distributorNetworkService).updateAgency(any());
    }

    @Test
    void deleteAgency_returnsNoContent() {
        when(distributorNetworkService.deleteAgency(eq(DISTRIBUTOR_ID), eq(AGENCY_ID)))
                .thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/api/v1/distributors/{distributorId}/agencies/{agencyId}", DISTRIBUTOR_ID, AGENCY_ID)
                .exchange()
                .expectStatus().isNoContent();

        verify(distributorNetworkService).deleteAgency(DISTRIBUTOR_ID, AGENCY_ID);
    }
}
