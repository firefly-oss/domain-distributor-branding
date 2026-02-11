package com.firefly.domain.distributor.branding.core.distributor.handlers;

import com.firefly.common.cqrs.annotations.CommandHandlerComponent;
import com.firefly.common.cqrs.command.CommandHandler;
import com.firefly.core.distributor.sdk.api.DistributorTermsAndConditionsApi;
import com.firefly.domain.distributor.branding.core.distributor.commands.RegisterTermsAndConditionsCommand;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.UUID;

@CommandHandlerComponent
public class RegisterTermsAndConditionsHandler extends CommandHandler<RegisterTermsAndConditionsCommand, UUID> {

    private final DistributorTermsAndConditionsApi distributorTermsAndConditionsApi;

    public RegisterTermsAndConditionsHandler(DistributorTermsAndConditionsApi distributorTermsAndConditionsApi) {
        this.distributorTermsAndConditionsApi = distributorTermsAndConditionsApi;
    }

    @Override
    protected Mono<UUID> doHandle(RegisterTermsAndConditionsCommand cmd) {
        return distributorTermsAndConditionsApi.createDistributorTermsAndConditions(cmd.getDistributorId(), cmd, UUID.randomUUID().toString())
                .mapNotNull(distributorTermsAndConditionsDTO ->
                        Objects.requireNonNull(Objects.requireNonNull(distributorTermsAndConditionsDTO)).getId());
    }
}

