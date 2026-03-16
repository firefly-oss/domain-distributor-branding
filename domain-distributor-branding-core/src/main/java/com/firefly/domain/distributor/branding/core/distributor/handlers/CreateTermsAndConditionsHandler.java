package com.firefly.domain.distributor.branding.core.distributor.handlers;

import com.firefly.core.distributor.sdk.api.DistributorTermsAndConditionsApi;
import com.firefly.domain.distributor.branding.core.distributor.commands.CreateTermsAndConditionsCommand;
import org.fireflyframework.cqrs.annotations.CommandHandlerComponent;
import org.fireflyframework.cqrs.command.CommandHandler;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.UUID;

/**
 * Handler that creates new terms and conditions for a distributor.
 */
@CommandHandlerComponent
public class CreateTermsAndConditionsHandler extends CommandHandler<CreateTermsAndConditionsCommand, UUID> {

    private final DistributorTermsAndConditionsApi termsApi;

    public CreateTermsAndConditionsHandler(DistributorTermsAndConditionsApi termsApi) {
        this.termsApi = termsApi;
    }

    @Override
    protected Mono<UUID> doHandle(CreateTermsAndConditionsCommand cmd) {
        return termsApi.createDistributorTermsAndConditions(cmd.getDistributorId(), cmd, UUID.randomUUID().toString())
                .mapNotNull(dto -> Objects.requireNonNull(Objects.requireNonNull(dto)).getId());
    }
}
