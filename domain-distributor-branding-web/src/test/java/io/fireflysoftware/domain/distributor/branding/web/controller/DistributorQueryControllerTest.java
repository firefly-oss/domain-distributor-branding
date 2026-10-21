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

import io.fireflysoftware.core.distributor.sdk.api.DistributorApi;
import io.fireflysoftware.core.distributor.sdk.api.DistributorBrandingApi;
import io.fireflysoftware.core.distributor.sdk.model.DistributorBrandingDTO;
import io.fireflysoftware.core.distributor.sdk.model.DistributorDTO;
import io.fireflysoftware.domain.distributor.branding.core.distributor.queries.GetDistributorBrandingQuery;
import io.fireflysoftware.domain.distributor.branding.core.distributor.queries.GetDistributorProfileQuery;
import org.fireflyframework.cqrs.query.QueryBus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webflux.test.autoconfigure.WebFluxTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebFluxTest(DistributorQueryController.class)
class DistributorQueryControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private QueryBus queryBus;

    @MockitoBean
    private DistributorApi distributorApi;

    @MockitoBean
    private DistributorBrandingApi distributorBrandingApi;

    private static final UUID DISTRIBUTOR_ID = UUID.randomUUID();
    private static final UUID BRANDING_ID = UUID.randomUUID();

    @Test
    void getDistributorProfile_returnsOk() {
        var dto = new DistributorDTO();
        when(queryBus.query(any(GetDistributorProfileQuery.class)))
                .thenReturn(Mono.just(dto));

        webTestClient.get()
                .uri("/api/v1/distributors/{distributorId}/profile", DISTRIBUTOR_ID)
                .exchange()
                .expectStatus().isOk();

        verify(queryBus).query(any(GetDistributorProfileQuery.class));
    }

    @Test
    void getDistributorBranding_returnsOk() {
        var dto = new DistributorBrandingDTO();
        when(queryBus.query(any(GetDistributorBrandingQuery.class)))
                .thenReturn(Mono.just(dto));

        webTestClient.get()
                .uri("/api/v1/distributors/{distributorId}/branding/{brandingId}", DISTRIBUTOR_ID, BRANDING_ID)
                .exchange()
                .expectStatus().isOk();

        verify(queryBus).query(any(GetDistributorBrandingQuery.class));
    }
}
