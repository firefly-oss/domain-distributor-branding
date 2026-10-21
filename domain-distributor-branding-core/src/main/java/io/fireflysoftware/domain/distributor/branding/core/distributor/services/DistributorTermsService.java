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

import io.fireflysoftware.core.distributor.sdk.model.DistributorTermsAndConditionsDTO;
import io.fireflysoftware.domain.distributor.branding.core.distributor.commands.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Service that orchestrates terms and conditions lifecycle operations
 * through the CQRS command and query buses.
 */
public interface DistributorTermsService {

    /**
     * Lists all terms and conditions for a distributor.
     *
     * @param distributorId the distributor identifier
     * @return a Mono emitting the terms and conditions
     */
    Mono<DistributorTermsAndConditionsDTO> listTermsAndConditions(UUID distributorId);

    /**
     * Retrieves the active terms and conditions for a distributor.
     *
     * @param distributorId the distributor identifier
     * @return a Mono emitting the active terms and conditions
     */
    Mono<DistributorTermsAndConditionsDTO> getActiveTermsAndConditions(UUID distributorId);

    /**
     * Retrieves the latest terms and conditions for a distributor.
     *
     * @param distributorId the distributor identifier
     * @return a Mono emitting the latest terms and conditions
     */
    Mono<DistributorTermsAndConditionsDTO> getLatestTermsAndConditions(UUID distributorId);

    /**
     * Creates new terms and conditions for a distributor.
     *
     * @param command the command containing the terms data
     * @return a Mono emitting the created identifier
     */
    Mono<UUID> createTermsAndConditions(CreateTermsAndConditionsCommand command);

    /**
     * Retrieves the detail of a specific terms and conditions entry.
     *
     * @param distributorId the distributor identifier
     * @param termsAndConditionsId the terms and conditions identifier
     * @return a Mono emitting the terms and conditions detail
     */
    Mono<DistributorTermsAndConditionsDTO> getTermsAndConditionsDetail(UUID distributorId, UUID termsAndConditionsId);

    /**
     * Deletes terms and conditions from a distributor.
     *
     * @param distributorId the distributor identifier
     * @param termsAndConditionsId the terms and conditions identifier
     * @return a Mono completing when deleted
     */
    Mono<Void> deleteTermsAndConditions(UUID distributorId, UUID termsAndConditionsId);

    /**
     * Signs terms and conditions for a distributor.
     *
     * @param command the sign command
     * @return a Mono emitting the signed terms and conditions
     */
    Mono<DistributorTermsAndConditionsDTO> signTermsAndConditions(SignTermsAndConditionsCommand command);

    /**
     * Activates terms and conditions for a distributor.
     *
     * @param command the activate command
     * @return a Mono emitting the activated terms and conditions
     */
    Mono<DistributorTermsAndConditionsDTO> activateTermsAndConditions(ActivateTermsAndConditionsCommand command);

    /**
     * Deactivates terms and conditions for a distributor.
     *
     * @param command the deactivate command
     * @return a Mono emitting the deactivated terms and conditions
     */
    Mono<DistributorTermsAndConditionsDTO> deactivateTermsAndConditions(DeactivateTermsAndConditionsCommand command);

    /**
     * Checks whether a distributor has active signed terms.
     *
     * @param distributorId the distributor identifier
     * @return a Mono emitting true if active signed terms exist
     */
    Mono<Boolean> hasActiveSignedTerms(UUID distributorId);
}
