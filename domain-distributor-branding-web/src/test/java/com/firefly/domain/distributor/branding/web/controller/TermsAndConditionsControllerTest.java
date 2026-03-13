package com.firefly.domain.distributor.branding.web.controller;

import com.firefly.core.distributor.sdk.model.DistributorTermsAndConditionsDTO;
import com.firefly.domain.distributor.branding.core.distributor.services.DistributorTermsService;
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

@WebFluxTest(TermsAndConditionsController.class)
class TermsAndConditionsControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private DistributorTermsService distributorTermsService;

    private static final UUID DISTRIBUTOR_ID = UUID.randomUUID();
    private static final UUID TC_ID = UUID.randomUUID();
    private static final UUID USER_ID = UUID.randomUUID();

    private DistributorTermsAndConditionsDTO sampleDto() {
        return new DistributorTermsAndConditionsDTO();
    }

    @Test
    void listTermsAndConditions_returnsOk() {
        when(distributorTermsService.listTermsAndConditions(DISTRIBUTOR_ID))
                .thenReturn(Mono.just(sampleDto()));

        webTestClient.get()
                .uri("/api/v1/distributors/{distributorId}/terms-and-conditions", DISTRIBUTOR_ID)
                .exchange()
                .expectStatus().isOk();

        verify(distributorTermsService).listTermsAndConditions(DISTRIBUTOR_ID);
    }

    @Test
    void getActiveTermsAndConditions_returnsOk() {
        when(distributorTermsService.getActiveTermsAndConditions(DISTRIBUTOR_ID))
                .thenReturn(Mono.just(sampleDto()));

        webTestClient.get()
                .uri("/api/v1/distributors/{distributorId}/terms-and-conditions/active", DISTRIBUTOR_ID)
                .exchange()
                .expectStatus().isOk();

        verify(distributorTermsService).getActiveTermsAndConditions(DISTRIBUTOR_ID);
    }

    @Test
    void getLatestTermsAndConditions_returnsOk() {
        when(distributorTermsService.getLatestTermsAndConditions(DISTRIBUTOR_ID))
                .thenReturn(Mono.just(sampleDto()));

        webTestClient.get()
                .uri("/api/v1/distributors/{distributorId}/terms-and-conditions/latest", DISTRIBUTOR_ID)
                .exchange()
                .expectStatus().isOk();

        verify(distributorTermsService).getLatestTermsAndConditions(DISTRIBUTOR_ID);
    }

    @Test
    void hasActiveSignedTerms_returnsOk() {
        when(distributorTermsService.hasActiveSignedTerms(DISTRIBUTOR_ID))
                .thenReturn(Mono.just(true));

        webTestClient.get()
                .uri("/api/v1/distributors/{distributorId}/terms-and-conditions/has-active-signed", DISTRIBUTOR_ID)
                .exchange()
                .expectStatus().isOk();

        verify(distributorTermsService).hasActiveSignedTerms(DISTRIBUTOR_ID);
    }

    @Test
    void createTermsAndConditions_returnsCreated() {
        when(distributorTermsService.createTermsAndConditions(any()))
                .thenReturn(Mono.just(TC_ID));

        webTestClient.post()
                .uri("/api/v1/distributors/{distributorId}/terms-and-conditions", DISTRIBUTOR_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{}")
                .exchange()
                .expectStatus().isCreated();

        verify(distributorTermsService).createTermsAndConditions(any());
    }

    @Test
    void getTermsAndConditionsDetail_returnsOk() {
        when(distributorTermsService.getTermsAndConditionsDetail(DISTRIBUTOR_ID, TC_ID))
                .thenReturn(Mono.just(sampleDto()));

        webTestClient.get()
                .uri("/api/v1/distributors/{distributorId}/terms-and-conditions/{tcId}", DISTRIBUTOR_ID, TC_ID)
                .exchange()
                .expectStatus().isOk();

        verify(distributorTermsService).getTermsAndConditionsDetail(DISTRIBUTOR_ID, TC_ID);
    }

    @Test
    void deleteTermsAndConditions_returnsNoContent() {
        when(distributorTermsService.deleteTermsAndConditions(eq(DISTRIBUTOR_ID), eq(TC_ID)))
                .thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/api/v1/distributors/{distributorId}/terms-and-conditions/{tcId}", DISTRIBUTOR_ID, TC_ID)
                .exchange()
                .expectStatus().isNoContent();

        verify(distributorTermsService).deleteTermsAndConditions(DISTRIBUTOR_ID, TC_ID);
    }

    @Test
    void signTermsAndConditions_returnsOk() {
        when(distributorTermsService.signTermsAndConditions(any()))
                .thenReturn(Mono.just(sampleDto()));

        webTestClient.patch()
                .uri("/api/v1/distributors/{distributorId}/terms-and-conditions/{tcId}/sign?signedBy={signedBy}",
                        DISTRIBUTOR_ID, TC_ID, USER_ID)
                .exchange()
                .expectStatus().isOk();

        verify(distributorTermsService).signTermsAndConditions(any());
    }

    @Test
    void activateTermsAndConditions_returnsOk() {
        when(distributorTermsService.activateTermsAndConditions(any()))
                .thenReturn(Mono.just(sampleDto()));

        webTestClient.patch()
                .uri("/api/v1/distributors/{distributorId}/terms-and-conditions/{tcId}/activate?activatedBy={activatedBy}",
                        DISTRIBUTOR_ID, TC_ID, USER_ID)
                .exchange()
                .expectStatus().isOk();

        verify(distributorTermsService).activateTermsAndConditions(any());
    }

    @Test
    void deactivateTermsAndConditions_returnsOk() {
        when(distributorTermsService.deactivateTermsAndConditions(any()))
                .thenReturn(Mono.just(sampleDto()));

        webTestClient.patch()
                .uri("/api/v1/distributors/{distributorId}/terms-and-conditions/{tcId}/deactivate?deactivatedBy={deactivatedBy}",
                        DISTRIBUTOR_ID, TC_ID, USER_ID)
                .exchange()
                .expectStatus().isOk();

        verify(distributorTermsService).deactivateTermsAndConditions(any());
    }
}
