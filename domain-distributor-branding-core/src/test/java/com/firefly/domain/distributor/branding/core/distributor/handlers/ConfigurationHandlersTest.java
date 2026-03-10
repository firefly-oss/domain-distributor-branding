package com.firefly.domain.distributor.branding.core.distributor.handlers;

import com.firefly.core.distributor.sdk.api.DistributorConfigurationsApi;
import com.firefly.core.distributor.sdk.model.DistributorConfigurationDTO;
import com.firefly.core.distributor.sdk.model.FilterRequestDistributorConfigurationDTO;
import com.firefly.core.distributor.sdk.model.PaginationResponse;
import com.firefly.domain.distributor.branding.core.distributor.commands.CreateConfigurationCommand;
import com.firefly.domain.distributor.branding.core.distributor.commands.DeleteConfigurationCommand;
import com.firefly.domain.distributor.branding.core.distributor.commands.UpdateConfigurationCommand;
import com.firefly.domain.distributor.branding.core.distributor.queries.GetConfigurationQuery;
import com.firefly.domain.distributor.branding.core.distributor.queries.ListConfigurationsQuery;
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
    void createConfiguration_shouldCallApiWithIdempotencyKey() {
        var dto = new DistributorConfigurationDTO(configurationId, null, null, null, null);

        when(configurationsApi.create2(eq(distributorId), any(CreateConfigurationCommand.class), argThat(s -> s != null && !s.isEmpty())))
                .thenReturn(Mono.just(dto));

        var cmd = new CreateConfigurationCommand();
        cmd.withDistributorId(distributorId);

        StepVerifier.create(createHandler.doHandle(cmd))
                .expectNext(configurationId)
                .verifyComplete();

        verify(configurationsApi).create2(eq(distributorId), any(), argThat(s -> s != null && !s.isEmpty()));
    }

    @Test
    void getConfiguration_shouldCallApiWithNullIdempotencyKey() {
        var dto = new DistributorConfigurationDTO();
        when(configurationsApi.getById2(distributorId, configurationId, null))
                .thenReturn(Mono.just(dto));

        StepVerifier.create(getHandler.doHandle(new GetConfigurationQuery(distributorId, configurationId)))
                .expectNext(dto)
                .verifyComplete();

        verify(configurationsApi).getById2(distributorId, configurationId, null);
    }

    @Test
    void listConfigurations_shouldCallFilterApiWithNullIdempotencyKey() {
        var response = new PaginationResponse();

        when(configurationsApi.filter2(eq(distributorId), any(FilterRequestDistributorConfigurationDTO.class), isNull()))
                .thenReturn(Mono.just(response));

        StepVerifier.create(listHandler.doHandle(new ListConfigurationsQuery(distributorId)))
                .expectNext(response)
                .verifyComplete();

        verify(configurationsApi).filter2(eq(distributorId), any(), isNull());
    }

    @Test
    void updateConfiguration_shouldCallApiWithIdempotencyKey() {
        var dto = new DistributorConfigurationDTO(configurationId, null, null, null, null);

        when(configurationsApi.update2(eq(distributorId), eq(configurationId), any(UpdateConfigurationCommand.class), argThat(s -> s != null && !s.isEmpty())))
                .thenReturn(Mono.just(dto));

        var cmd = new UpdateConfigurationCommand();
        cmd.withDistributorId(distributorId);
        cmd.withId(configurationId);

        StepVerifier.create(updateHandler.doHandle(cmd))
                .expectNext(configurationId)
                .verifyComplete();

        verify(configurationsApi).update2(eq(distributorId), eq(configurationId), any(), argThat(s -> s != null && !s.isEmpty()));
    }

    @Test
    void deleteConfiguration_shouldCallApiWithNullIdempotencyKey() {
        when(configurationsApi.delete2(distributorId, configurationId, null))
                .thenReturn(Mono.empty());

        StepVerifier.create(deleteHandler.doHandle(new DeleteConfigurationCommand(distributorId, configurationId)))
                .verifyComplete();

        verify(configurationsApi).delete2(distributorId, configurationId, null);
    }
}
