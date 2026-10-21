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

import io.fireflysoftware.core.distributor.sdk.api.DistributorConfigurationsApi;
import io.fireflysoftware.core.distributor.sdk.model.DistributorConfigurationDTO;
import io.fireflysoftware.core.distributor.sdk.model.FilterRequestDistributorConfigurationDTO;
import io.fireflysoftware.core.distributor.sdk.model.PaginationResponse;
import io.fireflysoftware.domain.distributor.branding.core.distributor.commands.CreateConfigurationCommand;
import io.fireflysoftware.domain.distributor.branding.core.distributor.commands.DeleteConfigurationCommand;
import io.fireflysoftware.domain.distributor.branding.core.distributor.commands.UpdateConfigurationCommand;
import io.fireflysoftware.domain.distributor.branding.core.distributor.queries.GetConfigurationQuery;
import io.fireflysoftware.domain.distributor.branding.core.distributor.queries.ListConfigurationsQuery;
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
 * Unit tests for configuration command and query handlers.
 */
@ExtendWith(MockitoExtension.class)
class ConfigurationHandlersTest {

    @Mock
    private DistributorConfigurationsApi configurationsApi;

    private CreateConfigurationHandler createHandler;
    private GetConfigurationHandler getHandler;
    private ListConfigurationsHandler listHandler;
    private UpdateConfigurationHandler updateHandler;
    private DeleteConfigurationHandler deleteHandler;

    private final UUID distributorId = UUID.randomUUID();
    private final UUID configurationId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        createHandler = new CreateConfigurationHandler(configurationsApi);
        getHandler = new GetConfigurationHandler(configurationsApi);
        listHandler = new ListConfigurationsHandler(configurationsApi);
        updateHandler = new UpdateConfigurationHandler(configurationsApi);
        deleteHandler = new DeleteConfigurationHandler(configurationsApi);
    }

    @Test
    void createConfiguration_shouldCallApi() {
        var dto = new DistributorConfigurationDTO(configurationId, null, null, null, null);

        when(configurationsApi.create2(eq(distributorId), any(CreateConfigurationCommand.class), anyString()))
                .thenReturn(Mono.just(dto));

        var cmd = new CreateConfigurationCommand();
        cmd.withDistributorId(distributorId);

        StepVerifier.create(createHandler.doHandle(cmd))
                .expectNext(configurationId)
                .verifyComplete();

        verify(configurationsApi).create2(eq(distributorId), any(), anyString());
    }

    @Test
    void getConfiguration_shouldCallApi() {
        var dto = new DistributorConfigurationDTO();
        when(configurationsApi.getById2(eq(distributorId), eq(configurationId), anyString()))
                .thenReturn(Mono.just(dto));

        StepVerifier.create(getHandler.doHandle(new GetConfigurationQuery(distributorId, configurationId)))
                .expectNext(dto)
                .verifyComplete();

        verify(configurationsApi).getById2(eq(distributorId), eq(configurationId), anyString());
    }

    @Test
    void listConfigurations_shouldCallFilterApi() {
        var response = new PaginationResponse();

        when(configurationsApi.filter2(eq(distributorId), any(FilterRequestDistributorConfigurationDTO.class), anyString()))
                .thenReturn(Mono.just(response));

        StepVerifier.create(listHandler.doHandle(new ListConfigurationsQuery(distributorId)))
                .expectNext(response)
                .verifyComplete();

        verify(configurationsApi).filter2(eq(distributorId), any(), anyString());
    }

    @Test
    void updateConfiguration_shouldCallApi() {
        var dto = new DistributorConfigurationDTO(configurationId, null, null, null, null);

        when(configurationsApi.update2(eq(distributorId), eq(configurationId), any(UpdateConfigurationCommand.class), anyString()))
                .thenReturn(Mono.just(dto));

        var cmd = new UpdateConfigurationCommand();
        cmd.withDistributorId(distributorId);
        cmd.withId(configurationId);

        StepVerifier.create(updateHandler.doHandle(cmd))
                .expectNext(configurationId)
                .verifyComplete();

        verify(configurationsApi).update2(eq(distributorId), eq(configurationId), any(), anyString());
    }

    @Test
    void deleteConfiguration_shouldCallApi() {
        when(configurationsApi.delete2(eq(distributorId), eq(configurationId), anyString()))
                .thenReturn(Mono.empty());

        StepVerifier.create(deleteHandler.doHandle(new DeleteConfigurationCommand(distributorId, configurationId)))
                .verifyComplete();

        verify(configurationsApi).delete2(eq(distributorId), eq(configurationId), anyString());
    }
}
