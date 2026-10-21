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

import io.fireflysoftware.core.distributor.sdk.model.DistributorConfigurationDTO;
import io.fireflysoftware.core.distributor.sdk.model.PaginationResponse;
import io.fireflysoftware.domain.distributor.branding.core.distributor.services.DistributorConfigService;
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

@WebFluxTest(ConfigurationController.class)
class ConfigurationControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private DistributorConfigService distributorConfigService;

    private static final UUID DISTRIBUTOR_ID = UUID.randomUUID();
    private static final UUID CONFIG_ID = UUID.randomUUID();

    @Test
    void listConfigurations_returnsOk() {
        when(distributorConfigService.listConfigurations(DISTRIBUTOR_ID))
                .thenReturn(Mono.just(new PaginationResponse()));

        webTestClient.get()
                .uri("/api/v1/distributors/{distributorId}/configurations", DISTRIBUTOR_ID)
                .exchange()
                .expectStatus().isOk();

        verify(distributorConfigService).listConfigurations(DISTRIBUTOR_ID);
    }

    @Test
    void createConfiguration_returnsCreated() {
        when(distributorConfigService.createConfiguration(any()))
                .thenReturn(Mono.just(CONFIG_ID));

        webTestClient.post()
                .uri("/api/v1/distributors/{distributorId}/configurations", DISTRIBUTOR_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{}")
                .exchange()
                .expectStatus().isCreated();

        verify(distributorConfigService).createConfiguration(any());
    }

    @Test
    void getConfiguration_returnsOk() {
        when(distributorConfigService.getConfiguration(DISTRIBUTOR_ID, CONFIG_ID))
                .thenReturn(Mono.just(new DistributorConfigurationDTO()));

        webTestClient.get()
                .uri("/api/v1/distributors/{distributorId}/configurations/{configurationId}", DISTRIBUTOR_ID, CONFIG_ID)
                .exchange()
                .expectStatus().isOk();

        verify(distributorConfigService).getConfiguration(DISTRIBUTOR_ID, CONFIG_ID);
    }

    @Test
    void updateConfiguration_returnsOk() {
        when(distributorConfigService.updateConfiguration(any()))
                .thenReturn(Mono.just(CONFIG_ID));

        webTestClient.put()
                .uri("/api/v1/distributors/{distributorId}/configurations/{configurationId}", DISTRIBUTOR_ID, CONFIG_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{}")
                .exchange()
                .expectStatus().isOk();

        verify(distributorConfigService).updateConfiguration(any());
    }

    @Test
    void deleteConfiguration_returnsNoContent() {
        when(distributorConfigService.deleteConfiguration(eq(DISTRIBUTOR_ID), eq(CONFIG_ID)))
                .thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/api/v1/distributors/{distributorId}/configurations/{configurationId}", DISTRIBUTOR_ID, CONFIG_ID)
                .exchange()
                .expectStatus().isNoContent();

        verify(distributorConfigService).deleteConfiguration(DISTRIBUTOR_ID, CONFIG_ID);
    }
}
