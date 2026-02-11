package com.firefly.domain.distributor.branding.core.distributor.handlers;

import com.firefly.common.cqrs.annotations.CommandHandlerComponent;
import com.firefly.common.cqrs.command.CommandHandler;
import com.firefly.core.distributor.sdk.api.DistributorTermsAndConditionsApi;
import com.firefly.domain.distributor.branding.core.distributor.commands.RemoveTermsAndConditionsCommand;
import reactor.core.publisher.Mono;

@CommandHandlerComponent
public class RemoveTermsAndConditionsHandler extends CommandHandler<RemoveTermsAndConditionsCommand, Void> {

    private final DistributorTermsAndConditionsApi distributorTermsAndConditionsApi;

    public RemoveTermsAndConditionsHandler(DistributorTermsAndConditionsApi distributorTermsAndConditionsApi) {
        this.distributorTermsAndConditionsApi = distributorTermsAndConditionsApi;
    }

    @Override
    protected Mono<Void> doHandle(RemoveTermsAndConditionsCommand cmd) {
        return distributorTermsAndConditionsApi.deleteDistributorTermsAndConditions(cmd.distributorId(), cmd.termsAndConditionsId());
    }
}

