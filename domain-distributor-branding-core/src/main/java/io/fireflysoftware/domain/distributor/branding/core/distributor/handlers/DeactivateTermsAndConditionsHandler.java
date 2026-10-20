package io.fireflysoftware.domain.distributor.branding.core.distributor.handlers;

import io.fireflysoftware.core.distributor.sdk.api.DistributorTermsAndConditionsApi;
import io.fireflysoftware.core.distributor.sdk.model.DistributorTermsAndConditionsDTO;
import io.fireflysoftware.domain.distributor.branding.core.distributor.commands.DeactivateTermsAndConditionsCommand;
import org.fireflyframework.cqrs.annotations.CommandHandlerComponent;
import org.fireflyframework.cqrs.command.CommandHandler;
import reactor.core.publisher.Mono;
import java.util.UUID;


/**
 * Handler that deactivates terms and conditions for a distributor.
 */
@CommandHandlerComponent
public class DeactivateTermsAndConditionsHandler extends CommandHandler<DeactivateTermsAndConditionsCommand, DistributorTermsAndConditionsDTO> {

    private final DistributorTermsAndConditionsApi termsApi;

    public DeactivateTermsAndConditionsHandler(DistributorTermsAndConditionsApi termsApi) {
        this.termsApi = termsApi;
    }

    @Override
    protected Mono<DistributorTermsAndConditionsDTO> doHandle(DeactivateTermsAndConditionsCommand cmd) {
        return termsApi.deactivateTermsAndConditions(cmd.distributorId(), cmd.termsAndConditionsId(), cmd.deactivatedBy(), UUID.randomUUID().toString());
    }
}
