/*
 * Copyright 2025 Firefly Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package io.fireflysoftware.domain.distributor.branding.core.distributor.services;

import io.fireflysoftware.domain.distributor.branding.core.distributor.commands.RegisterDistributorCommand;
import io.fireflysoftware.domain.distributor.branding.core.distributor.commands.ReviseBrandingCommand;
import io.fireflysoftware.domain.distributor.branding.core.distributor.commands.ReviseTermsAndConditionsCommand;
import io.fireflysoftware.domain.distributor.branding.core.distributor.commands.SetDefaultBrandingCommand;
import org.fireflyframework.orchestration.saga.engine.SagaResult;
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
