package com.firefly.domain.distributor.branding.web.controller;

import com.firefly.core.distributor.sdk.model.DistributorOperationDTO;
import com.firefly.domain.distributor.branding.core.distributor.services.DistributorOperationsService;
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

@WebFluxTest(OperationController.class)
class OperationControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private DistributorOperationsService distributorOperationsService;

    private static final UUID DISTRIBUTOR_ID = UUID.randomUUID();
    private static final UUID OPERATION_ID = UUID.randomUUID();
    private static final UUID USER_ID = UUID.randomUUID();
    private static final UUID LOCATION_ID = UUID.randomUUID();

    private DistributorOperationDTO sampleDto() {
        return new DistributorOperationDTO();
    }

    @Test
    void listOperations_returnsOk() {
        when(distributorOperationsService.listOperations(DISTRIBUTOR_ID))
                .thenReturn(Mono.just(sampleDto()));

        webTestClient.get()
                .uri("/api/v1/distributors/{distributorId}/operations", DISTRIBUTOR_ID)
                .exchange()
                .expectStatus().isOk();

        verify(distributorOperationsService).listOperations(DISTRIBUTOR_ID);
    }

    @Test
    void createOperation_returnsCreated() {
        when(distributorOperationsService.createOperation(any()))
                .thenReturn(Mono.just(OPERATION_ID));

        webTestClient.post()
                .uri("/api/v1/distributors/{distributorId}/operations", DISTRIBUTOR_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{}")
                .exchange()
                .expectStatus().isCreated();

        verify(distributorOperationsService).createOperation(any());
    }

    @Test
    void getOperation_returnsOk() {
        when(distributorOperationsService.getOperation(DISTRIBUTOR_ID, OPERATION_ID))
                .thenReturn(Mono.just(sampleDto()));

        webTestClient.get()
                .uri("/api/v1/distributors/{distributorId}/operations/{operationId}", DISTRIBUTOR_ID, OPERATION_ID)
                .exchange()
                .expectStatus().isOk();

        verify(distributorOperationsService).getOperation(DISTRIBUTOR_ID, OPERATION_ID);
    }

    @Test
    void updateOperation_returnsOk() {
        when(distributorOperationsService.updateOperation(any()))
                .thenReturn(Mono.just(OPERATION_ID));

        webTestClient.put()
                .uri("/api/v1/distributors/{distributorId}/operations/{operationId}", DISTRIBUTOR_ID, OPERATION_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{}")
                .exchange()
                .expectStatus().isOk();

        verify(distributorOperationsService).updateOperation(any());
    }

    @Test
    void deleteOperation_returnsNoContent() {
        when(distributorOperationsService.deleteOperation(eq(DISTRIBUTOR_ID), eq(OPERATION_ID)))
                .thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/api/v1/distributors/{distributorId}/operations/{operationId}", DISTRIBUTOR_ID, OPERATION_ID)
                .exchange()
                .expectStatus().isNoContent();

        verify(distributorOperationsService).deleteOperation(DISTRIBUTOR_ID, OPERATION_ID);
    }

    @Test
    void activateOperation_returnsOk() {
        when(distributorOperationsService.activateOperation(any()))
                .thenReturn(Mono.just(sampleDto()));

        webTestClient.patch()
                .uri("/api/v1/distributors/{distributorId}/operations/{operationId}/activate?activatedBy={activatedBy}",
                        DISTRIBUTOR_ID, OPERATION_ID, USER_ID)
                .exchange()
                .expectStatus().isOk();

        verify(distributorOperationsService).activateOperation(any());
    }

    @Test
    void deactivateOperation_returnsOk() {
        when(distributorOperationsService.deactivateOperation(any()))
                .thenReturn(Mono.just(sampleDto()));

        webTestClient.patch()
                .uri("/api/v1/distributors/{distributorId}/operations/{operationId}/deactivate?deactivatedBy={deactivatedBy}",
                        DISTRIBUTOR_ID, OPERATION_ID, USER_ID)
                .exchange()
                .expectStatus().isOk();

        verify(distributorOperationsService).deactivateOperation(any());
    }

    @Test
    void canOperate_returnsOk() {
        when(distributorOperationsService.canOperate(eq(DISTRIBUTOR_ID), eq(OPERATION_ID), eq(LOCATION_ID)))
                .thenReturn(Mono.just(true));

        webTestClient.get()
                .uri("/api/v1/distributors/{distributorId}/operations/can-operate?operationId={operationId}&locationId={locationId}",
                        DISTRIBUTOR_ID, OPERATION_ID, LOCATION_ID)
                .exchange()
                .expectStatus().isOk();

        verify(distributorOperationsService).canOperate(DISTRIBUTOR_ID, OPERATION_ID, LOCATION_ID);
    }
}
