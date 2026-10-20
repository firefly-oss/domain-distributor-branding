package io.fireflysoftware.domain.distributor.branding.core.distributor.handlers;

import org.fireflyframework.cqrs.annotations.CommandHandlerComponent;
import org.fireflyframework.cqrs.command.CommandHandler;
import io.fireflysoftware.core.distributor.sdk.api.DistributorTermsAndConditionsApi;
import io.fireflysoftware.domain.distributor.branding.core.distributor.commands.RemoveTermsAndConditionsCommand;
import reactor.core.publisher.Mono;
import java.util.UUID;

@CommandHandlerComponent
public class RemoveTermsAndConditionsHandler extends CommandHandler<RemoveTermsAndConditionsCommand, Void> {

    private final DistributorTermsAndConditionsApi distributorTermsAndConditionsApi;

    public RemoveTermsAndConditionsHandler(DistributorTermsAndConditionsApi distributorTermsAndConditionsApi) {
        this.distributorTermsAndConditionsApi = distributorTermsAndConditionsApi;
    }

    @Override
    protected Mono<Void> doHandle(RemoveTermsAndConditionsCommand cmd) {
        return distributorTermsAndConditionsApi.deleteDistributorTermsAndConditions(cmd.distributorId(), cmd.termsAndConditionsId(), UUID.randomUUID().toString());
    }
}

