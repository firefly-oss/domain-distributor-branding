package com.firefly.domain.distributor.branding.core.distributor.handlers;

import com.firefly.core.distributor.sdk.api.DistributorTermsAndConditionsApi;
import com.firefly.core.distributor.sdk.model.DistributorTermsAndConditionsDTO;
import com.firefly.domain.distributor.branding.core.distributor.commands.SignTermsAndConditionsCommand;
import org.fireflyframework.cqrs.annotations.CommandHandlerComponent;
import org.fireflyframework.cqrs.command.CommandHandler;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Handler that signs terms and conditions for a distributor.
 */
@CommandHandlerComponent
public class SignTermsAndConditionsHandler extends CommandHandler<SignTermsAndConditionsCommand, DistributorTermsAndConditionsDTO> {

    private final DistributorTermsAndConditionsApi termsApi;

    public SignTermsAndConditionsHandler(DistributorTermsAndConditionsApi termsApi) {
        this.termsApi = termsApi;
    }

    @Override
    protected Mono<DistributorTermsAndConditionsDTO> doHandle(SignTermsAndConditionsCommand cmd) {
        return termsApi.signTermsAndConditions(cmd.distributorId(), cmd.termsAndConditionsId(), cmd.signedBy(), UUID.randomUUID().toString());
    }
}
