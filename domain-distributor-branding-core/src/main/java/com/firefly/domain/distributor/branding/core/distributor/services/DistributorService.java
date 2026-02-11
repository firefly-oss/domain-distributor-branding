package com.firefly.domain.distributor.branding.core.distributor.services;

import com.firefly.domain.distributor.branding.core.distributor.commands.RegisterDistributorCommand;
import com.firefly.domain.distributor.branding.core.distributor.commands.ReviseBrandingCommand;
import com.firefly.domain.distributor.branding.core.distributor.commands.ReviseTermsAndConditionsCommand;
import com.firefly.domain.distributor.branding.core.distributor.commands.SetDefaultBrandingCommand;
import com.firefly.transactional.saga.core.SagaResult;
import reactor.core.publisher.Mono;

public interface DistributorService {

    /**
     * Handles the onboarding process for a new distributor, including setting up branding,
     * terms and conditions, and audit logs.
     *
     * @param command the command containing the necessary information to onboard a distributor,
     *                including branding, terms and conditions, and audit log details.
     * @return a {@code Mono<SagaResult>} representing the result of the onboarding saga,
     *         which includes the success or failure status of the operation.
     */
    Mono<SagaResult> onboardDistributor(RegisterDistributorCommand command);
    
    /**
     * Revises the branding configuration for a distributor.
     *
     * @param command the command containing the details required to revise the branding,
     *                including distributor ID and branding ID.
     * @return a Mono emitting the result of the branding revision operation as a SagaResult.
     */
    Mono<SagaResult> reviseBranding(ReviseBrandingCommand command);
    
    /**
     * Marks a specific branding as the default for a distributor.
     *
     * @param command an instance of {@link SetDefaultBrandingCommand} containing
     *                the distributor ID, branding ID, and the flag indicating
     *                the branding should be marked as default.
     * @return a {@link Mono} emitting the {@link SagaResult} that indicates the outcome
     *         of the operation.
     */
    Mono<SagaResult> setDefaultBranding(SetDefaultBrandingCommand command);

    /**
     * Revises or versions the terms and conditions for a distributor.
     *
     * @param command the command containing distributor-specific data and updates to the terms and conditions
     * @return a Mono emitting the result of the distributed transaction associated with the revision
     */
    Mono<SagaResult> reviseTermsAndConditions(ReviseTermsAndConditionsCommand command);

}
