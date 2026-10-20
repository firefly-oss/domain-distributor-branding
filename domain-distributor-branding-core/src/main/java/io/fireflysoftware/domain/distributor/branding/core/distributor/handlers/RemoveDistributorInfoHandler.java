package io.fireflysoftware.domain.distributor.branding.core.distributor.handlers;

import org.fireflyframework.cqrs.annotations.CommandHandlerComponent;
import org.fireflyframework.cqrs.command.CommandHandler;
import io.fireflysoftware.core.distributor.sdk.api.DistributorApi;
import io.fireflysoftware.domain.distributor.branding.core.distributor.commands.RemoveDistributorInfoCommand;
import reactor.core.publisher.Mono;
import java.util.UUID;

@CommandHandlerComponent
public class RemoveDistributorInfoHandler extends CommandHandler<RemoveDistributorInfoCommand, Void> {

    private final DistributorApi distributorApi;

    public RemoveDistributorInfoHandler(DistributorApi distributorApi) {
        this.distributorApi = distributorApi;
    }

    @Override
    protected Mono<Void> doHandle(RemoveDistributorInfoCommand cmd) {
        return distributorApi.deleteDistributor(cmd.distributorId(), UUID.randomUUID().toString());
    }
}

