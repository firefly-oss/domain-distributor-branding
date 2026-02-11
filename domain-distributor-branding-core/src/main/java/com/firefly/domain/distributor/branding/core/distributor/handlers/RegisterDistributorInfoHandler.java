package com.firefly.domain.distributor.branding.core.distributor.handlers;

import com.firefly.common.cqrs.annotations.CommandHandlerComponent;
import com.firefly.common.cqrs.command.CommandHandler;
import com.firefly.core.distributor.sdk.api.DistributorApi;
import com.firefly.domain.distributor.branding.core.distributor.commands.RegisterDistributorInfoCommand;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.UUID;

@CommandHandlerComponent
public class RegisterDistributorInfoHandler extends CommandHandler<RegisterDistributorInfoCommand, UUID> {

    private final DistributorApi distributorApi;

    public RegisterDistributorInfoHandler(DistributorApi distributorApi) {
        this.distributorApi = distributorApi;
    }

    @Override
    protected Mono<UUID> doHandle(RegisterDistributorInfoCommand cmd) {
        return distributorApi.createDistributor(cmd, UUID.randomUUID().toString())
                .mapNotNull(applicationCollateralDTO ->
                        Objects.requireNonNull(Objects.requireNonNull(applicationCollateralDTO)).getId());
    }
}

