package com.firefly.domain.distributor.branding.core.distributor.handlers;

import com.firefly.core.distributor.sdk.api.DistributorTermsAndConditionsApi;
import com.firefly.core.distributor.sdk.model.DistributorTermsAndConditionsDTO;
import com.firefly.domain.distributor.branding.core.distributor.commands.ActivateTermsAndConditionsCommand;
import org.fireflyframework.cqrs.annotations.CommandHandlerComponent;
import org.fireflyframework.cqrs.command.CommandHandler;
import reactor.core.publisher.Mono;


/**
 * Handler that activates terms and conditions for a distributor.
 */
@CommandHandlerComponent
public class ActivateTermsAndConditionsHandler extends CommandHandler<ActivateTermsAndConditionsCommand, DistributorTermsAndConditionsDTO> {

    private final DistributorTermsAndConditionsApi termsApi;

    public ActivateTermsAndConditionsHandler(DistributorTermsAndConditionsApi termsApi) {
        this.termsApi = termsApi;
    }

    @Override
    protected Mono<DistributorTermsAndConditionsDTO> doHandle(ActivateTermsAndConditionsCommand cmd) {
        return termsApi.activateTermsAndConditions(cmd.distributorId(), cmd.termsAndConditionsId(), cmd.activatedBy());
    }
}
