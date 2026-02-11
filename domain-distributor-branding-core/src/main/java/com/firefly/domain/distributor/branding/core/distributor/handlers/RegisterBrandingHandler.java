package com.firefly.domain.distributor.branding.core.distributor.handlers;

import com.firefly.common.cqrs.annotations.CommandHandlerComponent;
import com.firefly.common.cqrs.command.CommandHandler;
import com.firefly.core.distributor.sdk.api.DistributorBrandingApi;
import com.firefly.domain.distributor.branding.core.distributor.commands.RegisterDistributorBrandingCommand;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.UUID;

@CommandHandlerComponent
public class RegisterBrandingHandler extends CommandHandler<RegisterDistributorBrandingCommand, UUID> {

    private final DistributorBrandingApi distributorBrandingApi;

    public RegisterBrandingHandler(DistributorBrandingApi distributorBrandingApi) {
        this.distributorBrandingApi = distributorBrandingApi;
    }

    @Override
    protected Mono<UUID> doHandle(RegisterDistributorBrandingCommand cmd) {
        return distributorBrandingApi.createDistributorBranding(cmd.getDistributorId(), cmd, UUID.randomUUID().toString())
                .mapNotNull(distributorBrandingDTO ->
                        Objects.requireNonNull(Objects.requireNonNull(distributorBrandingDTO)).getId());
    }
}

