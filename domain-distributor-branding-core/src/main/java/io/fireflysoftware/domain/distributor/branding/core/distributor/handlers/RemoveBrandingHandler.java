package io.fireflysoftware.domain.distributor.branding.core.distributor.handlers;

import org.fireflyframework.cqrs.annotations.CommandHandlerComponent;
import org.fireflyframework.cqrs.command.CommandHandler;
import io.fireflysoftware.core.distributor.sdk.api.DistributorBrandingApi;
import io.fireflysoftware.domain.distributor.branding.core.distributor.commands.RemoveBrandingCommand;
import reactor.core.publisher.Mono;
import java.util.UUID;

@CommandHandlerComponent
public class RemoveBrandingHandler extends CommandHandler<RemoveBrandingCommand, Void> {

    private final DistributorBrandingApi distributorBrandingApi;

    public RemoveBrandingHandler(DistributorBrandingApi distributorBrandingApi) {
        this.distributorBrandingApi = distributorBrandingApi;
    }

    @Override
    protected Mono<Void> doHandle(RemoveBrandingCommand cmd) {
        return distributorBrandingApi.deleteDistributorBranding(cmd.distributorId(), cmd.brandingId(), UUID.randomUUID().toString());
    }
}

