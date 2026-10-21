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


package io.fireflysoftware.domain.distributor.branding.web.controller;

import io.fireflysoftware.core.distributor.sdk.model.DistributorAuthorizedTerritoryDTO;
import io.fireflysoftware.core.distributor.sdk.model.PaginationResponse;
import io.fireflysoftware.domain.distributor.branding.core.distributor.services.DistributorNetworkService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webflux.test.autoconfigure.WebFluxTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
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

    @MockitoBean
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
