package com.firefly.domain.distributor.branding.web.controller;

import com.firefly.core.distributor.sdk.model.DistributorBrandingDTO;
import com.firefly.core.distributor.sdk.model.DistributorDTO;
import com.firefly.domain.distributor.branding.core.distributor.queries.GetDistributorBrandingQuery;
import com.firefly.domain.distributor.branding.core.distributor.queries.GetDistributorProfileQuery;
import org.fireflyframework.cqrs.query.QueryBus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

    @MockBean
    private QueryBus queryBus;

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
