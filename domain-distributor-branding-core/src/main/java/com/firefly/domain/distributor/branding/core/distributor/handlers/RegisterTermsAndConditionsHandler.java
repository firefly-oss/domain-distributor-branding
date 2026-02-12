package com.firefly.domain.distributor.branding.core.distributor.handlers;

import org.fireflyframework.cqrs.annotations.CommandHandlerComponent;
import org.fireflyframework.cqrs.command.CommandHandler;
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

