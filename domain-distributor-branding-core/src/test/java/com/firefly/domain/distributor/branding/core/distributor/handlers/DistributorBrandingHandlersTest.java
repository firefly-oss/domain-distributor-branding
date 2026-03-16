package com.firefly.domain.distributor.branding.core.distributor.handlers;

import com.firefly.core.distributor.sdk.api.DistributorBrandingApi;
import com.firefly.core.distributor.sdk.model.DistributorBrandingDTO;
import com.firefly.domain.distributor.branding.core.distributor.queries.GetDistributorBrandingQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for distributor branding query handler.
 */
@ExtendWith(MockitoExtension.class)
class DistributorBrandingHandlersTest {

    @Mock
    private DistributorBrandingApi distributorBrandingApi;

    private GetDistributorBrandingQueryHandler getHandler;

    private final UUID distributorId = UUID.randomUUID();
    private final UUID brandingId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        getHandler = new GetDistributorBrandingQueryHandler(distributorBrandingApi);
    }

    @Test
    void getDistributorBranding_shouldCallApi() {
        var dto = new DistributorBrandingDTO();
        when(distributorBrandingApi.getDistributorBrandingById(eq(distributorId), eq(brandingId), anyString()))
                .thenReturn(Mono.just(dto));

        var query = GetDistributorBrandingQuery.builder()
                .distributorId(distributorId)
                .brandingId(brandingId)
                .build();

        StepVerifier.create(getHandler.doHandle(query))
                .expectNext(dto)
                .verifyComplete();

        verify(distributorBrandingApi).getDistributorBrandingById(eq(distributorId), eq(brandingId), anyString());
    }
}
