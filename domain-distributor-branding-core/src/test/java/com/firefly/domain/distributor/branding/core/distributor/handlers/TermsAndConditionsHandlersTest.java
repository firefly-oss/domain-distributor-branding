package com.firefly.domain.distributor.branding.core.distributor.handlers;

import com.firefly.core.distributor.sdk.api.DistributorTermsAndConditionsApi;
import com.firefly.core.distributor.sdk.model.DistributorTermsAndConditionsDTO;
import com.firefly.domain.distributor.branding.core.distributor.commands.*;
import com.firefly.domain.distributor.branding.core.distributor.queries.*;
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
 * Unit tests for terms and conditions lifecycle command and query handlers.
 */
@ExtendWith(MockitoExtension.class)
class TermsAndConditionsHandlersTest {

    @Mock
    private DistributorTermsAndConditionsApi termsApi;

    private ListTermsAndConditionsHandler listHandler;
    private GetActiveTermsAndConditionsHandler getActiveHandler;
    private GetLatestTermsAndConditionsHandler getLatestHandler;
    private CreateTermsAndConditionsHandler createHandler;
    private GetTermsAndConditionsDetailHandler getDetailHandler;
    private SignTermsAndConditionsHandler signHandler;
    private ActivateTermsAndConditionsHandler activateHandler;
    private DeactivateTermsAndConditionsHandler deactivateHandler;
    private HasActiveSignedTermsHandler hasActiveSignedHandler;

    private final UUID distributorId = UUID.randomUUID();
    private final UUID termsId = UUID.randomUUID();
    private final UUID userId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        listHandler = new ListTermsAndConditionsHandler(termsApi);
        getActiveHandler = new GetActiveTermsAndConditionsHandler(termsApi);
        getLatestHandler = new GetLatestTermsAndConditionsHandler(termsApi);
        createHandler = new CreateTermsAndConditionsHandler(termsApi);
        getDetailHandler = new GetTermsAndConditionsDetailHandler(termsApi);
        signHandler = new SignTermsAndConditionsHandler(termsApi);
        activateHandler = new ActivateTermsAndConditionsHandler(termsApi);
        deactivateHandler = new DeactivateTermsAndConditionsHandler(termsApi);
        hasActiveSignedHandler = new HasActiveSignedTermsHandler(termsApi);
    }

    @Test
    void listTermsAndConditions_shouldCallApi() {
        var dto = new DistributorTermsAndConditionsDTO();
        when(termsApi.getTermsAndConditionsByDistributorId(distributorId))
                .thenReturn(Mono.just(dto));

        StepVerifier.create(listHandler.doHandle(new ListTermsAndConditionsQuery(distributorId)))
                .expectNext(dto)
                .verifyComplete();

        verify(termsApi).getTermsAndConditionsByDistributorId(distributorId);
    }

    @Test
    void getActiveTermsAndConditions_shouldCallApi() {
        var dto = new DistributorTermsAndConditionsDTO();
        when(termsApi.getActiveTermsAndConditionsByDistributorId(distributorId))
                .thenReturn(Mono.just(dto));

        StepVerifier.create(getActiveHandler.doHandle(new GetActiveTermsAndConditionsQuery(distributorId)))
                .expectNext(dto)
                .verifyComplete();

        verify(termsApi).getActiveTermsAndConditionsByDistributorId(distributorId);
    }

    @Test
    void getLatestTermsAndConditions_shouldCallApi() {
        var dto = new DistributorTermsAndConditionsDTO();
        when(termsApi.getLatestTermsAndConditions(distributorId))
                .thenReturn(Mono.just(dto));

        StepVerifier.create(getLatestHandler.doHandle(new GetLatestTermsAndConditionsQuery(distributorId)))
                .expectNext(dto)
                .verifyComplete();

        verify(termsApi).getLatestTermsAndConditions(distributorId);
    }

    @Test
    void createTermsAndConditions_shouldCallApi() {
        var dto = new DistributorTermsAndConditionsDTO(termsId, null, null, null, null);

        when(termsApi.createDistributorTermsAndConditions(eq(distributorId), any(CreateTermsAndConditionsCommand.class)))
                .thenReturn(Mono.just(dto));

        var cmd = new CreateTermsAndConditionsCommand();
        cmd.withDistributorId(distributorId);

        StepVerifier.create(createHandler.doHandle(cmd))
                .expectNext(termsId)
                .verifyComplete();

        verify(termsApi).createDistributorTermsAndConditions(eq(distributorId), any());
    }

    @Test
    void getTermsAndConditionsDetail_shouldCallApi() {
        var dto = new DistributorTermsAndConditionsDTO();
        when(termsApi.getDistributorTermsAndConditionsById(distributorId, termsId))
                .thenReturn(Mono.just(dto));

        StepVerifier.create(getDetailHandler.doHandle(new GetTermsAndConditionsDetailQuery(distributorId, termsId)))
                .expectNext(dto)
                .verifyComplete();

        verify(termsApi).getDistributorTermsAndConditionsById(distributorId, termsId);
    }

    @Test
    void signTermsAndConditions_shouldCallApi() {
        var dto = new DistributorTermsAndConditionsDTO();
        when(termsApi.signTermsAndConditions(distributorId, termsId, userId))
                .thenReturn(Mono.just(dto));

        var cmd = new SignTermsAndConditionsCommand(distributorId, termsId, userId);

        StepVerifier.create(signHandler.doHandle(cmd))
                .expectNext(dto)
                .verifyComplete();

        verify(termsApi).signTermsAndConditions(distributorId, termsId, userId);
    }

    @Test
    void activateTermsAndConditions_shouldCallApi() {
        var dto = new DistributorTermsAndConditionsDTO();
        when(termsApi.activateTermsAndConditions(distributorId, termsId, userId))
                .thenReturn(Mono.just(dto));

        var cmd = new ActivateTermsAndConditionsCommand(distributorId, termsId, userId);

        StepVerifier.create(activateHandler.doHandle(cmd))
                .expectNext(dto)
                .verifyComplete();

        verify(termsApi).activateTermsAndConditions(distributorId, termsId, userId);
    }

    @Test
    void deactivateTermsAndConditions_shouldCallApi() {
        var dto = new DistributorTermsAndConditionsDTO();
        when(termsApi.deactivateTermsAndConditions(distributorId, termsId, userId))
                .thenReturn(Mono.just(dto));

        var cmd = new DeactivateTermsAndConditionsCommand(distributorId, termsId, userId);

        StepVerifier.create(deactivateHandler.doHandle(cmd))
                .expectNext(dto)
                .verifyComplete();

        verify(termsApi).deactivateTermsAndConditions(distributorId, termsId, userId);
    }

    @Test
    void hasActiveSignedTerms_shouldCallApi() {
        when(termsApi.hasActiveSignedTerms(distributorId))
                .thenReturn(Mono.just(true));

        StepVerifier.create(hasActiveSignedHandler.doHandle(new HasActiveSignedTermsQuery(distributorId)))
                .expectNext(true)
                .verifyComplete();

        verify(termsApi).hasActiveSignedTerms(distributorId);
    }
}
