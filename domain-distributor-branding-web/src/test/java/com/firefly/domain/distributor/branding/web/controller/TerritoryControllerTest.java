package com.firefly.domain.distributor.branding.web.controller;

import com.firefly.core.distributor.sdk.model.DistributorAuthorizedTerritoryDTO;
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

@WebFluxTest(TerritoryController.class)
class TerritoryControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private DistributorNetworkService distributorNetworkService;

    private static final UUID DISTRIBUTOR_ID = UUID.randomUUID();
    private static final UUID TERRITORY_ID = UUID.randomUUID();

    @Test
    void listTerritories_returnsOk() {
        when(distributorNetworkService.listTerritories(DISTRIBUTOR_ID))
                .thenReturn(Mono.just(new PaginationResponse()));

        webTestClient.get()
                .uri("/api/v1/distributors/{distributorId}/territories", DISTRIBUTOR_ID)
                .exchange()
                .expectStatus().isOk();

        verify(distributorNetworkService).listTerritories(DISTRIBUTOR_ID);
    }

    @Test
    void createTerritory_returnsCreated() {
        when(distributorNetworkService.createTerritory(any()))
                .thenReturn(Mono.just(TERRITORY_ID));

        webTestClient.post()
                .uri("/api/v1/distributors/{distributorId}/territories", DISTRIBUTOR_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{}")
                .exchange()
                .expectStatus().isCreated();

        verify(distributorNetworkService).createTerritory(any());
    }

    @Test
    void getTerritory_returnsOk() {
        when(distributorNetworkService.getTerritory(DISTRIBUTOR_ID, TERRITORY_ID))
                .thenReturn(Mono.just(new DistributorAuthorizedTerritoryDTO()));

        webTestClient.get()
                .uri("/api/v1/distributors/{distributorId}/territories/{territoryId}", DISTRIBUTOR_ID, TERRITORY_ID)
                .exchange()
                .expectStatus().isOk();

        verify(distributorNetworkService).getTerritory(DISTRIBUTOR_ID, TERRITORY_ID);
    }

    @Test
    void updateTerritory_returnsOk() {
        when(distributorNetworkService.updateTerritory(any()))
                .thenReturn(Mono.just(TERRITORY_ID));

        webTestClient.put()
                .uri("/api/v1/distributors/{distributorId}/territories/{territoryId}", DISTRIBUTOR_ID, TERRITORY_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{}")
                .exchange()
                .expectStatus().isOk();

        verify(distributorNetworkService).updateTerritory(any());
    }

    @Test
    void deleteTerritory_returnsNoContent() {
        when(distributorNetworkService.deleteTerritory(eq(DISTRIBUTOR_ID), eq(TERRITORY_ID)))
                .thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/api/v1/distributors/{distributorId}/territories/{territoryId}", DISTRIBUTOR_ID, TERRITORY_ID)
                .exchange()
                .expectStatus().isNoContent();

        verify(distributorNetworkService).deleteTerritory(DISTRIBUTOR_ID, TERRITORY_ID);
    }
}
