package com.firefly.domain.distributor.branding.core.distributor.handlers;

import com.firefly.core.distributor.sdk.api.DistributorApi;
import com.firefly.core.distributor.sdk.model.DistributorDTO;
import com.firefly.domain.distributor.branding.core.distributor.queries.GetDistributorProfileQuery;
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
 * Unit tests for distributor profile query handler.
 */
@ExtendWith(MockitoExtension.class)
class DistributorProfileHandlersTest {

    @Mock
    private DistributorApi distributorApi;

    private GetDistributorProfileHandler getHandler;

    private final UUID distributorId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        getHandler = new GetDistributorProfileHandler(distributorApi);
    }

    @Test
    void getDistributorProfile_shouldCallApi() {
        var dto = new DistributorDTO();
        when(distributorApi.getDistributorById(eq(distributorId), anyString()))
                .thenReturn(Mono.just(dto));

        var query = GetDistributorProfileQuery.builder()
                .distributorId(distributorId)
                .build();

        StepVerifier.create(getHandler.doHandle(query))
                .expectNext(dto)
                .verifyComplete();

        verify(distributorApi).getDistributorById(eq(distributorId), anyString());
    }
}
