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


package io.fireflysoftware.domain.distributor.branding.core.distributor.handlers;

import io.fireflysoftware.core.distributor.sdk.api.DistributorBrandingApi;
import io.fireflysoftware.core.distributor.sdk.model.DistributorBrandingDTO;
import io.fireflysoftware.domain.distributor.branding.core.distributor.queries.GetDistributorBrandingQuery;
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
